package com.ironhack.bankingsystemapp.services.accounts;

import com.ironhack.bankingsystemapp.models.accounts.Account;
import com.ironhack.bankingsystemapp.models.accounts.CheckingAccount;
import com.ironhack.bankingsystemapp.models.accounts.StudentAccount;
import com.ironhack.bankingsystemapp.repositories.accounts.AccountRepository;
import com.ironhack.bankingsystemapp.repositories.accounts.CheckingAccountRepository;
import com.ironhack.bankingsystemapp.repositories.accounts.StudentAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class StudentAccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    StudentAccountRepository studentAccountRepository;
    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    // Create a new StudentAccount if age is below 24, otherwise checking account:
    public Account createStudentAccount (StudentAccount studentAccount){
        // compares DOB of Primary Owner with Age 24 to determine if a Checking Account or Student Account is created --> <24y create student account.
        if(Period.between(studentAccount.getPrimaryOwner().getDateOfBirth(), LocalDate.now()).getYears() <24){
           return studentAccountRepository.save(studentAccount);
        } else{
            CheckingAccount checkingAccount = new CheckingAccount(studentAccount.getBalance(), studentAccount.getPrimaryOwner(), studentAccount.getSecretKey());
            return checkingAccountRepository.save(checkingAccount);
        }
    }


}
