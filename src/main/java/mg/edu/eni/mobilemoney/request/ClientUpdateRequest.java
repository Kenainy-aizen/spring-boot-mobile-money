package mg.edu.eni.mobilemoney.request;

import lombok.Data;
import lombok.Setter;

@Data

public class ClientUpdateRequest {

    private String numtel;
    private String nom;
    private String sexe;
    private Integer age;
    private Integer solde;
    private String mail;


    
}
