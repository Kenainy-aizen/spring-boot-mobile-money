package mg.edu.eni.mobilemoney.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
public class Retrait {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idrecep;
    private String numtel;
    private Integer montant;
    private LocalDateTime daterecep;
    private String raison;
}
