package mg.edu.eni.mobilemoney.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionDateDTO {
    private LocalDateTime date;
    private String raison;
    private Integer montant;
    private String type;
}
