package mg.edu.eni.mobilemoney.service.frais_envoi;

import mg.edu.eni.mobilemoney.model.Frais_envoi;

import java.util.List;

public interface IFrais_envoiService {
    Frais_envoi addFraisEnvoi(Frais_envoi fraisEnvoi);
    void deleteFraisEnvoi(String idEnv);
    Frais_envoi updateFraisEnvoiById(Frais_envoi fraisEnvoi, String idEnv);
    Frais_envoi getFraisEnvoiById(String idEnv);
    List<Frais_envoi> getAllFraisEnvoi();
}

