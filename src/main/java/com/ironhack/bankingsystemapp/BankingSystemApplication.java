package com.ironhack.bankingsystemapp;

import com.ironhack.bankingsystemapp.repositories.accounts.*;
import com.ironhack.bankingsystemapp.repositories.users.*;
import com.ironhack.bankingsystemapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

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
