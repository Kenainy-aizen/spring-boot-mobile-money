package mg.edu.eni.mobilemoney.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SecondaryRow;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Client {
    @Id
    private String numtel;
    private String nom;
    private String sexe;
    private Integer age;
    private Integer solde;
    private String mail;

    public Client(String numtel, String nom, String sexe, Integer age, Integer solde, String mail) {
        this.numtel = numtel;
        this.nom = nom;
        this.sexe = sexe;
        this.age = age;
        this.solde = solde;
        this.mail = mail;
    }
}
