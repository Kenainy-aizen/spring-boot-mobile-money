package mg.edu.eni.mobilemoney.service.frais_recep;

import mg.edu.eni.mobilemoney.model.Frais_recep;

import java.util.List;

public interface IFrais_recepService {
    Frais_recep addFraisRecep(Frais_recep fraisRecep);
    Frais_recep updateFraisRecepById(Frais_recep fraisRecep, String idRec);
    void deleteFraisRecep(String idRec);
    List<Frais_recep> getAllFraisRecep();
    Frais_recep getFraisRecepById(String idRec);
    String generateNextId();
}
