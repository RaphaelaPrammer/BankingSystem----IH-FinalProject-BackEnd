package com.ironhack.bankingsystemapp;

import com.ironhack.bankingsystemapp.models.users.*;
import com.ironhack.bankingsystemapp.repositories.accounts.*;
import com.ironhack.bankingsystemapp.repositories.users.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class BankingSystemApplication implements CommandLineRunner{

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


    public static void main(String[] args) {
        SpringApplication.run(BankingSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Admin admin1 = new Admin("admin1", "admin1","1234");
        adminRepository.save(admin1);

        Address address1 = new Address("Calle 1","Barcelona","08020", "Spain");
        Address mailaddress1 = new Address("mailaddress 1","Madrid","00000", "Spain");

        AccountHolder accountHolder1 = new AccountHolder("Max Mustermann", "maxmustermann", "1234",LocalDate.of(1980,1,1),address1, mailaddress1);
        accountHolderRepository.save(accountHolder1);

        ThirdParty thirdParty1 = new ThirdParty("thirdPartyName", "thirdpartyusername", "1234", "hashedKey" );
        thirdPartyRepository.save(thirdParty1);

        Role role1 = new Role("ACCOUNT-HOLDER", accountHolder1);
        Role role2 = new Role("THIRD-PARTY", thirdParty1);
        Role role3 = new Role("ADMIN", admin1);
        roleRepository.save(role1);
        roleRepository.save(role2);
        roleRepository.save(role3);

//        CheckingAccount account1 = new CheckingAccount(BigDecimal.valueOf(2000), accountHolder1,"abc");


    }


//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }

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
 //   }

}
