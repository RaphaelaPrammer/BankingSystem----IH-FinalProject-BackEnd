package com.ironhack.bankingsystemapp.controllers.impl;

import com.ironhack.bankingsystemapp.dtos.StudentAccCheckingAccDTO;
import com.ironhack.bankingsystemapp.models.accounts.*;
import com.ironhack.bankingsystemapp.models.users.*;
import com.ironhack.bankingsystemapp.repositories.users.RoleRepository;
import com.ironhack.bankingsystemapp.repositories.users.UserRepository;
import com.ironhack.bankingsystemapp.services.accounts.*;
import com.ironhack.bankingsystemapp.services.users.AccountHolderService;
import com.ironhack.bankingsystemapp.services.users.AdminService;
import com.ironhack.bankingsystemapp.services.users.ThirdPartyService;
import com.ironhack.bankingsystemapp.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collection;
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

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;


    // ------------------------------------------------------------------------
    // -------------- A C C O U N T S ------------------------------------------
    // ------------------------------------------------------------------------
    //--------- Get Account(s)---------------
    //--------- Get All Accounts---------------
    @GetMapping("/accounts/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAccounts() {
        return accountService.findAll();
   }

    //--------- Get All Checking Accounts---------------
    @GetMapping("/accounts/all/checking")
    @ResponseStatus(HttpStatus.OK)
    public List<CheckingAccount> getAllCheckings() {
        return checkingAccountService.getCheckingAccounts();
    }

    //--------- Get All Credit Cards---------------
    @GetMapping("/accounts/all/creditcards")
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCard> getAllCreditCards() {
        return creditCardService.getAllCreditCards();
    }

    //--------- Get All Savings Accounts---------------
    @GetMapping("/accounts/all/savingsaccounts")
    @ResponseStatus(HttpStatus.OK)
    public List<SavingsAccount> getAllSavingAccounts() {
        return savingsAccountService.getAllSavingsAccounts();
    }

    //--------- Get All Student Accounts---------------
    @GetMapping("/accounts/all/studentaccounts")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentAccount> getAllStudentAccounts() {
        return studentAccountService.getAllStudentAccounts();
    }

    //--------- Get Account by AccountID---------------
    @GetMapping("/accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccountById(@PathVariable Long id) {
        return accountService.findById(id);
    }


    // --------- Get List of Accounts of User by Username -----------
    @GetMapping("/accounts/user")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getListAccounts(@RequestParam String username){
        return accountHolderService.getListOfAccountsByUsernameAdmin(username);
    }
    //---------------------------------------------------
    //--------- Delete Account by AccountID---------------
    @DeleteMapping("/accounts")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void deleteAccount(@RequestParam Long id){
        accountService.deleteAccount(id);
    }

    //---------------------------------------------------
    //--------- Get Balance of Account by AccountId---------------
     @GetMapping("/accounts/balance")
     @ResponseStatus(HttpStatus.OK)
     public BigDecimal getBalance(@RequestParam Long id){
         return accountService.requestBalance(id);
    }

    //--------- Modify Balance ADD - of Account by AccountId----------------
    @PatchMapping("/accounts/update-balance-add")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal updateBalanceAdd(@RequestParam Long id, @RequestParam BigDecimal amount){
        return accountService.modifyBalanceAdd(id, amount);
    }

    //--------- Modify Balance SUBTRACT - of Account by AccountId----------------
    @PatchMapping("/accounts/update-balance-subtract")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal updateBalanceSubtract(@RequestParam Long id, @RequestParam BigDecimal amount){
        return accountService.modifyBalanceSubtract(id, amount);
    }
    //---------- ADD NEW ACCOUNT---------------------------
    //--------- create New Checking Account ---------------
    @PostMapping("/accounts/new/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createCheckingAccount(@RequestBody StudentAccCheckingAccDTO studentAccCheckingAccDTO){
        return checkingAccountService.createCheckingAccount(studentAccCheckingAccDTO);
    }

    //--------- create New Credit Card Account ---------------
    @PostMapping("/accounts/new/credit")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createCreditCard(@RequestBody CreditCard creditCard){
        return creditCardService.createCreditCard(creditCard);
    }

    //--------- create New Savings Account ---------------
    @PostMapping("/accounts/new/savings")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createSavingsAccount(@RequestBody SavingsAccount savingsAccount){
        return savingsAccountService.createSavingsAccount(savingsAccount);
    }

    //--------- create New Student Account ---------------
    @PostMapping("/accounts/new/student")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createStudentAccount(@RequestBody StudentAccCheckingAccDTO studentAccCheckingAccDTO){
        return studentAccountService.createStudentAccount(studentAccCheckingAccDTO);
    }




    // ------------------------------------------------------------------------
    // -------------- U S E R S  ----------------------------------------------
    // ------------------------------------------------------------------------

    // -------------- ADD USERS --------------------
    //--------- create New Admin User ---------------
    @PostMapping("/users/new/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin createAdmin(@RequestBody Admin admin){
        return adminService.addAdmin(admin);
    }

    //--------- create New Account Holder User ---------------
    @PostMapping("/users/new/accountholder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createAccountHolder(@RequestBody AccountHolder accountHolder){
        return accountHolderService.addAccountHolder(accountHolder);
    }

    //--------- create New Third Party User ---------------
    @PostMapping("/users/new/thirdparty")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty createThirdParty(@RequestBody ThirdParty thirdParty){
        return thirdPartyService.addThirdParty(thirdParty);
    }
    //--------- --------- ---------------
    // --------- delete Users ------------
    @DeleteMapping("/users/delete")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void deleteUser(@RequestParam Long id){
        userService.deleteUser(id);
    }

    // --------- get Users + Info ---------------
    // --------- get All Users + Info ------------
    @GetMapping("/users/all")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers(){
        return userService.getUsers();
    }

    // --------- get User Info by Username ------------
    @GetMapping("/users/{username}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable String username){
        return userService.getUser(username);
    }


    // ------------- find Role of User -----------
    @GetMapping("/roles/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Role> getRoleByUserId(@PathVariable Long userId){

        Collection<Role> roleList = userRepository.findById(userId).get().getRoles();

        return roleList;
    }

    // ---------- add Role to User -----------
    @PatchMapping("roles/add-role-to-user")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addRoleToUser(@RequestParam String userName, String roleName){
        userService.addRoleToUser(userName,roleName);
    }

    // ------------- add Role ----------------
    @PostMapping("/roles/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Role saveRole(@RequestBody Role role) {
       return userService.saveRole(role);
    }

    // ---------- get all Roles -----------
    @GetMapping("/roles/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

}
