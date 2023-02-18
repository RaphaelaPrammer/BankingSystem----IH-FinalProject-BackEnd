package com.ironhack.bankingsystemapp.controllers.impl;

import com.ironhack.bankingsystemapp.dtos.TransactionDTO;
import com.ironhack.bankingsystemapp.models.accounts.Account;
import com.ironhack.bankingsystemapp.models.users.AccountHolder;
import com.ironhack.bankingsystemapp.models.users.Address;
import com.ironhack.bankingsystemapp.repositories.users.AccountHolderRepository;
import com.ironhack.bankingsystemapp.services.TransactionService;
import com.ironhack.bankingsystemapp.services.accounts.AccountService;
import com.ironhack.bankingsystemapp.services.users.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accountholder-area")
public class AccountHolderController {

    @Autowired
    AccountService accountService;
    @Autowired
    AccountHolderService accountHolderService;
    @Autowired
    TransactionService transactionService;

@Autowired
    AccountHolderRepository accountHolderRepository;



    //--------- Get Balance of Account WITH AUTH------------!!!!!!!!!!! WORKING---
    @GetMapping("/accounts/my-balance-with-auth")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getBalanceTest2(@RequestParam Long accountId,Authentication authentication){
        return accountService.getBalanceAccountHolderWithAuth(accountId, authentication);
    }
    //--------- Get Balance of Account WITHOUT AUTH---------------
//    @GetMapping("/accounts/my-balance-without-auth")
//    @ResponseStatus(HttpStatus.OK)
//    public BigDecimal getBalance(@RequestParam Long accountId, @RequestParam Long ownerId){
//        return accountService.getBalanceAccountHolder(accountId, ownerId);
//    }


  // --------- Get List of Accounts  WITH AUTHENTICATION-----------
    @GetMapping("/my-accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getListAccounts(Authentication authentication){
        return accountHolderService.getListOfAccountsByUsername(authentication);
    }
    // --------- Get List of Accounts  WITHOUT AUTHENTICATION-----------
//    @GetMapping("/accounts/")
//    @ResponseStatus(HttpStatus.OK)
//            public List<Account> getAccounts(@RequestParam Long UserId){
//           return accountHolderService.getListOfAccountsById(UserId);
//    }

   // ----------- Update Account Info -------------
    @PatchMapping("/add-mailing-address")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder addMailingAddress(@RequestParam Long id, @RequestBody Address mailAddress){
        return accountHolderService.addMailingAddress(id, mailAddress);
    }
    @PutMapping("/update-address")
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

    // ----------- Transaction  WITH AUTHENTICATION--------------------
    @PostMapping("/transaction-with-auth")
    @ResponseStatus(HttpStatus.OK)
    public Account transferMoney(@RequestBody TransactionDTO transactionDTO, Authentication authentication){
        return transactionService.makeTransactionWithAUTH(transactionDTO, authentication);
    }
    // ----------- Transaction  WITHOUT AUTH -------------------------
    @PostMapping("/transaction-without-auth")
    @ResponseStatus(HttpStatus.OK)
    public Account transferMoney(@RequestBody TransactionDTO transactionDTO){
        return transactionService.makeTransactionWithoutAUTH(transactionDTO);
    }
    // ----------- GET LIST OF TRANSACTIONS -------------------
    @GetMapping("/transaction/all")
    @ResponseStatus(HttpStatus.OK)
    public List getListOfTransactions(Authentication authentication){
        return transactionService.getTransactionList(authentication);
    }


}
