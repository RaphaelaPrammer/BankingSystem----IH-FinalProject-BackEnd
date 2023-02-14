package com.ironhack.bankingsystemapp.services.accounts;

import com.ironhack.bankingsystemapp.models.accounts.Account;
import com.ironhack.bankingsystemapp.models.accounts.CreditCard;
import com.ironhack.bankingsystemapp.repositories.accounts.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardService {

    @Autowired
    CreditCardRepository creditCardRepository;

public Account createCreditCard(CreditCard creditCard){
    return creditCardRepository.save(creditCard);
}


// get a List with all Credit Card Accounts
    public List<CreditCard> getAllCreditCards(){
    return creditCardRepository.findAll();
    }
}
