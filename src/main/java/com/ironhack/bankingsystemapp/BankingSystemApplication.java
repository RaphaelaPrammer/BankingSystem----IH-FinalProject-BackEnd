package com.ironhack.bankingsystemapp;

import com.ironhack.bankingsystemapp.models.Transaction;
import com.ironhack.bankingsystemapp.models.accounts.CheckingAccount;
import com.ironhack.bankingsystemapp.models.accounts.CreditCard;
import com.ironhack.bankingsystemapp.models.accounts.SavingsAccount;
import com.ironhack.bankingsystemapp.models.accounts.StudentAccount;
import com.ironhack.bankingsystemapp.models.users.*;
import com.ironhack.bankingsystemapp.repositories.accounts.*;
import com.ironhack.bankingsystemapp.repositories.users.*;
import com.ironhack.bankingsystemapp.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
//_________________________________________________________________________
@SpringBootApplication
public class BankingSystemApplication implements CommandLineRunner{

    @Autowired
    UserService userService;
    //User Repositories
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    //Account Repositories
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CheckingAccountRepository checkingAccountRepository;
    @Autowired
    SavingsAccountRepository savingsAccountRepository;
    @Autowired
    CreditCardRepository creditCardRepository;
    @Autowired
    StudentAccountRepository studentAccountRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(BankingSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Address address = new Address("Calle 1", "Barcelona", "08000", "Spain");
        Address mailaddress =  new Address("Calle 2", "Madrid", "06000", "Spain");

        AccountHolder accountHolder1 = accountHolderRepository.save(new AccountHolder("User1", "user1", passwordEncoder.encode("1234"), LocalDate.of(1980, 01, 01), address));
        AccountHolder accountHolder2 = accountHolderRepository.save(new AccountHolder("User2", "user2", passwordEncoder.encode("1234"), LocalDate.of(2005, 01, 01), address, mailaddress));
        ThirdParty thirdParty = thirdPartyRepository.save(new ThirdParty("ThirdParty1", "thirdparty1", passwordEncoder.encode("1234"), "ABC"));
        Admin admin = adminRepository.save(new Admin("Admin1","admin1", passwordEncoder.encode("1234")));

        CheckingAccount checkingAccount= checkingAccountRepository.save(new CheckingAccount(BigDecimal.valueOf(1000), accountHolder1, "ABC"));
        StudentAccount studentAccount = studentAccountRepository.save(new StudentAccount(BigDecimal.valueOf(750), accountHolder2, "ABC"));
        SavingsAccount savingsAccount = savingsAccountRepository.save(new SavingsAccount(BigDecimal.valueOf(2500), accountHolder1, "ABC"));
        CreditCard creditCard = creditCardRepository.save(new CreditCard(BigDecimal.valueOf(2000), accountHolder1));





      //  Transaction transaction = transactionRepository.save(new Transaction(checkingAccount, studentAccount,"User2",BigDecimal.valueOf(100) ));


         roleRepository.save(new Role("ACCOUNT-HOLDER"));
         roleRepository.save(new Role("THIRD-PARTY"));
         roleRepository.save(new Role("ADMIN"));

         userService.addRoleToUser("admin1", "ADMIN");
        userService.addRoleToUser("admin1", "ACCOUNT-HOLDER");
        userService.addRoleToUser("user1", "ACCOUNT-HOLDER");
        userService.addRoleToUser("user2", "ACCOUNT-HOLDER");
        userService.addRoleToUser("thirdparty1", "THIRD-PARTY");





    }


//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//    @Bean
//    CommandLineRunner run(UserService userService) {
//        return args -> {
//            userService.saveRole(new Role(null, "ROLE_USER"));
//            userService.saveRole(new Role(null, "ROLE_ADMIN"));
//
//            userService.saveUser(new User(null, "John Doe", "john", "1234", new ArrayList<>()));
//            userService.saveUser(new User(null, "James Smith", "james", "1234", new ArrayList<>()));
//            userService.saveUser(new User(null, "Jane Carry", "jane", "1234", new ArrayList<>()));
//            userService.saveUser(new User(null, "Chris Anderson", "chris", "1234", new ArrayList<>()));
//
//            userService.addRoleToUser("john", "ROLE_USER");
//            userService.addRoleToUser("james", "ROLE_ADMIN");
//            userService.addRoleToUser("jane", "ROLE_USER");
//            userService.addRoleToUser("chris", "ROLE_ADMIN");
//            userService.addRoleToUser("chris", "ROLE_USER");
//        };
//    }

}






