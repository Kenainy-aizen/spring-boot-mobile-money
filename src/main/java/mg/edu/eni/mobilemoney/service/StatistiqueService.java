package mg.edu.eni.mobilemoney.service;

import lombok.RequiredArgsConstructor;
import mg.edu.eni.mobilemoney.DTO.ClientReportDTO;
import mg.edu.eni.mobilemoney.DTO.TransactionDTO;
import mg.edu.eni.mobilemoney.DTO.TransactionDateDTO;
import mg.edu.eni.mobilemoney.model.Client;
import mg.edu.eni.mobilemoney.model.Envoi;
import mg.edu.eni.mobilemoney.model.Retrait;
import mg.edu.eni.mobilemoney.repository.ClientRepository;
import mg.edu.eni.mobilemoney.repository.EnvoiRepository;
import mg.edu.eni.mobilemoney.repository.RetraitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatistiqueService {
    private final EnvoiRepository envoiRepository;
    private final RetraitRepository retraitRepository;
    private final ClientRepository clientRepository;
    
    public Integer calculerRecetteTotale() {
        Integer totalEnvoi = envoiRepository.totalRecetteEnvoi();
        Integer totalRetrait = retraitRepository.totalRecetteRetrait();

        int sommeEnvoi = (totalEnvoi != null) ? totalEnvoi : 0;
        int sommeRetrait = (totalRetrait != null) ? totalRetrait : 0;

        return sommeEnvoi + sommeRetrait;
    }

    public ClientReportDTO genererRapportMensuel(String contact, int mois, int annee) {
        // 1. Récupérer les données brutes
        List<Envoi> envois = envoiRepository.findByClientAndMonth(contact, mois, annee);
        List<Retrait> retraits = retraitRepository.findByClientAndMonth(contact, mois, annee);
        Client theClient = clientRepository.findById(contact).get();

        List<TransactionDTO> transactions = new ArrayList<>();
        Integer totalDebit = 0;
        Integer totalCredit = 0;

        for (Envoi e : envois) {
            transactions.add(new TransactionDTO(
                    e.getDate(),
                    e.getRaison(),
                    e.getMontant(),
                    0
            ));
            totalDebit += e.getMontant();
        }

        // 3. Transformer les retraits en DTO (Crédit)
        for (Retrait r : retraits) {
            transactions.add(new TransactionDTO(
                    r.getDaterecep(),
                    r.getRaison(),
                    0,
                    r.getMontant()
            ));
            totalCredit += r.getMontant();
        }

        // 4. Trier par date (facultatif mais plus propre pour le relevé)
        transactions.sort((t1, t2) -> t1.getDate().compareTo(t2.getDate()));

        // 5. Construire le rapport final
        // (Ici tu récupères aussi les infos du client Rakoto Bernard...)
        return new ClientReportDTO(theClient.getNom(), theClient.getNumtel(), theClient.getAge(), theClient.getSolde(),transactions, totalDebit, totalCredit);

    }

    public List<TransactionDateDTO> getHistoriqueGlobal(LocalDate dateDebut) {
        LocalDateTime debut = dateDebut.atStartOfDay();
        LocalDateTime fin = dateDebut.atTime(LocalTime.MAX);
        List<Envoi> envois = envoiRepository.findByDateBetween(debut, fin);
        List<Retrait> retraits = retraitRepository.findByDateBetween(debut, fin);

        List<TransactionDateDTO> historique = new ArrayList<>();

        // 1. Ajouter les Envois
        for (Envoi e : envois) {
            historique.add(new TransactionDateDTO(
                    e.getDate(),
                    e.getRaison(),
                    e.getMontant(),
                    "ENVOI"
            ));
        }

        // 2. Ajouter les Retraits
        for (Retrait r : retraits) {
            historique.add(new TransactionDateDTO(
                    r.getDaterecep(),
                    r.getRaison(),
                    r.getMontant(),
                    "RETRAIT"
            ));
        }

        // 3. Trier du plus récent au plus ancien
        historique.sort((t1, t2) -> t2.getDate().compareTo(t1.getDate()));

        return historique;
    }

}
