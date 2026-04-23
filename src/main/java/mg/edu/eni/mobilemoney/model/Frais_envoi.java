package mg.edu.eni.mobilemoney.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Frais_envoi {
    @Id
    private String idEnv;
    private Integer montant1;
    private Integer montant2;
    private Integer frais_env;

}
