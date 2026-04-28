package mg.edu.eni.mobilemoney.repository;

import mg.edu.eni.mobilemoney.model.Envoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EnvoiRepository extends JpaRepository<Envoi, String> {
    @Query("SELECT e FROM Envoi e WHERE e.date >= :debut AND e.date <= :fin")
    List<Envoi> findByDateBetween(
            @Param("debut") LocalDateTime debut,
            @Param("fin") LocalDateTime fin
    );

    @Query(value = "SELECT SUM(fe.frais_env) FROM envoi e " +
            "JOIN frais_envoi fe ON e.montant >= fe.montant1 AND e.montant <= fe.montant2",
            nativeQuery = true)
    Integer totalRecetteEnvoi();

    @Query("SELECT e FROM Envoi e WHERE (e.numEnvoyeur = :numTel OR e.numRecepteur = :numTel) " +
            "AND MONTH(e.date) = :mois AND YEAR(e.date) = :annee")
    List<Envoi> findByNumTelAndMonth(@Param("numTel") String numTel,
                                     @Param("mois") int mois,
                                     @Param("annee") int annee);

    @Query("SELECT e FROM Envoi e WHERE e.numEnvoyeur = :num AND MONTH(e.date) = :mois AND YEAR(e.date) = :annee")
    List<Envoi> findByClientAndMonth(String num, int mois, int annee);

    List<Envoi> findByDate(LocalDateTime date);
}
