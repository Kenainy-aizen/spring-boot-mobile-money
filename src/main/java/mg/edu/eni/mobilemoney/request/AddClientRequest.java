package mg.edu.eni.mobilemoney.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AddClientRequest {
    private String numtel;
    private String nom;
    private String sexe;
    private Integer age;
    private Integer solde;
    private String mail;

}