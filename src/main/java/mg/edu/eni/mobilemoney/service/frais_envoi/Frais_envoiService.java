package mg.edu.eni.mobilemoney.service.frais_envoi;

import lombok.RequiredArgsConstructor;
import mg.edu.eni.mobilemoney.exceptions.FraisEnvoiNotFoundException;
import mg.edu.eni.mobilemoney.model.Frais_envoi;
import mg.edu.eni.mobilemoney.repository.FraisEnvoiRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Frais_envoiService implements IFrais_envoiService{

    private final FraisEnvoiRepository fraisEnvoiRepository;

    public Frais_envoiService(FraisEnvoiRepository fraisEnvoiRepository) {
        this.fraisEnvoiRepository = fraisEnvoiRepository;
    }

    @Override
    public Frais_envoi addFraisEnvoi(Frais_envoi request) {
        request.setIdEnv(generateNextId());
        return fraisEnvoiRepository.save(request);
    }

    @Override
    public void deleteFraisEnvoi(String idEnv) {
        fraisEnvoiRepository.findById(idEnv)
                .ifPresentOrElse(fraisEnvoiRepository::delete, () -> {
                    throw new FraisEnvoiNotFoundException("Frai d'envoi non trouve");
                });
    }

    @Override
    public Frais_envoi updateFraisEnvoiById(Frais_envoi request, String idEnv) {
        return fraisEnvoiRepository.findById(idEnv)
                .map(existingFraisEnvoi -> updateExistingFraisEnvoi(request, existingFraisEnvoi))
                .map(fraisEnvoiRepository :: save)
                .orElseThrow(() -> new FraisEnvoiNotFoundException("Frais d'envoi non trouve"));
    }

    private Frais_envoi updateExistingFraisEnvoi(Frais_envoi request , Frais_envoi existingFraisEnvoi) {
        existingFraisEnvoi.setMontant1(request.getMontant1());
        existingFraisEnvoi.setMontant2(request.getMontant2());
        existingFraisEnvoi.setFrais_env(request.getFrais_env());

        return existingFraisEnvoi;
    }
    @Override
    public Frais_envoi getFraisEnvoiById(String idEnv) {
        return fraisEnvoiRepository.findById(idEnv)
                .orElseThrow(() -> new FraisEnvoiNotFoundException("Frais d'envoi non trouve"));
    }

    @Override
    public List<Frais_envoi> getAllFraisEnvoi() {
        return fraisEnvoiRepository.findAll();
    }

    @Override
    public String generateNextId() {
        return fraisEnvoiRepository.findFirstByOrderByIdEnvDesc()
                .map(dernier -> {
                    String idStr = dernier.getIdEnv();
                    int numero = Integer.parseInt(idStr.substring(2));
                    return "FV" + (numero + 1);
                })
                .orElse("FV1");
    }
}
