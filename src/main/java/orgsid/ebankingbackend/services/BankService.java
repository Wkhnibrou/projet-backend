package orgsid.ebankingbackend.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import orgsid.ebankingbackend.entities.BankAccount;
import orgsid.ebankingbackend.entities.CurrentAccount;
import orgsid.ebankingbackend.entities.SavingAccount;
import orgsid.ebankingbackend.repositories.BankAccountRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class BankService {

    @Autowired
    private BankAccountRepository bankAccountRepository;
    public void consulter(){
        BankAccount bankAccount =
                bankAccountRepository.findById("339b7a4d-c430-4ef1-a65f-1f0425418bb4").orElse(null);

        if(bankAccount!=null) {

            System.out.println("*****************************");
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getStatus());
            System.out.println(bankAccount.getCreatedAt());
            System.out.println(bankAccount.getCustomer().getName());

            if (bankAccount instanceof CurrentAccount) {
                System.out.println("Over Draft=>" + ((CurrentAccount) bankAccount).getOverDraft());
            } else if (bankAccount instanceof SavingAccount) {
                System.out.println("Rate" + ((SavingAccount) bankAccount).getInterestRate());
            }

            bankAccount.getAccountOperationList().forEach(op -> {
                System.out.println(op.getType() + "\t" + op.getOperationDate() + "\t" + op.getAmount());

            });
        }
    }
}
