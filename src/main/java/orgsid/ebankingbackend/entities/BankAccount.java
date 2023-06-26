package orgsid.ebankingbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import orgsid.ebankingbackend.enums.AccountStatus;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)//Dans Une Seul Table
@DiscriminatorColumn(name = "TYPE", length = 4)


@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BankAccount {
    @Id
    private String id;
    private double balance;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;//De Type Enum
    @ManyToOne //Plusieure Compte Pour OnClient
    private Customer customer;
    @OneToMany (mappedBy = "bankAccount", fetch = FetchType.LAZY) //Pour qu il creer pas deux cle Etrangere  //Un compte peux avoire plusieur Operation
    private List<AccountOperation> accountOperationList;
}

























