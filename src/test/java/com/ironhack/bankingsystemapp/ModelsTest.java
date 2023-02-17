//package com.ironhack.bankingsystemapp;
//
//import com.ironhack.bankingsystemapp.models.Transaction;
//import com.ironhack.bankingsystemapp.models.accounts.CheckingAccount;
//import com.ironhack.bankingsystemapp.models.accounts.CreditCard;
//import com.ironhack.bankingsystemapp.models.accounts.SavingsAccount;
//import com.ironhack.bankingsystemapp.models.accounts.StudentAccount;
//import com.ironhack.bankingsystemapp.models.users.AccountHolder;
//import com.ironhack.bankingsystemapp.models.users.Address;
//import com.ironhack.bankingsystemapp.models.users.Admin;
//import com.ironhack.bankingsystemapp.models.users.ThirdParty;
//import com.ironhack.bankingsystemapp.repositories.accounts.*;
//import com.ironhack.bankingsystemapp.repositories.users.AccountHolderRepository;
//import com.ironhack.bankingsystemapp.repositories.users.AdminRepository;
//import com.ironhack.bankingsystemapp.repositories.users.ThirdPartyRepository;
//import com.ironhack.bankingsystemapp.repositories.users.UserRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//public class ModelsTest {
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    AccountHolderRepository accountHolderRepository;
//    @Autowired
//    AdminRepository adminRepository;
//    @Autowired
//    ThirdPartyRepository thirdPartyRepository;
//    @Autowired
//    AccountRepository accountRepository;
//    @Autowired
//    CheckingAccountRepository checkingAccountRepository;
//    @Autowired
//    StudentAccountRepository studentAccountRepository;
//    @Autowired
//    SavingsAccountRepository savingsAccountRepository;
//    @Autowired
//    CreditCardRepository creditCardRepository;
//    @Autowired
//    TransactionRepository transactionRepository;
//
//    Address address, mailaddress;
//    AccountHolder accountHolder, accountHolder2;
//    ThirdParty thirdParty;
//    Admin admin;
//    CheckingAccount checkingAccount;
//    StudentAccount studentAccount;
//    CreditCard creditCard;
//    SavingsAccount savingsAccount;
//    Transaction transaction;
////
////    @BeforeEach
////    public void setUp(){
////
////        address = new Address("Calle 1", "Barcelona", "08000", "Spain");
////        mailaddress =  new Address("Calle 1", "Barcelona", "08000", "Spain");
////
////        accountHolder = accountHolderRepository.save(new AccountHolder("User1", "user1", "1234", LocalDate.of(1980, 01, 01), address));
////        accountHolder2 = accountHolderRepository.save(new AccountHolder("User2", "user2", "1234", LocalDate.of(1980, 01, 01), address, mailaddress));
////        thirdParty = thirdPartyRepository.save(new ThirdParty("ThirdParty1", "thirdparty1", "1234", "ABC"));
////        admin = adminRepository.save(new Admin("Admin2","admin2", "1234"));
////
////        checkingAccount = checkingAccountRepository.save(new CheckingAccount(BigDecimal.valueOf(1000), accountHolder, "ABC"));
////        studentAccount = studentAccountRepository.save(new StudentAccount(BigDecimal.valueOf(750), accountHolder2, "ABC"));
////        savingsAccount = savingsAccountRepository.save(new SavingsAccount(BigDecimal.valueOf(2500), accountHolder, "ABC"));
////        creditCard = creditCardRepository.save(new CreditCard(BigDecimal.valueOf(2000), accountHolder));
////
////        transaction = transactionRepository.save(new Transaction(checkingAccount, studentAccount,"User2",BigDecimal.valueOf(100) ));
////    }
//
////    @AfterEach
////        public void tearDown(){
////        accountHolderRepository.deleteAll();
////        thirdPartyRepository.deleteAll();
////        adminRepository.deleteAll();
////        checkingAccountRepository.deleteAll();
////        studentAccountRepository.deleteAll();
////        savingsAccountRepository.deleteAll();
////        creditCardRepository.deleteAll();
////        transactionRepository.deleteAll();
////}
//    @Test
//    @DisplayName("createAdmin")
//    public void shouldCreateAdmin(){
//        assertEquals(1, adminRepository.findAll().size());
//    }
//    @Test
//    @DisplayName("createAccountholder")
//    public void shouldCreateAccountHOlder(){
//        assertEquals(2, accountHolderRepository.findAll().size());
//    }
//    @Test
//    @DisplayName("createThirdParty")
//    public void shouldCreateThirdParty(){
//        assertEquals(1, thirdPartyRepository.findAll().size());
//
//    }
//
//    @Test
//    @DisplayName("createUsers")
//    public void shouldCreateUsers(){
//        assertEquals(4,userRepository.findAll().size());
//        assertEquals("Admin1", userRepository.findByUsername("admin1").getName());
//    }
//    @Test
//    @DisplayName("deleteUser")
//    public void shouldDeleteUserById(){
//        userRepository.deleteById(1L);
//        assertEquals(3, userRepository.findAll().size());
//    }
//    @Test
//    @DisplayName("createAccounts")
//    public void shouldCreateAccounts(){
//        assertEquals(4, accountRepository.findAll().size());
//    }
//
//    @Test
//    @DisplayName("deleteAccount")
//    public void shouldDeleteAccountById(){
//        accountRepository.deleteById(1L);
//        assertEquals(3, accountRepository.findAll().size());
//    }
//
//
//
//}
