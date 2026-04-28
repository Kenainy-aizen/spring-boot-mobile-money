package mg.edu.eni.mobilemoney.repository;

import mg.edu.eni.mobilemoney.model.Frais_envoi;
import mg.edu.eni.mobilemoney.model.Frais_recep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FraisRecepRepository extends JpaRepository<Frais_recep, String> {
    @Query("SELECT f FROM Frais_recep f WHERE :montant >= f.montant1 AND :montant <= f.montant2 ")
    Frais_recep findFraisPourMontant(Integer montant);
    Optional<Frais_recep> findFirstByOrderByIdRecDesc();


}

