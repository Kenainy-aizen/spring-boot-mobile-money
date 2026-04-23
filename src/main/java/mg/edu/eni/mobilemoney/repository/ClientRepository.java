package mg.edu.eni.mobilemoney.repository;

import mg.edu.eni.mobilemoney.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, String> {

    List<Client> findByNom(String nom);
    @Query("SELECT c FROM Client c WHERE LOWER(c.nom) LIKE LOWER(CONCAT('%',:keyword,'%')) OR c.numtel LIKE %:keyword%")
    List<Client> searchClients(@Param("keyword") String keyword);
}
