package com.ironhack.bankingsystemapp.controllers.impl;

import com.ironhack.bankingsystemapp.models.accounts.Account;
import com.ironhack.bankingsystemapp.models.users.AccountHolder;
import com.ironhack.bankingsystemapp.models.users.Address;
import com.ironhack.bankingsystemapp.services.accounts.AccountService;
import com.ironhack.bankingsystemapp.services.users.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accountholder-area")
public class AccountHolderController {

    @Autowired
    AccountService accountService;
    @Autowired
    AccountHolderService accountHolderService;




    //--------- Get Balance of Account ---------------
    @GetMapping("/accounts/balance")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getBalance(@RequestParam Long accountId, @RequestParam Long ownerId){
        return accountService.getBalanceAccountHolder(accountId, ownerId);
    }


  // --------- Get List of Accounts -----------
    @GetMapping("/accounts/{username}")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAccounts(@PathVariable String username){
        return accountHolderService.getListOfAccountsByUsername(username);
    }
    @GetMapping("/accounts/")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAccounts(@RequestParam Long UserId){
        return accountHolderService.getListOfAccountsById(UserId);
    }

   // ----------- Update Account Info -------------
    @PatchMapping("/add-mailing-address")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder addMailingAddress(@RequestParam Long id,@RequestParam Address mailAddress){
        return accountHolderService.addMailingAddress(id, mailAddress);
    }
    @PatchMapping("/update-address")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder updateAddressInfo(@RequestParam Long id, @RequestParam Address address){
        return accountHolderService.updateAddressInfo(id, address);
    }

    // ----------- Transaction --------------



}
