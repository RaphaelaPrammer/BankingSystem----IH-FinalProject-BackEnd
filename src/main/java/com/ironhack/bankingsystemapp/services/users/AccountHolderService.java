package com.ironhack.bankingsystemapp.services.users;

import com.ironhack.bankingsystemapp.models.accounts.Account;
import com.ironhack.bankingsystemapp.models.users.*;
import com.ironhack.bankingsystemapp.repositories.accounts.AccountRepository;
import com.ironhack.bankingsystemapp.repositories.users.AccountHolderRepository;
import com.ironhack.bankingsystemapp.repositories.users.RoleRepository;
import com.ironhack.bankingsystemapp.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AccountHolderService {

    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    // create an Account Holder User, encode the password, add it to the DB and add the Role "ACCOUNT-HOLDER" to it.
    public AccountHolder addAccountHolder(AccountHolder accountHolder){
        accountHolder.setPassword(passwordEncoder.encode(accountHolder.getPassword()));
        AccountHolder newAccountHolder = accountHolderRepository.save(accountHolder);
        Role role = roleRepository.findByRole("ACCOUNT-HOLDER");
        newAccountHolder.getRoles().add(role);
        accountHolderRepository.save(newAccountHolder);
        return newAccountHolder;
    }

    // Get a List of all Accounts of the Owner --- ACCOUNT-HOLDER
    public List<Account> getListOfAccountsByUsername(Authentication authentication){
        AccountHolder user = accountHolderRepository.findByUsername(authentication.getName().toString()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
              // get the List of the primary Accounts with this id
     return user.getPrimaryAccounts();
    }

    // Get a List of all Accounts of the Owner --- ADMIN
    public List<Account> getListOfAccountsByUsernameAdmin(String username){
        // check if there is a user with the given id
        AccountHolder accountHolder1 = accountHolderRepository.findByUsername(username).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Holder not found"));

        // get the List of the primary Accounts with this id
        return accountHolder1.getPrimaryAccounts();
    }

// add Mailing address to Account Holder info
    public AccountHolder addMailingAddress(Long userId, Address mailAddress){
        // check if the Account exists.
        AccountHolder accountHolderFromDB = accountHolderRepository.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Holder does not exist"));
        accountHolderFromDB.setMailingAddress(mailAddress);
        return accountHolderFromDB;
    }

    // update Address Info in Account Holder Info
    public AccountHolder updateAddressInfo(Long userId, Address address){
       // check if the Account exists.
        AccountHolder accountHolderFromDB = accountHolderRepository.findById(userId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Holder not found"));
        // get the existing Address Information from the Database.
        Address addressFromDB = accountHolderFromDB.getPrimaryAddress();

        // Overwrite existing Address Information with the new Address inputs, if one field of the new Address is empty, the existing Info will not be overwritten.
        if(!address.getAddressName().isEmpty()) addressFromDB.setAddressName(address.getAddressName());
        if(!address.getCity().isEmpty()) addressFromDB.setCity(address.getCity());
        if(!address.getCountry().isEmpty()) addressFromDB.setCountry(address.getCountry());
        if(!address.getPostalCode().isEmpty()) addressFromDB.setPostalCode(address.getPostalCode());

        //assign the updated Address again to the Account Holder Info and save it in the Database.
        accountHolderFromDB.setPrimaryAddress(addressFromDB);
        return accountHolderRepository.save(accountHolderFromDB);
    }













}
