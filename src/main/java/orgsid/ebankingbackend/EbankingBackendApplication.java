package orgsid.ebankingbackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import orgsid.ebankingbackend.dtos.BankAccountDTO;
import orgsid.ebankingbackend.dtos.CurrentBankAccountDTO;
import orgsid.ebankingbackend.dtos.CustomerDTO;
import orgsid.ebankingbackend.dtos.SavingBankAccountDTO;
import orgsid.ebankingbackend.entities.*;
import orgsid.ebankingbackend.enums.AccountStatus;
import orgsid.ebankingbackend.enums.OperationType;
import orgsid.ebankingbackend.exceptions.BalanceNotSufficantException;
import orgsid.ebankingbackend.exceptions.BankAccountNotFoundException;
import orgsid.ebankingbackend.exceptions.CustomerNotFoundException;
import orgsid.ebankingbackend.repositories.AccountOperationRepository;
import orgsid.ebankingbackend.repositories.BankAccountRepository;
import orgsid.ebankingbackend.repositories.CustomerRepository;
import orgsid.ebankingbackend.services.BankAccountService;



import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingBackendApplication.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
		return args -> {
			Stream.of("Walid", "Ahmed", "Khnibrou").forEach(name->{
				CustomerDTO customer=new CustomerDTO();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				bankAccountService.saveCustomer(customer);
			});
			bankAccountService.listCustomers().forEach(customer -> {

				try {
					bankAccountService.saveCurrentBankAccount(Math.random()*90000,900, customer.getId());
					bankAccountService.saveCurrentBankAccount(Math.random()*120000,5.5, customer.getId());


				} catch (CustomerNotFoundException e) {
					e.printStackTrace();
				}
			});

			List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();

			for (BankAccountDTO bankAccount:bankAccounts){
				for (int i = 0; i < 10; i++) {
					String accountId;
					if(bankAccount instanceof SavingBankAccountDTO){
						accountId=((SavingBankAccountDTO) bankAccount).getId();
					} else {
						accountId=((CurrentBankAccountDTO) bankAccount).getId();
					}
					bankAccountService.credit(accountId, 10000+Math.random()*120000, "Credit");
					bankAccountService.debit(accountId, 100+Math.random()*9000,"Debit");
				}

			}
		};
	}


	//@Bean
	CommandLineRunner start(CustomerRepository customerRepository,
							BankAccountRepository bankAccountRepository,
							AccountOperationRepository accountOperationRepository){


		return args -> {
			Stream.of("Youssef","Elkhatimi","Ahmed").forEach(name->{
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);//Pour Enregistrer
			});

			customerRepository.findAll().forEach(cust->{
				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString()); //Elle Genere Une Chaine de Caractere Aleatoire Unique
				currentAccount.setBalance(Math.random()*1800);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCustomer(cust);
				currentAccount.setOverDraft(8523);
				bankAccountRepository.save(currentAccount);


				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString()); //Elle Genere Une Chaine de Caractere Aleatoire Unique
				savingAccount.setBalance(Math.random()*1800);
				savingAccount.setCreatedAt(new Date());
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCustomer(cust);
				savingAccount.setInterestRate(5.5);
				bankAccountRepository.save(savingAccount);


			});

			bankAccountRepository.findAll().forEach(acc->{
				for (int i = 0 ; i <10 ; i++){
					AccountOperation accountOperation = new AccountOperation();
					accountOperation.setOperationDate(new Date());
					accountOperation.setAmount(Math.random()*1200);
					accountOperation.setType(Math.random()>0.5? OperationType.DEBIT : OperationType.CREDIT);
					accountOperation.setBankAccount(acc);
					accountOperationRepository.save(accountOperation);

				}
			});

		};
	}


}
