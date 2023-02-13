package com.ironhack.bankingsystemapp.services.accounts;

import com.ironhack.bankingsystemapp.models.accounts.Account;
import com.ironhack.bankingsystemapp.models.users.AccountHolder;
import com.ironhack.bankingsystemapp.repositories.accounts.AccountRepository;
import com.ironhack.bankingsystemapp.repositories.users.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;



public List<Account> findAll(){
    return accountRepository.findAll();
}

public Account findById(Long id){
    return accountRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "This account does not exist"));
}

public Account findByName(String ownerName){
    return accountRepository.findByPrimaryOwnerName(ownerName).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no account for this Name"));
}

// deleteAccount(id) --> ADMIN RIGHT
public void deleteAccount(Long id){
    accountRepository.deleteById(id);
}


// get Balance

    // as Admin
    public BigDecimal getBalanceAdmin(Long accountId){
       Account account = accountRepository.findById(accountId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "This Account does not exist"));

       return account.getBalance();

    }
    // as AccountHolder
    public BigDecimal getBalanceAccountHolder(Long accountId, Long ownerId){
    // check if Account and Owner exist
        Account account = accountRepository.findById(accountId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "This Account does not exist"));
        AccountHolder owner = accountHolderRepository.findById(ownerId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no Account with this Account Holder Id"));

    // check if the owner ID corresponds to the accountId
        if(account.getPrimaryOwner().getId()==ownerId || account.getSecondaryOwner().getId()==ownerId){
            return account.getBalance();
        }else{
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You don't have access to this account");
        }
    }

    // get Balance for Third Party?????
public BigDecimal getBalanceThirdParty(Long accountId, Long thirdPartyId){
return null;
}



// modifyBalanceAdd



// modifyBalanceSubstract




//  ?? change primary / secondary owner ??










}
