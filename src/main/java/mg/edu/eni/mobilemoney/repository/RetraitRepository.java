package mg.edu.eni.mobilemoney.repository;

import mg.edu.eni.mobilemoney.model.Envoi;
import mg.edu.eni.mobilemoney.model.Retrait;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RetraitRepository extends JpaRepository<Retrait, String> {
    @Query("SELECT r FROM Retrait r WHERE r.daterecep >= :debut AND r.daterecep <= :fin")
    List<Retrait> findByDateBetween(
            @Param("debut") LocalDateTime debut,
            @Param("fin") LocalDateTime fin
    );

    @Query(value = "SELECT SUM(fr.frais_rec) FROM retrait r " + // 'retrait' en minuscules ici
            "JOIN frais_recep fr ON r.montant >= fr.montant1 AND r.montant <= fr.montant2",
            nativeQuery = true)
    Integer totalRecetteRetrait();

    @Query("SELECT r FROM Retrait r WHERE r.numtel = :numTel " +
            "AND MONTH(r.daterecep) = :mois AND YEAR(r.daterecep) = :annee")
    List<Retrait> findByNumTelAndMonth(@Param("numTel") String numTel,
                                       @Param("mois") int mois,
                                       @Param("annee") int annee);

    @Query("SELECT r FROM Retrait r WHERE r.numtel = :num AND MONTH(r.daterecep) = :mois AND YEAR(r.daterecep) = :annee")
    List<Retrait> findByClientAndMonth(String num, int mois, int annee);

    List<Retrait> findByDaterecep(LocalDateTime date);

}
