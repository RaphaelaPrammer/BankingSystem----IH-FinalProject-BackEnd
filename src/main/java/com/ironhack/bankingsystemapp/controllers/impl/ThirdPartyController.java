package com.ironhack.bankingsystemapp.controllers.impl;

import com.ironhack.bankingsystemapp.dtos.ThirdPartyTransactionDTO;
import com.ironhack.bankingsystemapp.dtos.TransactionDTO;
import com.ironhack.bankingsystemapp.models.accounts.Account;
import com.ironhack.bankingsystemapp.services.TransactionService;
import com.ironhack.bankingsystemapp.services.users.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/third-party-area")
public class ThirdPartyController {

@Autowired
    ThirdPartyService thirdPartyService;
@Autowired
    TransactionService transactionService;

    //------ Third Party Transactions -----------------
    // -------- Send Money away from an Account -------
@PostMapping("/transaction/send-money")
    @ResponseStatus(HttpStatus.OK)
    public Account thirdPartyTransactionSendMoney(@RequestHeader String hashedKey, @RequestBody ThirdPartyTransactionDTO thirdPartyTransactionDTO){
    return transactionService.thirdPartyTransactionSendMoney(hashedKey,thirdPartyTransactionDTO);
}
    // -------- Receive Money to an Account ------------
@PostMapping("/transaction/receive-money")
    @ResponseStatus(HttpStatus.OK)
    public Account thirdPartyTransactionReceiveMoney (@RequestHeader String hashedKey, @RequestBody ThirdPartyTransactionDTO thirdPartyTransactionDTO){
    return transactionService.thirdPartyTransactionReceiveMoney(hashedKey, thirdPartyTransactionDTO);
}

}
