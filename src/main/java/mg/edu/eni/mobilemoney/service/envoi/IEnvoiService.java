package mg.edu.eni.mobilemoney.service.envoi;

import mg.edu.eni.mobilemoney.model.Envoi;
import mg.edu.eni.mobilemoney.model.Retrait;

import java.time.LocalDate;
import java.util.List;

public interface IEnvoiService {
    Envoi addEnvoi(Envoi envoi);
    Envoi updateEnvoi(Envoi envoi, String idEnv);
    void deleteEnvoi(String idEnv);
    List<Envoi> getAllEnvoi();
    Envoi getEnvoiById(String idEnv);
    List<Envoi> searchByDate(LocalDate date);
    List<Envoi> searchByYearMonthNum(String numTel, int month, int year);
}