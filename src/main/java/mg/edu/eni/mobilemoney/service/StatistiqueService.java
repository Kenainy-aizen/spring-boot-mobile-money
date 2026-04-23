package mg.edu.eni.mobilemoney.service;

import lombok.RequiredArgsConstructor;
import mg.edu.eni.mobilemoney.repository.EnvoiRepository;
import mg.edu.eni.mobilemoney.repository.RetraitRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatistiqueService {
    private EnvoiRepository envoiRepository;
    private RetraitRepository retraitRepository;
    
    public Integer calculerRecetteTotale() {
        Integer totalEnvoi = envoiRepository.totalRecetteEnvoi();
        Integer totalRetrait = retraitRepository.totalRecetteRetrait();

        int sommeEnvoi = (totalEnvoi != null) ? totalEnvoi : 0;
        int sommeRetrait = (totalRetrait != null) ? totalRetrait : 0;

        return sommeEnvoi + sommeRetrait;
    }
}
