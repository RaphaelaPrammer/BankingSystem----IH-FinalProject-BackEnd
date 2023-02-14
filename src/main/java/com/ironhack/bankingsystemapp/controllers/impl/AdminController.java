package com.ironhack.bankingsystemapp.controllers.impl;

import com.ironhack.bankingsystemapp.models.accounts.Account;
import com.ironhack.bankingsystemapp.models.users.User;
import com.ironhack.bankingsystemapp.services.accounts.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/admin-area")
public class AdminController {

    @Autowired
    AccountService accountService;



    @GetMapping("/accounts/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAccounts() {
        return accountService.findAll();
   }
 @GetMapping("/accounts/balance/{id}")
 @ResponseStatus(HttpStatus.OK)
    public BigDecimal getBalance(@PathVariable Long id){
        return accountService.getBalance(id);

 }
 @PatchMapping("/update-balance-add")
 @ResponseStatus(HttpStatus.OK)
    public BigDecimal updateBalanceAdd(@RequestParam Long id, @RequestParam BigDecimal amount){
        return accountService.modifyBalanceAdd(id, amount);
 }
    @PatchMapping("/update-balance-subtract")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal updateBalanceSubtract(@RequestParam Long id, @RequestParam BigDecimal amount){
        return accountService.modifyBalanceSubtract(id, amount);
    }

}
