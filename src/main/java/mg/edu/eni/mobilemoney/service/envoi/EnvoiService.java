package mg.edu.eni.mobilemoney.service.envoi;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mg.edu.eni.mobilemoney.exceptions.ClientNotFoundException;
import mg.edu.eni.mobilemoney.exceptions.EnvoiNotFoundException;
import mg.edu.eni.mobilemoney.exceptions.FraisEmptyException;
import mg.edu.eni.mobilemoney.exceptions.LowBalanceException;
import mg.edu.eni.mobilemoney.model.Client;
import mg.edu.eni.mobilemoney.model.Envoi;
import mg.edu.eni.mobilemoney.model.Frais_envoi;
import mg.edu.eni.mobilemoney.model.Frais_recep;
import mg.edu.eni.mobilemoney.repository.ClientRepository;
import mg.edu.eni.mobilemoney.repository.EnvoiRepository;
import mg.edu.eni.mobilemoney.repository.FraisEnvoiRepository;
import mg.edu.eni.mobilemoney.repository.FraisRecepRepository;
import mg.edu.eni.mobilemoney.service.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnvoiService implements IEnvoiService {
    private final EnvoiRepository envoiRepository;
    private final ClientRepository clientRepository;
    private final FraisRecepRepository fraisRecepRepository;
    private final FraisEnvoiRepository fraisEnvoiRepository;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public Envoi addEnvoi(Envoi request){

        Client clientEnv = clientRepository.findById(request.getNumEnvoyeur())
                .orElseThrow(() -> new ClientNotFoundException("Le client envoyeur non trouve"));

        Client clientRec = clientRepository.findById(request.getNumRecepteur())
                .orElseThrow(() -> new ClientNotFoundException("Client non trouve"));

        Frais_envoi palierEnv = fraisEnvoiRepository.findFraisPourMontant(request.getMontant());

        Frais_recep palierRec = fraisRecepRepository.findFraisPourMontant(request.getMontant());

        if (palierEnv == null || palierRec == null) {
            throw new FraisEmptyException("Frais introuvables");
        }

        Integer FraisRep = request.isPayer_frais_retrait() ? palierRec.getFrais_rec() : 0;
        Integer Total = request.getMontant() + palierEnv.getFrais_env() + FraisRep;

        if(clientEnv.getSolde() < Total){
            throw new LowBalanceException("Solde insuffisant");
        }

        clientEnv.setSolde(clientEnv.getSolde() - Total);
        clientRec.setSolde(clientRec.getSolde() + FraisRep + request.getMontant());

        request.setDate(LocalDateTime.now());
        clientRepository.save(clientEnv);
        clientRepository.save(clientRec);

        Envoi theEnvoiRepository =  envoiRepository.save(request);

        String msgEnv = "Bonjour " + clientEnv.getNom() + ",\n" +
                "Vous avez envoyé " + request.getMontant() + " Ar à " + request.getNumRecepteur() + ".\n" +
                "Montant total débité (avec frais) : " + Total + " Ar.\n" +
                "Votre nouveau solde est de : " + clientEnv.getSolde() + " Ar.";

        notificationService.envoyerEmail(clientEnv.getMail(), "Confirmation d'envoi", msgEnv);

        String msgRec = "Bonjour " + clientRec.getNom() + ",\n" +
                "Vous avez reçu " + request.getMontant() + " Ar de la part de " + request.getNumEnvoyeur() + ".\n" +
                "Votre nouveau solde est de : " + clientRec.getSolde() + " Ar.";

        notificationService.envoyerEmail(clientRec.getMail(), "Avis de réception", msgRec);

        return theEnvoiRepository;
    }

    @Override
    public Envoi updateEnvoi(Envoi request, String idEnv) {
        return envoiRepository.findById(idEnv)
                .map(existingEnvoi -> updateExistingEnvoi(existingEnvoi, request))
                .map(envoiRepository :: save)
                .orElseThrow(() -> new EnvoiNotFoundException("Envoi non trouve"));
    }

    private Envoi updateExistingEnvoi(Envoi existingEnvoi, Envoi request) {
        existingEnvoi.setDate(LocalDateTime.now());
        existingEnvoi.setRaison(request.getRaison());
        existingEnvoi.setMontant(request.getMontant());
        existingEnvoi.setNumEnvoyeur(request.getNumEnvoyeur());
        existingEnvoi.setNumRecepteur(request.getNumRecepteur());
        existingEnvoi.setPayer_frais_retrait(request.isPayer_frais_retrait());

        return existingEnvoi;
    }

    @Override
    public void deleteEnvoi(String idEnv) {
        envoiRepository.findById(idEnv)
                .ifPresentOrElse(envoiRepository :: delete,
                        () -> { throw new EnvoiNotFoundException("Envoi non trouve");}
                );
    }

    @Override
    public List<Envoi> getAllEnvoi() {
        return envoiRepository.findAll();
    }

    @Override
    public Envoi getEnvoiById(String idEnv) {
        return envoiRepository.findById(idEnv)
                .orElseThrow(() -> new EnvoiNotFoundException("Envoi non trouve")
                );
    }

    @Override
    public List<Envoi> searchByDate(LocalDate date) {
        LocalDateTime debut = date.atStartOfDay();
        LocalDateTime fin = date.atTime(LocalTime.MAX);

        return envoiRepository.findByDateBetween(debut, fin);
        
    }
   
    @Override
    public List<Envoi> searchByYearMonthNum(String numTel, int month, int year) {
        return envoiRepository.findByNumTelAndMonth(numTel, month, year);
    }
}
