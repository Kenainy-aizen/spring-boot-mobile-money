package mg.edu.eni.mobilemoney.repository;

import mg.edu.eni.mobilemoney.model.Frais_envoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FraisEnvoiRepository extends JpaRepository<Frais_envoi, String>{
    @Query("SELECT f FROM Frais_envoi f WHERE :montant >= f.montant1 AND :montant <= f.montant2 ")
    Frais_envoi findFraisPourMontant(Integer montant);
}
