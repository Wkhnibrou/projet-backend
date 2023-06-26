package orgsid.ebankingbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import orgsid.ebankingbackend.enums.OperationType;

import javax.persistence.*;

import java.util.Date;

@Entity

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AccountOperation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)//Auto Increment

    private Long id;
    private Date operationDate;
    private double amount;
    private String description;
    @Enumerated(EnumType.STRING)
    private OperationType type;//De Type Enum
    @ManyToOne //Plusieur Opreration Peut Effectuer Sur Un Compte
    private BankAccount bankAccount;

}








