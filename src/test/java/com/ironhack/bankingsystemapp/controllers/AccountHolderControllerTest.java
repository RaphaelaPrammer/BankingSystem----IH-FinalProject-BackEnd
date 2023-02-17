package com.ironhack.bankingsystemapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ironhack.bankingsystemapp.dtos.TransactionDTO;
import com.ironhack.bankingsystemapp.models.Transaction;
import com.ironhack.bankingsystemapp.models.accounts.SavingsAccount;
import com.ironhack.bankingsystemapp.models.users.AccountHolder;
import com.ironhack.bankingsystemapp.models.users.Address;
import com.ironhack.bankingsystemapp.repositories.accounts.SavingsAccountRepository;
import com.ironhack.bankingsystemapp.repositories.users.AccountHolderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AccountHolderControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    SavingsAccountRepository savingsAccountRepository;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }
//    @AfterEach
//    public void tearDown(){
//        accountHolderRepository.deleteAll();
//        checkingAccountRepository.deleteAll();
//        savingAccountRepository.deleteAll();
//    }
    @Test
    public void shouldCreateAccountHolder() throws Exception {
        Address address = new Address("Calle 1", "Barcelona", "08000", "Spain");
        AccountHolder accountHolderX = new AccountHolder("User1Test", "user1test", "1234", LocalDate.of(1980, 01, 01), address);

        String body = objectMapper.writeValueAsString(accountHolderX);
        MvcResult mvcResult = mockMvc.perform(post("/api/accountholder-area/create-user")
                .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("User1Test"));
        assertEquals("User1Test",accountHolderRepository.findByUsername("user1test").get().getName());
        //System.out.println(mvcResult.getResolvedException());
    }

    // GET BALANCE WITH AUTHENTICATION
    @Test
    public void shouldGetBalanceWithAuth() throws Exception {
        Address address = new Address("Calle 1", "Barcelona", "08000", "Spain");
        AccountHolder accountHolderX = accountHolderRepository.save(new AccountHolder("User2test", "user2test", "1234", LocalDate.of(1980, 01, 01), address)) ;
        SavingsAccount savingsAccount = savingsAccountRepository.save(new SavingsAccount(BigDecimal.valueOf(1050),accountHolderX,"ABC"));

        String body = objectMapper.writeValueAsString(savingsAccount);

        MvcResult mvcResult=mockMvc.perform(get("/api/accountholder-area/accounts/my-balance-with-auth")
                        .with(user("user2test").password("1234").roles("ROLE_ACCOUNT-HOLDER"))
                        .param("accountId",savingsAccount.getId().toString())
                        //.param("ownerId", accountHolderX.getId().toString())
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //System.out.println(mvcResult.getResolvedException());
        System.out.println(mvcResult.getResponse().getContentAsString());
        assertTrue(mvcResult.getResponse().getContentAsString().contains(BigDecimal.valueOf(1050).toString()));
    }
    // GET BALANCE WITHOUT AUTHENTICATION - OK!
//    @Test
//    public void shouldGetBalanceWithoutAuth() throws Exception {
//        Address address = new Address("Calle 1", "Barcelona", "08000", "Spain");
//        AccountHolder accountHolderX = accountHolderRepository.save(new AccountHolder("User2Test", "user2test", "1234", LocalDate.of(1980, 01, 01), address)) ;
//        SavingsAccount savingsAccount = savingsAccountRepository.save(new SavingsAccount(BigDecimal.valueOf(1050),accountHolderX,"ABC"));
//
//        String body = objectMapper.writeValueAsString(savingsAccount);
//
//        MvcResult mvcResult=mockMvc.perform(get("/api/accountholder-area/accounts/my-balance-without-auth")
//                        //.with(user("user2test").password("1234").roles("ACCOUNT-HOLDER"))
//                        .param("accountId",savingsAccount.getId().toString())
//                        .param("ownerId", accountHolderX.getId().toString())
//                .content(body).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//        //System.out.println(mvcResult.getResolvedException());
//        System.out.println(mvcResult.getResponse().getContentAsString());
//        assertTrue(mvcResult.getResponse().getContentAsString().contains(BigDecimal.valueOf(1050).toString()));
//    }

    // TRANSFER MONEY WITH AUTHENTICATION !! ----------- NOT WORKING :/
    @Test
    public void shouldTransferMoneyWithAuth() throws Exception {
        Address address2 = new Address("Calle 2", "Barcelona", "08000", "Spain");
        // Sender Account and Accountholder
        AccountHolder senderAccountHolder = accountHolderRepository.save(new AccountHolder("User3Test", "user3test", "1234", LocalDate.of(1980, 01, 01), address2)) ;
        SavingsAccount senderAccount = savingsAccountRepository.save(new SavingsAccount(BigDecimal.valueOf(2100),senderAccountHolder,"ABC"));

        // Reveiver Account and Accountholder
        AccountHolder receiverAccountHolder = accountHolderRepository.save(new AccountHolder("User3Test", "user3test", "1234", LocalDate.of(1980, 01, 01), address2)) ;
        SavingsAccount receiverAccount = savingsAccountRepository.save(new SavingsAccount(BigDecimal.valueOf(2050),receiverAccountHolder,"ABC"));

        // Transaction from
        TransactionDTO transactionTest = new TransactionDTO(senderAccount.getId(), receiverAccount.getId(),receiverAccountHolder.getName(),BigDecimal.valueOf(100));

        String body = objectMapper.writeValueAsString(transactionTest);

        MvcResult mvcResult = mockMvc.perform(post("/api/accountholder-area/transaction-with-auth")
                        .with(user("user3test").password("1234").roles("ROLE_ACCOUNT-HOLDER"))
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("\"balance\":2000.00"));

    }
    // TRANSFER MONEY WITHOUT AUTHENTICATION , Otherwise Test OKAY!!
//    @Test
//    public void shouldTransferMoneyWithoutAuth() throws Exception {
//        Address address2 = new Address("Calle 2", "Barcelona", "08000", "Spain");
//        // Sender Account and Accountholder
//        AccountHolder senderAccountHolder = accountHolderRepository.save(new AccountHolder("User3Test", "user3test", "1234", LocalDate.of(1980, 01, 01), address2)) ;
//        SavingsAccount senderAccount = savingsAccountRepository.save(new SavingsAccount(BigDecimal.valueOf(2050),senderAccountHolder,"ABC"));
//
//        // Reveiver Account and Accountholder
//        AccountHolder receiverAccountHolder = accountHolderRepository.save(new AccountHolder("User3Test", "user3test", "1234", LocalDate.of(1980, 01, 01), address2)) ;
//        SavingsAccount receiverAccount = savingsAccountRepository.save(new SavingsAccount(BigDecimal.valueOf(2050),receiverAccountHolder,"ABC"));
//
//        // Transaction from
//        TransactionDTO transactionTest = new TransactionDTO(senderAccount.getId(), receiverAccount.getId(),receiverAccountHolder.getName(),BigDecimal.valueOf(100));
//
//        String body = objectMapper.writeValueAsString(transactionTest);
//
//        MvcResult mvcResult = mockMvc.perform(post("/api/accountholder-area/transaction-without-auth").content(body).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//        System.out.println(mvcResult.getResponse().getContentAsString());
//       assertTrue(mvcResult.getResponse().getContentAsString().contains("\"balance\":1950.00"));
//
//    }



}
