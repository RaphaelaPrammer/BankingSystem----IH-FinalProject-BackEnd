package com.ironhack.bankingsystemapp.services.accounts;

import com.ironhack.bankingsystemapp.models.accounts.Account;
import com.ironhack.bankingsystemapp.models.accounts.SavingsAccount;
import com.ironhack.bankingsystemapp.repositories.accounts.SavingsAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class SavingsAccountService {
    @Autowired
    SavingsAccountRepository savingsAccountRepository;

    public Account createSavingsAccount(SavingsAccount savingsAccount){
        return savingsAccountRepository.save(savingsAccount);
    }
 // get a List with all Savings Accounts

    public List<SavingsAccount> getAllSavingsAccounts(){
        return savingsAccountRepository.findAll();
    }






}
