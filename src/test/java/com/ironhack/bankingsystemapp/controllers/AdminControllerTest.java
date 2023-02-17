package com.ironhack.bankingsystemapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class AdminControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }



    @Test
    public void shouldAddThirdParty(){

    }

    @Test
    public  void shouldAddAccountHolder(){

    }

    @Test
    public void shouldAddAdmin(){

    }
    @Test
    public void shouldDeleteUser(){

    }
    @Test
    public void shouldGetAllUsers(){

    }
    @Test
    public void shouldCreateCheckingAccount(){

    }

    @Test
    public void shouldCreateStudentAccount(){

    }

    @Test
    public void shouldCreateSavingsAccount(){

    }

    @Test
    public void shouldCreateCreditCard(){

    }

   @Test
    public void shouldDeleteAccount(){

   }
   @Test
    public void shouldGetAccounts(){

   }
    @Test
    public void shouldUpdateBalanceADD(){

    }

    @Test
    public void shouldUpdateBalanceSUBTRACT(){

    }

    @Test
    public void shouldGetBalance(){

    }
}
