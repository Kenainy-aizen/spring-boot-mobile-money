package mg.edu.eni.mobilemoney.service.frais_recep;

import lombok.RequiredArgsConstructor;
import mg.edu.eni.mobilemoney.exceptions.FraisRecepNotFoundException;
import mg.edu.eni.mobilemoney.model.Frais_recep;
import mg.edu.eni.mobilemoney.repository.FraisRecepRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Frais_recepService implements IFrais_recepService {

    private final FraisRecepRepository fraisRecepRepository;

    public Frais_recepService(FraisRecepRepository fraisRecepRepository) {
        this.fraisRecepRepository = fraisRecepRepository;
    }

    @Override
    public Frais_recep addFraisRecep(Frais_recep request) {
        request.setIdRec(generateNextId());
        return fraisRecepRepository.save(request);
    }

    @Override
    public Frais_recep updateFraisRecepById(Frais_recep request, String idRec) {
        return fraisRecepRepository.findById(idRec)
                .map(existingFraisRecep -> updateExistingFraisRecep(request, existingFraisRecep))
                .map(fraisRecepRepository :: save)
                .orElseThrow(() -> new FraisRecepNotFoundException("Frais de reception non trouve"));
    }

    private Frais_recep updateExistingFraisRecep(Frais_recep request, Frais_recep existingFraisRecep){
        existingFraisRecep.setMontant1(request.getMontant1());
        existingFraisRecep.setMontant2(request.getMontant2());
        existingFraisRecep.setFrais_rec(request.getFrais_rec());

        return existingFraisRecep;
    }

    @Override
    public void deleteFraisRecep(String idRec) {
        fraisRecepRepository.findById(idRec).ifPresentOrElse(fraisRecepRepository :: delete,
                () -> {throw new FraisRecepNotFoundException("Frais de reception non trouve");}
        );
    }

    @Override
    public List<Frais_recep> getAllFraisRecep() {
        return fraisRecepRepository.findAll();
    }

    @Override
    public Frais_recep getFraisRecepById(String idRec) {
        return fraisRecepRepository.findById(idRec)
                .orElseThrow(() -> new FraisRecepNotFoundException("Frais de reception non trouve")
                );
    }

    @Override
    public String generateNextId() {
        return fraisRecepRepository.findFirstByOrderByIdRecDesc()
                .map(dernier -> {
                    String idStr = dernier.getIdRec();
                    int numero = Integer.parseInt(idStr.substring(2));
                    return "FR" + (numero + 1);
                })
                .orElse("FR1");
    }


}
