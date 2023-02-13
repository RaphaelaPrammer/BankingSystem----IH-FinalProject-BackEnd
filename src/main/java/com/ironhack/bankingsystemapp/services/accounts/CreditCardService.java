package com.ironhack.bankingsystemapp.services.accounts;

import com.ironhack.bankingsystemapp.models.accounts.Account;
import com.ironhack.bankingsystemapp.models.accounts.CreditCard;
import com.ironhack.bankingsystemapp.repositories.accounts.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardService {

    @Autowired
    CreditCardRepository creditCardRepository;

public Account createCreditCard(CreditCard creditCard){
    return creditCardRepository.save(creditCard);
}

}
