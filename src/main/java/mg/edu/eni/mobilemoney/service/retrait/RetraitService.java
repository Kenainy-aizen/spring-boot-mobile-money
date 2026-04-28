package mg.edu.eni.mobilemoney.service.retrait;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mg.edu.eni.mobilemoney.exceptions.ClientNotFoundException;
import mg.edu.eni.mobilemoney.exceptions.FraisEmptyException;
import mg.edu.eni.mobilemoney.exceptions.LowBalanceException;
import mg.edu.eni.mobilemoney.exceptions.RetraitNotFoundException;
import mg.edu.eni.mobilemoney.model.Client;
import mg.edu.eni.mobilemoney.model.Frais_recep;
import mg.edu.eni.mobilemoney.model.Retrait;
import mg.edu.eni.mobilemoney.repository.ClientRepository;
import mg.edu.eni.mobilemoney.repository.FraisRecepRepository;
import mg.edu.eni.mobilemoney.repository.RetraitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RetraitService implements IRetraitService{

    private final RetraitRepository retraitRepository;
    private final FraisRecepRepository fraisRecepRepository;
    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public Retrait addRetrait(Retrait request) {

        Client ClientRet = clientRepository.findById(request.getNumtel())
                .orElseThrow(() -> new ClientNotFoundException("Client non trouve"));

        Frais_recep palierRec = fraisRecepRepository.findFraisPourMontant(request.getMontant());

        if (palierRec == null) {
            throw new FraisEmptyException("Paliers de frais non configurés");
        }

        Integer Total = request.getMontant() + palierRec.getFrais_rec();

        if( ClientRet.getSolde() < Total){
            throw new LowBalanceException("Solde insuffisant");
        }

        ClientRet.setSolde(ClientRet.getSolde() - Total);
        request.setDaterecep(LocalDateTime.now());

        clientRepository.save(ClientRet);
        return retraitRepository.save(request);
    }

    @Override
    public Retrait updateRetrait(Retrait request, String idRecep) {
        return retraitRepository.findById(idRecep)
                .map(existingRetrait -> updateExistingRetrait(request, existingRetrait))
                .map( retraitRepository :: save)
                .orElseThrow(() -> new RetraitNotFoundException("Retrait non trouve"));
    }

    private Retrait updateExistingRetrait(Retrait request, Retrait existingRetrait){
        existingRetrait.setDaterecep(LocalDateTime.now());
        existingRetrait.setMontant(request.getMontant());
        existingRetrait.setNumtel(request.getNumtel());
        existingRetrait.setRaison(request.getRaison());

        return existingRetrait;
    }

    @Override
    public void deleteRetrait(String idRecep) {
        retraitRepository.findById(idRecep).ifPresentOrElse(retraitRepository :: delete,
                () -> { throw new RetraitNotFoundException("Retrait non trouve");});
    }

    @Override
    public List<Retrait> getAllRetrait() {
        return retraitRepository.findAll();
    }

    @Override
    public Retrait getRetraitById(String idRecep) {
        return retraitRepository.findById(idRecep)
                .orElseThrow(() -> new RetraitNotFoundException("Retrait non trouve"));
    }

    @Override
    public List<Retrait> searchByDate(LocalDate date) {
        LocalDateTime debut = date.atStartOfDay();
        LocalDateTime fin = date.atTime(LocalTime.MAX);

        return retraitRepository.findByDateBetween(debut, fin);
    }

    @Override
    public List<Retrait> searchByYearMonthNum(String numTel, int month, int year) {
        return retraitRepository.findByNumTelAndMonth(numTel, month, year);
    }

}
