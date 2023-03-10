package com.ironhack.bankingsystemapp.services;

import com.ironhack.bankingsystemapp.dtos.ThirdPartyTransactionDTO;
import com.ironhack.bankingsystemapp.dtos.TransactionDTO;
import com.ironhack.bankingsystemapp.models.Transaction;
import com.ironhack.bankingsystemapp.models.accounts.*;
import com.ironhack.bankingsystemapp.models.users.AccountHolder;
import com.ironhack.bankingsystemapp.models.users.ThirdParty;
import com.ironhack.bankingsystemapp.models.users.User;
import com.ironhack.bankingsystemapp.repositories.accounts.AccountRepository;
import com.ironhack.bankingsystemapp.repositories.accounts.TransactionRepository;
import com.ironhack.bankingsystemapp.repositories.users.AccountHolderRepository;
import com.ironhack.bankingsystemapp.repositories.users.ThirdPartyRepository;
import com.ironhack.bankingsystemapp.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ThirdPartyRepository thirdPartyRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;


    public Account makeTransactionWithAUTH(TransactionDTO transactionDTO, Authentication authentication){
        // get the receiving account from the DTO.
        Account receiverAccount = accountRepository.findById(transactionDTO.getReceiverAccountId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"No Account with this id"));
        //-------
        // without Authentication:
        //Account senderAccount = accountRepository.findById(transactionDTO.getSenderAccountId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"No Account with this id"));
        //------

        // with Authentication

       AccountHolder sender = accountHolderRepository.findByUsername(authentication.getName().toString()).orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized "));
       //check if the senderId from the transactionDTO matches one of the accountsIds for which the sender is Primary Owner or Secondary Owner of.
        Account senderAccount1=null;
       for(Account pa : sender.getPrimaryAccounts()){
           if(pa.getId().equals(transactionDTO.getSenderAccountId())) {
               senderAccount1 = pa;
           }
       }
        for(Account sa : sender.getSecondaryAccounts()){
            if(sa.getId().equals(transactionDTO.getSenderAccountId())) {
                senderAccount1 = sa;
            }
        }
        Account senderAccount = accountRepository.findById(senderAccount1.getId()).get();

        // check if there are enough funds on the sending account.
        if(senderAccount.getBalance().compareTo(transactionDTO.getAmount())<0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough funds on this account.");
        }
        // Check if Name of Receiver matches the Receiving account name.
        if(!receiverAccount.getPrimaryOwner().getName().equals(transactionDTO.getReceiverName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The name of the receiver doesn't match with the receiving account");
        }

        // subtract the transfer amount from the sending account
        // check if sufficient funds are provided on the sending account
        if(senderAccount.getBalance().compareTo(transactionDTO.getAmount())<0){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough funds to to this operation");
        } else{
            // deduct the money to the sending account
            senderAccount.setBalance(senderAccount.getBalance().subtract(transactionDTO.getAmount()));;
        }


        // Check for Penalty Fee and Interest Rate and Maintenance Fee
        //check Type of account
        if(senderAccount instanceof SavingsAccount){
            ((SavingsAccount) senderAccount).applyPenaltyFeeSavings();
            ((SavingsAccount) senderAccount).applyInterestRateSavings();
            accountRepository.save(senderAccount);
            return senderAccount;
        }
        // check if its CreditCard, and apply interest rate:
        if(senderAccount instanceof CreditCard){
            ((CreditCard) senderAccount).applyPenaltyFeeCredit();
            ((CreditCard) senderAccount).applyInterestRateCredit();
            accountRepository.save(senderAccount);
            return senderAccount;
        }
        // check if its Checking Account, and apply maintenance Fee:
        if(senderAccount instanceof CheckingAccount){
            ((CheckingAccount) senderAccount).applyPenaltyFeeChecking();
            ((CheckingAccount) senderAccount).applyMaintenanceFeeChecking();
            accountRepository.save(senderAccount);
            return senderAccount;
        }
        // check if its Student Account:
        if(senderAccount instanceof StudentAccount){
            accountRepository.save(senderAccount);
            return senderAccount;
        }

        // save the sender Account with new balance
        //accountRepository.save(senderAccount);

        // add the money to the receiving account
        receiverAccount.setBalance(receiverAccount.getBalance().add(transactionDTO.getAmount()));

        // save receiving account with new balance
        accountRepository.save(receiverAccount);

        // save the transaction in the Database
        Transaction transaction = transactionRepository.save(new Transaction(senderAccount, receiverAccount, transactionDTO.getReceiverName(), transactionDTO.getAmount()));
        transactionRepository.save(transaction);

        //return senderAccount;
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    public Account thirdPartyTransactionReceiveMoney(String hashedKey, ThirdPartyTransactionDTO thirdPartyTransactionDTO){
        Account receiverAccount = accountRepository.findById(thirdPartyTransactionDTO.getAccountId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"This account does not exist"));

        // check if its a credit card
        if(receiverAccount instanceof CreditCard){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You cannot send money to a credit card");
        }
        // Check if Secret Key which was passed from the ThirdParty  matches with secret Key of the account
        // secret Key depends on the type of account
        String secretKey = null;
        if(receiverAccount instanceof CheckingAccount){
            secretKey = ((CheckingAccount) receiverAccount).getSecretKey();
        }
        if(receiverAccount instanceof SavingsAccount){
            secretKey = ((SavingsAccount) receiverAccount).getSecretKey();
        }
        if(receiverAccount instanceof StudentAccount){
            secretKey= ((StudentAccount) receiverAccount).getSecretKey();
        }

        if(!thirdPartyTransactionDTO.getSecretKey().equals(secretKey)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The secret Key is not correct");
        }

        ThirdParty thirdParty = thirdPartyRepository.findById(thirdPartyTransactionDTO.getThirdPartyId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Third Party not found with this id"));

        // Check if hashed Key provided by the Third party matches the one from the DB
        if(!hashedKey.equals(thirdParty.getHashedKey())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The hashed Key provided does not match the one in the Database for this Third Party user");
        }

        // add the money to the receiving account
        receiverAccount.setBalance(receiverAccount.getBalance().add(thirdPartyTransactionDTO.getAmount()));

        // Check for Penalty Fee and Interest Rate and Maintenance Fee
        //check Type of account
        if(receiverAccount instanceof SavingsAccount){
            ((SavingsAccount) receiverAccount).applyPenaltyFeeSavings();
            ((SavingsAccount) receiverAccount).applyInterestRateSavings();
            accountRepository.save(receiverAccount);
            return receiverAccount;
        }
        // check if its Checking Account, and apply maintenance Fee:
        if(receiverAccount instanceof CheckingAccount){
            ((CheckingAccount) receiverAccount).applyPenaltyFeeChecking();
            ((CheckingAccount) receiverAccount).applyMaintenanceFeeChecking();
            accountRepository.save(receiverAccount);
            return receiverAccount;
        }
        // check if its Student Account:
        if(receiverAccount instanceof StudentAccount){
            accountRepository.save(receiverAccount);
            return receiverAccount;
        }

        // save the receiving account to the DB
        //accountRepository.save(receiverAccount);

        // save the transaction
        Transaction transaction = transactionRepository.save(new Transaction(null, receiverAccount, receiverAccount.getPrimaryOwner().getName(),thirdPartyTransactionDTO.getAmount()));

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    public Account thirdPartyTransactionSendMoney(String hashedKey, ThirdPartyTransactionDTO thirdPartyTransactionDTO){
        Account sendingAccount = accountRepository.findById(thirdPartyTransactionDTO.getAccountId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"This account does not exist"));

        // check if its a credit card
        if(sendingAccount instanceof CreditCard){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You cannot send money from a credit card");
        }
        // Check if Secret Key which was passed from the ThirdParty  matches with secret Key of the account
        // secret Key depends on the type of account
        String secretKey1 = null;
        if(sendingAccount instanceof CheckingAccount){
            secretKey1 = ((CheckingAccount) sendingAccount).getSecretKey();
        }
        if(sendingAccount instanceof SavingsAccount){
            secretKey1 = ((SavingsAccount) sendingAccount).getSecretKey();
        }
        if(sendingAccount instanceof StudentAccount){
            secretKey1= ((StudentAccount) sendingAccount).getSecretKey();
        }

        if(!thirdPartyTransactionDTO.getSecretKey().equals(secretKey1)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The secret Key is not correct");
        }

        ThirdParty thirdParty = thirdPartyRepository.findById(thirdPartyTransactionDTO.getThirdPartyId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Third Party not found with this id"));

        // Check if hashed Key provided by the Third party matches the one from the DB
        if(!hashedKey.equals(thirdParty.getHashedKey())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The hashed Key provided does not match the one in the Database for this Third Party user");
        }

        // subtract the money from the sending account
        // check if sufficient funds are provided on the sending account
        if(sendingAccount.getBalance().compareTo(thirdPartyTransactionDTO.getAmount())<0){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough funds to to this operation");
        } else{
        // deduct the money to the sending account
            sendingAccount.setBalance(sendingAccount.getBalance().subtract(thirdPartyTransactionDTO.getAmount()));
        }

        // Check for Penalty Fee and Interest Rate and Maintenance Fee
        //check Type of account
        if(sendingAccount instanceof SavingsAccount){
            ((SavingsAccount) sendingAccount).applyPenaltyFeeSavings();
            ((SavingsAccount) sendingAccount).applyInterestRateSavings();
            accountRepository.save(sendingAccount);
            return sendingAccount;
        }
        // check if its Checking Account, and apply maintenance Fee:
        if(sendingAccount instanceof CheckingAccount){
            ((CheckingAccount) sendingAccount).applyPenaltyFeeChecking();
            ((CheckingAccount) sendingAccount).applyMaintenanceFeeChecking();
            accountRepository.save(sendingAccount);
            return sendingAccount;
        }
        // check if its Student Account:
        if(sendingAccount instanceof StudentAccount){
            accountRepository.save(sendingAccount);
            return sendingAccount;
        }

        // save the sending account to the DB
        accountRepository.save(sendingAccount);

        // save the transaction to the DB
        Transaction transaction = transactionRepository.save(new Transaction(null, sendingAccount, sendingAccount.getPrimaryOwner().getName(),thirdPartyTransactionDTO.getAmount()));

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    public List getTransactionList(Authentication authentication){
        AccountHolder user = accountHolderRepository.findByUsername(authentication.getName().toString()).orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized "));
        // Without Authentication:
        //AccountHolder user = accountHolderRepository.findByUsername(username).orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized "));
        List<Transaction> receivingTransactions = transactionRepository.findByReceiverAccountId(user.getId());
        List<Transaction> sendingTransactions = transactionRepository.findBySenderAccountId(user.getId());
        TransactionDTO receivingTransactionDTO = null;
        TransactionDTO sendingTransactionDTO = null;
        List<TransactionDTO> transactionDTOListReceiver= new ArrayList<>();
        List<TransactionDTO> transactionDTOListSender= new ArrayList<>();
        for(Transaction receivingTransaction : receivingTransactions){
            receivingTransactionDTO = new TransactionDTO(receivingTransaction.getSenderAccount().getId(), receivingTransaction.getReceiverAccount().getId(), receivingTransaction.getReceiverName(), receivingTransaction.getTransferAmount());
            transactionDTOListReceiver.add(receivingTransactionDTO);
        }
        for(Transaction sedingTransaction : sendingTransactions){
           sendingTransactionDTO = new TransactionDTO(sedingTransaction.getSenderAccount().getId(), sedingTransaction.getReceiverAccount().getId(), sedingTransaction.getReceiverName(), sedingTransaction.getTransferAmount());
            transactionDTOListSender.add(sendingTransactionDTO);
        }
        List transactionList= new ArrayList<>();
        transactionList.add(transactionDTOListReceiver);
        transactionList.add(transactionDTOListSender);
        return transactionList;

    }

}
