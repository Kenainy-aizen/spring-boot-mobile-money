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

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter

public class Envoi {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idEnv;
    private String numEnvoyeur;
    private String numRecepteur;
    private Integer montant;
    private LocalDateTime date;
    private boolean payer_frais_retrait;
    private String raison;
}
