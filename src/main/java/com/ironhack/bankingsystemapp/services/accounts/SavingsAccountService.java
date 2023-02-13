package com.ironhack.bankingsystemapp.services.accounts;

import com.ironhack.bankingsystemapp.models.accounts.Account;
import com.ironhack.bankingsystemapp.models.accounts.SavingsAccount;
import com.ironhack.bankingsystemapp.repositories.accounts.SavingsAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SavingsAccountService {
    @Autowired
    SavingsAccountRepository savingsAccountRepository;

    public Account createSavingsAccount(SavingsAccount savingsAccount){
        return savingsAccountRepository.save(savingsAccount);
    }
}
