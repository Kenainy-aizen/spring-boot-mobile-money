package mg.edu.eni.mobilemoney.service.retrait;

import mg.edu.eni.mobilemoney.model.Retrait;

import java.time.LocalDate;
import java.util.List;

public interface IRetraitService {
    Retrait addRetrait(Retrait retrait);
    Retrait updateRetrait(Retrait retrait, String idRecep);
    void deleteRetrait(String idRecep);
    List<Retrait> getAllRetrait();
    Retrait getRetraitById(String idRecep);
    List<Retrait> searchByDate(LocalDate date);
    List<Retrait> searchByYearMonthNum(String numTel, int month, int year);
}
