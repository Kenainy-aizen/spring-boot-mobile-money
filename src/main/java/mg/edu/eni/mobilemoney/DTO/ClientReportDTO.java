package mg.edu.eni.mobilemoney.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientReportDTO {
    private String nomComplet;
    private String contact;
    private Integer age;
    private Integer soldeActuel;
    private List<TransactionDTO> transactions;
    private Integer totalDebit;
    private Integer totalCredit;
}
