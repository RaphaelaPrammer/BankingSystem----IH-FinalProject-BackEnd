package com.ironhack.bankingsystemapp.services.accounts;

import com.ironhack.bankingsystemapp.dtos.StudentAccCheckingAccDTO;
import com.ironhack.bankingsystemapp.models.accounts.Account;
import com.ironhack.bankingsystemapp.models.accounts.CheckingAccount;
import com.ironhack.bankingsystemapp.models.accounts.StudentAccount;
import com.ironhack.bankingsystemapp.models.users.AccountHolder;
import com.ironhack.bankingsystemapp.repositories.accounts.AccountRepository;
import com.ironhack.bankingsystemapp.repositories.accounts.CheckingAccountRepository;
import com.ironhack.bankingsystemapp.repositories.accounts.StudentAccountRepository;
import com.ironhack.bankingsystemapp.repositories.users.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class StudentAccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    StudentAccountRepository studentAccountRepository;
    @Autowired
    CheckingAccountRepository checkingAccountRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;


    // Create a new StudentAccount if age is below 24, otherwise checking account:
        public Account createStudentAccount (StudentAccCheckingAccDTO studentAccCheckingAccDTO){
        // compares DOB of Primary Owner with Age 24 to determine if a Checking Account or Student Account is created --> <24y create student account.
            AccountHolder owner = accountHolderRepository.findById(studentAccCheckingAccDTO.getPrimaryOwnerId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

            if(Period.between(owner.getDateOfBirth(), LocalDate.now()).getYears() <24){
                StudentAccount studentAccount = new StudentAccount(studentAccCheckingAccDTO.getBalance(), owner, studentAccCheckingAccDTO.getSecretKey());
                return studentAccountRepository.save(studentAccount);
        } else{
            CheckingAccount checkingAccount = new CheckingAccount(studentAccCheckingAccDTO.getBalance(), owner, studentAccCheckingAccDTO.getSecretKey());
            return checkingAccountRepository.save(checkingAccount);
        }
    }

    // Get a List with all Student Accounts
    public List<StudentAccount> getAllStudentAccounts(){
        return studentAccountRepository.findAll();
    }

}
