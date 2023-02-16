package com.ironhack.bankingsystemapp.services.accounts;

import com.ironhack.bankingsystemapp.models.accounts.Account;
import com.ironhack.bankingsystemapp.models.accounts.CheckingAccount;
import com.ironhack.bankingsystemapp.models.accounts.CreditCard;
import com.ironhack.bankingsystemapp.models.accounts.SavingsAccount;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

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


public List<Account> findAll(){
    return accountRepository.findAll();
}

public Account findById(Long id){
    return accountRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "This account does not exist"));
}

public Account findByName(String username){
    return accountRepository.findByPrimaryOwnerUsername(username).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no account for this Name"));
}

// deleteAccount(id) --> ADMIN RIGHT
public void deleteAccount(Long id){
    accountRepository.deleteById(id);
}

//------------------------------------------------------------------------
// INTEREST RATE
//public void applyInterestRateSavings(Long accountId){
//    SavingsAccount savAcc = savingsAccountRepository.findById(accountId).get();
//    // check if last Time the interest rate has been applied is more than one year, we add the interest rate to the balance.
//    if(Period.between(savAcc.getLastInterestRateApplied(), LocalDate.now()).getYears()>1){
//        savAcc.setBalance(savAcc.getBalance().add(savAcc.getBalance().multiply(savAcc.getInterestRate())));
//        // reset the lastInterestRateApplied Date
//        savAcc.setLastInterestRateApplied(savAcc.getLastInterestRateApplied().plusYears(1));
//        savingsAccountRepository.save(savAcc);
//    }
//}
//    public void applyInterestRateCredit(Long accountId){
//    CreditCard cC = creditCardRepository.findById(accountId).get();
//        // check if last Time the interest rate has been applied is more than one year, we add the interest rate to the balance.
//        if(Period.between(cC.getLastInterestRateApplied(),LocalDate.now()).getMonths()>1){
//           cC.setBalance(cC.getBalance().add(cC.getBalance().multiply(cC.getInterestRate())));
//            // reset the lastInterestRateApplied Date
//            cC.setLastInterestRateApplied(cC.getLastInterestRateApplied().plusMonths(1));
//            creditCardRepository.save(cC);
//        }
//    }
//------------------------------------------------------------------------
// MONTHLY  MAINTENANCE FEE

//    public void applyMaintenanceFee(Long accountId){
//    CheckingAccount checkAcc = checkingAccountRepository.findById(accountId).get();
//    if(Period.between(checkAcc.getLastMonthlyMaintenanceFeeApplied(), LocalDate.now()).getMonths()>1){
//        checkAcc.setBalance(checkAcc.getBalance().subtract(checkAcc.getMONTHLY_MAINTENANCE_FEE()));
//        checkAcc.setLastMonthlyMaintenanceFeeApplied(checkAcc.getLastMonthlyMaintenanceFeeApplied().plusMonths(1));
//        checkingAccountRepository.save(checkAcc);
//    }
//    }

//-----------------------------------------------------
// get Balance

    // as Admin
    public BigDecimal requestBalance(Long accountId){
        Account account = accountRepository.findById(accountId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "This Account does not exist"));

        //check if its Savings Account, and apply interest rate:
        if(account instanceof SavingsAccount){
            SavingsAccount savingAccount = (SavingsAccount) account;
            savingAccount.applyInterestRateSavings();
            savingAccount.applyPenaltyFeeSavings();
            accountRepository.save(savingAccount);
        }
//            //OR THIS WAY with Code from lines 58-67
//            if(account instanceof SavingsAccount){
//                applyInterestRateSavings(accountId);
//            }

        // check if its CreditCard, and apply interest rate:
        if(account instanceof CreditCard){
            CreditCard creditCard = (CreditCard) account;
            creditCard.applyInterestRateCredit();
            creditCard.applyPenaltyFeeCredit();
            accountRepository.save(creditCard);
        }
        // OR THIS WAY with code from lines 68-77
//            if(account instanceof CreditCard){
//                applyInterestRateCredit(accountId);
//            }

        // check if its Checking Account, and apply maintenance Fee:
        if(account instanceof CheckingAccount){
            CheckingAccount checkingACcount = (CheckingAccount) account;
            checkingACcount.applyMaintenanceFeeChecking();
            checkingACcount.applyPenaltyFeeChecking();
            accountRepository.save(checkingACcount);
        }
        //OR THIS WAY with Code from lines 81-88
//            if(account instanceof CheckingAccount){
//                applyMaintenanceFee(accountId);
//            }

        return accountRepository.findById(accountId).get().getBalance();

    }

    // as AccountHolder

    public BigDecimal getBalanceAccountHolder(Long accountId, Long ownerId) {
        // check if Account and Owner exist
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This Account does not exist"));
        AccountHolder owner = accountHolderRepository.findById(ownerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no Account with this Account Holder Id"));

        // check if the owner ID corresponds to the accountId
        if (account.getPrimaryOwner().getId() == ownerId || account.getSecondaryOwner().getId() == ownerId) {

            return requestBalance(accountId);

        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You don't have access to this account");
        }
    }

    // CON USER DETAILS
//    public BigDecimal getBalanceAccountHolder(Long accountId, UserDetails userDetails){
//        AccountHolder user = accountHolderRepository.findByUsername(userDetails.getUsername()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Username does not match"));
//
//        Account account = null;
//          //check if the given accountId matches one of the accountsIds for which the sender is Primary Owner or Secondary Owner of.
//        for(Account a : user.getPrimaryAccounts()){
//            if(a.getId() == accountId) {
//                account = a;
//            }else{
//                throw new ResponseStatusException(HttpStatus.CONFLICT);
//            }
//        }
//        for(Account a : user.getSecondaryAccounts()){
//            if(a.getId() == accountId) {
//                account = a;
//            }else{
//                throw new ResponseStatusException(HttpStatus.CONFLICT);
//            }
//        }
//            return getBalance(account.getId());
//    }





// modifyBalanceAdd -- ADMIN
public BigDecimal modifyBalanceAdd(Long id, BigDecimal amount){
    Account account = accountRepository.findById(id).get();
    account.setBalance(account.getBalance().add(amount));
    accountRepository.save(account);
    return account.getBalance();
}

// modifyBalanceSubtract -- ADMIN
public BigDecimal modifyBalanceSubtract(Long id, BigDecimal amount){
    Account account = accountRepository.findById(id).get();
    account.setBalance(account.getBalance().subtract(amount));
    accountRepository.save(account);
    return account.getBalance();
}



//  ?? change primary / secondary owner ??










}
