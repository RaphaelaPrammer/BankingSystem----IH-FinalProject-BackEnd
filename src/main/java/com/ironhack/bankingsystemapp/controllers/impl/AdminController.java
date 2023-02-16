package com.ironhack.bankingsystemapp.controllers.impl;

import com.ironhack.bankingsystemapp.dtos.StudentAccCheckingAccDTO;
import com.ironhack.bankingsystemapp.models.accounts.*;
import com.ironhack.bankingsystemapp.models.users.AccountHolder;
import com.ironhack.bankingsystemapp.models.users.Admin;
import com.ironhack.bankingsystemapp.models.users.ThirdParty;
import com.ironhack.bankingsystemapp.models.users.User;
import com.ironhack.bankingsystemapp.services.accounts.*;
import com.ironhack.bankingsystemapp.services.users.AccountHolderService;
import com.ironhack.bankingsystemapp.services.users.AdminService;
import com.ironhack.bankingsystemapp.services.users.ThirdPartyService;
import com.ironhack.bankingsystemapp.services.users.UserService;
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
    @Autowired
    CheckingAccountService checkingAccountService;
    @Autowired
    CreditCardService creditCardService;
    @Autowired
    SavingsAccountService savingsAccountService;
    @Autowired
    StudentAccountService studentAccountService;
    @Autowired
    UserService userService;
    @Autowired
    AdminService adminService;
    @Autowired
    AccountHolderService accountHolderService;
    @Autowired
    ThirdPartyService thirdPartyService;


    // ------------------------------------------------------------------------
    // -------------- A C C O U N T S ------------------------------------------
    // ------------------------------------------------------------------------
    //--------- Get Account(s)---------------
    @GetMapping("/accounts/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAccounts() {
        return accountService.findAll();
   }
    @GetMapping("/accounts/all/checking")
    @ResponseStatus(HttpStatus.OK)
    public List<CheckingAccount> getAllCheckings() {
        return checkingAccountService.getCheckingAccounts();
    }
    @GetMapping("/accounts/all/creditcards")
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCard> getAllCreditCards() {
        return creditCardService.getAllCreditCards();
    }
    @GetMapping("/accounts/all/savingsaccounts")
    @ResponseStatus(HttpStatus.OK)
    public List<SavingsAccount> getAllSavingAccounts() {
        return savingsAccountService.getAllSavingsAccounts();
    }
    @GetMapping("/accounts/all/studentaccounts")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentAccount> getAllStudentAccounts() {
        return studentAccountService.getAllStudentAccounts();
    }
    @GetMapping("/accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccountById(@PathVariable Long id) {
        return accountService.findById(id);
    }

    @GetMapping("/accounts")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccountByName(@RequestParam String userName) {
        return accountService.findByName(userName);
    }



    //--------- Delete Account ---------------
    @DeleteMapping("/accounts")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void deleteAccount(@RequestParam Long id){
        accountService.deleteAccount(id);
    }

    //--------- Get Balance of Account ---------------
     @GetMapping("/accounts/balance")
     @ResponseStatus(HttpStatus.OK)
     public BigDecimal getBalance(@RequestParam Long id){
         return accountService.requestBalance(id);
    }
    //--------- Modify Balance---------------
    @PatchMapping("/accounts/update-balance-add")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal updateBalanceAdd(@RequestParam Long id, @RequestParam BigDecimal amount){
        return accountService.modifyBalanceAdd(id, amount);
    }
    @PatchMapping("/accounts/update-balance-subtract")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal updateBalanceSubtract(@RequestParam Long id, @RequestParam BigDecimal amount){
        return accountService.modifyBalanceSubtract(id, amount);
    }

    //--------- create New Accounts---------------
    @PostMapping("/accounts/new/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createCheckingAccount(@RequestBody StudentAccCheckingAccDTO studentAccCheckingAccDTO){
        return checkingAccountService.createCheckingAccount(studentAccCheckingAccDTO);
    }
    @PostMapping("/accounts/new/credit")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createCreditCard(@RequestBody CreditCard creditCard){
        return creditCardService.createCreditCard(creditCard);
    }
    @PostMapping("/accounts/new/savings")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createSavingsAccount(@RequestBody SavingsAccount savingsAccount){
        return savingsAccountService.createSavingsAccount(savingsAccount);
    }
    //-----------------does not work-------------------!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @PostMapping("/accounts/new/student")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createStudentAccount(@RequestBody StudentAccCheckingAccDTO studentAccCheckingAccDTO){
        return studentAccountService.createStudentAccount(studentAccCheckingAccDTO);
    }




    // ------------------------------------------------------------------------
    // -------------- U S E R S  ----------------------------------------------
    // ------------------------------------------------------------------------

    // --------- create Users ------------
    @PostMapping("/users/new/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin createAdmin(@RequestBody Admin admin){
        return adminService.addAdmin(admin);
    }
    @PostMapping("/users/new/accountholder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createAccountHolder(@RequestBody AccountHolder accountHolder){
        return accountHolderService.addAccountHolder(accountHolder);
    }
    @PostMapping("/users/new/thirdparty")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty createThirdParty(@RequestBody ThirdParty thirdParty){
        return thirdPartyService.addThirdParty(thirdParty);
    }

    // --------- delete Users ------------

    //-----------------does not work-------------------!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @DeleteMapping("/users/delete")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void deleteUser(@RequestParam Long id){
        userService.deleteUser(id);
    }

    // --------- get Users ------------
    @GetMapping("/users/all")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers(){
        return userService.getUsers();
    }
    @GetMapping("/users/{username}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable String username){
        return userService.getUser(username);
    }


}
