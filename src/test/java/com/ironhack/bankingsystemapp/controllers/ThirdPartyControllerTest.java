package com.ironhack.bankingsystemapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ironhack.bankingsystemapp.dtos.ThirdPartyTransactionDTO;
import com.ironhack.bankingsystemapp.models.accounts.SavingsAccount;
import com.ironhack.bankingsystemapp.models.users.AccountHolder;
import com.ironhack.bankingsystemapp.models.users.Address;
import com.ironhack.bankingsystemapp.models.users.Admin;
import com.ironhack.bankingsystemapp.models.users.ThirdParty;
import com.ironhack.bankingsystemapp.repositories.accounts.SavingsAccountRepository;
import com.ironhack.bankingsystemapp.repositories.users.AccountHolderRepository;
import com.ironhack.bankingsystemapp.repositories.users.UserRepository;
import com.ironhack.bankingsystemapp.services.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ThirdPartyControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    SavingsAccountRepository savingsAccountRepository;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                //.apply(springSecurity())
                .build();;
    }
    void setThirdparty0(){

    }
    @Test
    public void shouldMakeTransferenceSENDMONEY() throws Exception {
        // Create a Thirdparty who wants to do the operation
        ThirdParty thirdParty0 = userRepository.save(new ThirdParty("ThirdParty0", "thirdparty0", "0000", "WAUWAU")) ;
        userService.addRoleToUser("thirdparty0","THIRD-PARTY");

        // Create The User and Account
        Address address = new Address("Calle 1", "Barcelona", "08000", "Spain");
        AccountHolder accountHolderX = accountHolderRepository.save(new AccountHolder("User2test", "user2test", "1234", LocalDate.of(1980, 01, 01), address)) ;
        SavingsAccount savingsAccount = savingsAccountRepository.save(new SavingsAccount(BigDecimal.valueOf(5000),accountHolderX,"ABC"));

        // Create the DTO and convert in into JSON Format
        ThirdPartyTransactionDTO transactionDTO = new ThirdPartyTransactionDTO(thirdParty0.getId(), BigDecimal.valueOf(1000),savingsAccount.getId(),"ABC");

        String body = objectMapper.writeValueAsString(transactionDTO);

        MvcResult mvcResult = mockMvc.perform(post("/api/third-party-area/transaction/send-money")
                    .with(user("thirdparty0").password("0000").roles("THIRD-PARTY"))
                        .param("hashedKey", "WAUWAU")
                    .content(body).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

        assertEquals("4000.00",savingsAccount.getBalance().toString());





    }

    @Test
    public void shouldMakeTransferenceRECEIVEMONEY() throws Exception {
        // Create a Thirdparty who wants to do the operation
        ThirdParty thirdParty0 = userRepository.save(new ThirdParty("ThirdParty0", "thirdparty0", "0000", "WAUWAU")) ;
        userService.addRoleToUser("thirdparty0","THIRD-PARTY");

        // Create The User and Account
        Address address = new Address("Calle 1", "Barcelona", "08000", "Spain");
        AccountHolder accountHolderX = accountHolderRepository.save(new AccountHolder("User2test", "user2test", "1234", LocalDate.of(1980, 01, 01), address)) ;
        SavingsAccount savingsAccount = savingsAccountRepository.save(new SavingsAccount(BigDecimal.valueOf(5000),accountHolderX,"ABC"));

        // Create the DTO and convert in into JSON Format
        ThirdPartyTransactionDTO transactionDTO = new ThirdPartyTransactionDTO(thirdParty0.getId(), BigDecimal.valueOf(1000),savingsAccount.getId(),"ABC");

        String body = objectMapper.writeValueAsString(transactionDTO);

        MvcResult mvcResult = mockMvc.perform(post("/api/third-party-area/transaction/receive-money")
                        .with(user("thirdparty0").password("0000").roles("THIRD-PARTY"))
                        .param("hashedKey", "WAUWAU")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("6000.00",savingsAccount.getBalance().toString());

    }
}
