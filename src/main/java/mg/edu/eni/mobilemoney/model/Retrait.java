package mg.edu.eni.mobilemoney.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
}
