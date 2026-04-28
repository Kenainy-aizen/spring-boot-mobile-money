package mg.edu.eni.mobilemoney.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private LocalDateTime date;
    private String raison;      // Ex: "Envoi à 034...", "Retrait d'argent"
    private Integer debit;       // Montant si c'est une sortie
    private Integer credit;      // Montant si c'est une entrée
}
