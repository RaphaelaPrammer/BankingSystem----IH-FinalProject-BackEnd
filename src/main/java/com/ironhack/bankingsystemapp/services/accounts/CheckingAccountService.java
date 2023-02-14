package com.ironhack.bankingsystemapp.services.accounts;

import com.ironhack.bankingsystemapp.models.accounts.Account;
import com.ironhack.bankingsystemapp.models.accounts.CheckingAccount;
import com.ironhack.bankingsystemapp.models.accounts.StudentAccount;
import com.ironhack.bankingsystemapp.repositories.accounts.AccountRepository;
import com.ironhack.bankingsystemapp.repositories.accounts.CheckingAccountRepository;
import com.ironhack.bankingsystemapp.repositories.accounts.StudentAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class CheckingAccountService {

    @Autowired
    CheckingAccountRepository checkingAccountRepository;

@Autowired
    AccountRepository accountRepository;
@Autowired
    StudentAccountRepository studentAccountRepository;


// Create a new CheckingAccount  OR StudentAccount if age is below 24:
    public Account createCheckingAccount (CheckingAccount checkingAccount){
        // compares DOB of Primary Owner with Age 24 to determine if a Checking Account or Student Account is created --> <24y create student account.
        if(Period.between(checkingAccount.getPrimaryOwner().getDateOfBirth(), LocalDate.now()).getYears() <24){
            StudentAccount studentAccount = new StudentAccount(checkingAccount.getBalance(),checkingAccount.getPrimaryOwner(),checkingAccount.getSecretKey());
            return studentAccountRepository.save(studentAccount);
        } return checkingAccountRepository.save(checkingAccount);
    }

// Get a List with all Checking Accounts
    public List<CheckingAccount> getCheckingAccounts(){
        return checkingAccountRepository.findAll();
    }

}
