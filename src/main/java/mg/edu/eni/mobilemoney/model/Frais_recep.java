package mg.edu.eni.mobilemoney.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Frais_recep {
    @Id
    private String idRec;
    private Integer montant1;
    private Integer montant2;
    private Integer frais_rec;

}
