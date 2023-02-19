package com.ironhack.bankingsystemapp.services.accounts;

import com.ironhack.bankingsystemapp.models.accounts.*;
import com.ironhack.bankingsystemapp.models.users.AccountHolder;
import com.ironhack.bankingsystemapp.models.users.User;
import com.ironhack.bankingsystemapp.repositories.accounts.AccountRepository;
import com.ironhack.bankingsystemapp.repositories.accounts.CheckingAccountRepository;
import com.ironhack.bankingsystemapp.repositories.accounts.CreditCardRepository;
import com.ironhack.bankingsystemapp.repositories.accounts.SavingsAccountRepository;
import com.ironhack.bankingsystemapp.repositories.users.AccountHolderRepository;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;


@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    SavingsAccountRepository savingsAccountRepository;
    @Autowired
    CreditCardRepository creditCardRepository;
    @Autowired
    CheckingAccountRepository checkingAccountRepository;

// --------- get a List of all Accounts ---------
public List<Account> findAll(){
    return accountRepository.findAll();
}

// --------- get Account by AccountId--------------
public Account findById(Long id){
    return accountRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "This account does not exist"));
}

// ------- get Account by Username ----------
public Account findByName(String username){
    return accountRepository.findByPrimaryOwnerUsername(username).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no account for this Name"));
}

// ---------- delete Account by Account Id ----------
public void deleteAccount(Long id){
    accountRepository.deleteById(id);
}


//----------Get Balance Information -------------------------------------------

// ------- as Admin, get Balance by Account Id ----------
    public BigDecimal requestBalance(Long accountId){
    // retrieve the account from the DB.
    Account account = accountRepository.findById(accountId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "This Account does not exist"));

        //check if its Savings Account, and apply interest rate:
        if(account instanceof SavingsAccount){
            SavingsAccount savingAccount = (SavingsAccount) account;
            savingAccount.applyInterestRateSavings();
            savingAccount.applyPenaltyFeeSavings();
            accountRepository.save(savingAccount);
            return accountRepository.findById(savingAccount.getId()).get().getBalance();
        }

        // check if its CreditCard, and apply interest rate:
        if(account instanceof CreditCard){
            CreditCard creditCard = (CreditCard) account;
            creditCard.applyInterestRateCredit();
            creditCard.applyPenaltyFeeCredit();
            accountRepository.save(creditCard);
            return accountRepository.findById(creditCard.getId()).get().getBalance();
        }

        // check if its Checking Account, and apply maintenance Fee:
        if(account instanceof CheckingAccount){
            CheckingAccount checkingACcount = (CheckingAccount) account;
            checkingACcount.applyMaintenanceFeeChecking();
            checkingACcount.applyPenaltyFeeChecking();
            accountRepository.save(checkingACcount);
            return accountRepository.findById(checkingACcount.getId()).get().getBalance();
        }
        if(account instanceof StudentAccount){
            StudentAccount studentAccount = (StudentAccount) account;
            // no Penalty Fee or Maintenance Fee for Student Accounts
                //studentAccount.applyMaintenanceFeeChecking();
                //studentAccount.applyPenaltyFeeChecking();
            accountRepository.save(studentAccount);
            return accountRepository.findById(studentAccount.getId()).get().getBalance();
        }

        //return accountRepository.findById(accountId).get().getBalance();
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    // -------- as AccountHolder, WITH AUTHENTICATION -----------------
    public BigDecimal getBalanceAccountHolderWithAuth(Long accountId, Authentication authentication){
        AccountHolder user = accountHolderRepository.findByUsername(authentication.getName().toString()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not Authorized"));

        Account account = null;
        //check if the given accountId matches one of the accountsIds for which the sender is Primary Owner or Secondary Owner of.
        for(Account a : user.getPrimaryAccounts()){
            if(a.getId().equals(accountId)) {
                // call the function for retrieving the balance to check for penalty fee and interest rate.
                return requestBalance(accountId);
                //return accountRepository.findById(a.getId()).get().getBalance();
            }
        }
        for(Account a : user.getSecondaryAccounts()){
            if(a.getId().equals(accountId)) {
                // call the function for retrieving the balance to check for penalty fee, interest rate.
               return requestBalance(accountId);
                //return accountRepository.findById(a.getId()).get().getBalance();
            }
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT);
    }


// ----------------  modifyBalance - Add Money to the account -- ADMIN -------------------
public BigDecimal modifyBalanceAdd(Long id, BigDecimal amount){
    Account account = accountRepository.findById(id).get();
    account.setBalance(account.getBalance().add(amount));
    accountRepository.save(account);
    return account.getBalance();
}

// --------------- modifyBalance Subtract money from the account  -- ADMIN
public BigDecimal modifyBalanceSubtract(Long id, BigDecimal amount){
    Account account = accountRepository.findById(id).get();
    account.setBalance(account.getBalance().subtract(amount));
    accountRepository.save(account);
    return account.getBalance();
}

}
