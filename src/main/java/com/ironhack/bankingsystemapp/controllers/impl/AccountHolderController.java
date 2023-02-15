package com.ironhack.bankingsystemapp.controllers.impl;

import com.ironhack.bankingsystemapp.dtos.TransactionDTO;
import com.ironhack.bankingsystemapp.models.accounts.Account;
import com.ironhack.bankingsystemapp.models.users.AccountHolder;
import com.ironhack.bankingsystemapp.models.users.Address;
import com.ironhack.bankingsystemapp.services.TransactionService;
import com.ironhack.bankingsystemapp.services.accounts.AccountService;
import com.ironhack.bankingsystemapp.services.users.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Autowired
    TransactionService transactionService;




    //--------- Get Balance of Account --------------- ?????????????? with authentication ???
    @GetMapping("/accounts/my-balance")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getBalance(@RequestParam Long accountId, @RequestParam Long ownerId){
        return accountService.getBalanceAccountHolder(accountId, ownerId);
    }


  // --------- Get List of Accounts -----------
    @GetMapping("/my-accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getListAccounts(@AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        return accountHolderService.getListOfAccountsByUsername(username);
    }
//    @GetMapping("/accounts/")
//    @ResponseStatus(HttpStatus.OK)
//            public List<Account> getAccounts(@RequestParam Long UserId){
//           return accountHolderService.getListOfAccountsById(UserId);
//    }

   // ----------- Update Account Info -------------
    @PatchMapping("/add-mailing-address")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder addMailingAddress(@RequestParam Long id,@RequestBody Address mailAddress){
        return accountHolderService.addMailingAddress(id, mailAddress);
    }
    @PatchMapping("/update-address")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder updateAddressInfo(@RequestParam Long id, @RequestBody Address address){
        return accountHolderService.updateAddressInfo(id, address);
    }

    // -------- create  AccountHolder ----------
    @PostMapping("/create-user")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createAccountHolder(@RequestBody AccountHolder accountHolder){
        return accountHolderService.addAccountHolder(accountHolder);
    }

    // ----------- Transaction ----------------------??????????????? with USER DETAILS ??? --
    @PostMapping("/transaction")
    @ResponseStatus(HttpStatus.OK)
    public Account transferMoney(@RequestBody TransactionDTO transactionDTO){
        return transactionService.makeTransaction(transactionDTO);
    }

    @GetMapping("/transaction/all")
    @ResponseStatus(HttpStatus.OK)
    public List getListOfTransactions(@RequestParam Long id){
        return transactionService.getListOfTransactions(id);
    }

}
