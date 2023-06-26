package orgsid.ebankingbackend.dtos;


import lombok.Data;
import orgsid.ebankingbackend.entities.BankAccount;
import orgsid.ebankingbackend.enums.OperationType;

import javax.persistence.*;
import java.util.Date;

@Data

public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private String description;
    private OperationType type;//De Type Enum

}








