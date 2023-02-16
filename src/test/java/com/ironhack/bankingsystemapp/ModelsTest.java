package com.ironhack.bankingsystemapp;

import com.ironhack.bankingsystemapp.models.Transaction;
import com.ironhack.bankingsystemapp.models.accounts.CheckingAccount;
import com.ironhack.bankingsystemapp.models.accounts.CreditCard;
import com.ironhack.bankingsystemapp.models.accounts.SavingsAccount;
import com.ironhack.bankingsystemapp.models.accounts.StudentAccount;
import com.ironhack.bankingsystemapp.models.users.AccountHolder;
import com.ironhack.bankingsystemapp.models.users.Address;
import com.ironhack.bankingsystemapp.models.users.Admin;
import com.ironhack.bankingsystemapp.models.users.ThirdParty;
import com.ironhack.bankingsystemapp.repositories.accounts.*;
import com.ironhack.bankingsystemapp.repositories.users.AccountHolderRepository;
import com.ironhack.bankingsystemapp.repositories.users.AdminRepository;
import com.ironhack.bankingsystemapp.repositories.users.ThirdPartyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
public class ModelsTest {

    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    ThirdPartyRepository thirdPartyRepository;
    @Autowired
    CheckingAccountRepository checkingAccountRepository;
    @Autowired
    StudentAccountRepository studentAccountRepository;
    @Autowired
    SavingsAccountRepository savingsAccountRepository;
    @Autowired
    CreditCardRepository creditCardRepository;
    @Autowired
    TransactionRepository transactionRepository;

    Address address, mailaddress;
    AccountHolder accountHolder, accountHolder2;
    ThirdParty thirdParty;
    Admin admin;
    CheckingAccount checkingAccount;
    StudentAccount studentAccount;
    CreditCard creditCard;
    SavingsAccount savingsAccount;
    Transaction transaction;

    @BeforeEach
    public void setUp(){
//        address = new Address("Calle 1", "Barcelona", "08000", "Spain");
//        mailaddress =  new Address("Calle 1", "Barcelona", "08000", "Spain");
//
//        accountHolder = accountHolderRepository.save(new AccountHolder("User1", "user1", "1234", LocalDate.of(1980, 01, 01), address));
//        accountHolder2 = accountHolderRepository.save(new AccountHolder("User2", "user2", "1234", LocalDate.of(1980, 01, 01), address, mailaddress));
//        thirdParty = thirdPartyRepository.save(new ThirdParty("ThirdParty1", "thirdparty1", "1234", "ABC"));
//        admin = adminRepository.save(new Admin("Admin2","admin2", "1234"));
//
//        checkingAccount = checkingAccountRepository.save(new CheckingAccount(BigDecimal.valueOf(1000), accountHolder, "ABC"));
//        studentAccount = studentAccountRepository.save(new StudentAccount(BigDecimal.valueOf(750), accountHolder2, "ABC"));
//        savingsAccount = savingsAccountRepository.save(new SavingsAccount(BigDecimal.valueOf(2500), accountHolder, "ABC"));
//        creditCard = creditCardRepository.save(new CreditCard(BigDecimal.valueOf(2000), accountHolder));
//
//        transaction = transactionRepository.save(new Transaction(checkingAccount, studentAccount,"User2",BigDecimal.valueOf(100) ));
    }

@AfterEach
    public void tearDown(){
//        accountHolderRepository.deleteAll();
//        thirdPartyRepository.deleteAll();
//        adminRepository.deleteAll();
//        checkingAccountRepository.deleteAll();
//        studentAccountRepository.deleteAll();
//        savingsAccountRepository.deleteAll();
//        creditCardRepository.deleteAll();
//        transactionRepository.deleteAll();
}

}
