package com.ironhack.bankingsystemapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ironhack.bankingsystemapp.models.users.AccountHolder;
import com.ironhack.bankingsystemapp.models.users.Address;
import com.ironhack.bankingsystemapp.models.users.Admin;
import com.ironhack.bankingsystemapp.models.users.ThirdParty;
import com.ironhack.bankingsystemapp.repositories.accounts.SavingsAccountRepository;
import com.ironhack.bankingsystemapp.repositories.users.AccountHolderRepository;
import com.ironhack.bankingsystemapp.repositories.users.AdminRepository;
import com.ironhack.bankingsystemapp.repositories.users.ThirdPartyRepository;
import com.ironhack.bankingsystemapp.repositories.users.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AdminControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    SavingsAccountRepository savingsAccountRepository;
    @Autowired
    ThirdPartyRepository thirdPartyRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    UserRepository userRepository;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());


    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
//    void deleteUserRepository(){
//    userRepository.deleteAll();
//    }


    @Test
    public void shouldCreateThirdParty() throws Exception {
        ThirdParty newThirdParty = new ThirdParty("ThirdParty1Test", "thridparty1test", "pwd", "hashedkey");

        String body = objectMapper.writeValueAsString(newThirdParty);
        MvcResult mvcResult = mockMvc.perform(post("/api/admin-area/users/new/thirdparty")
                .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("ThirdParty1Test"));
        assertEquals("ThirdParty1Test",thirdPartyRepository.findByUsername("thridparty1test").get().getName());
       //System.out.println(mvcResult.getResolvedException());
    }

    @Test
    public void shouldCreateAccountHolder() throws Exception {
        Address address = new Address("Calle 1", "Barcelona", "08000", "Spain");
        AccountHolder accountHolderX = new AccountHolder("User1Test", "user1test", "1234", LocalDate.of(1980, 01, 01), address);

        String body = objectMapper.writeValueAsString(accountHolderX);
        MvcResult mvcResult = mockMvc.perform(post("/api/admin-area/users/new/accountholder")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("User1Test"));
        assertEquals("User1Test",accountHolderRepository.findByUsername("user1test").get().getName());
        //System.out.println(mvcResult.getResolvedException());
    }
    @Test
    public void shouldCreateAdmin() throws Exception {

        Admin admin = new Admin("Admin1Test", "admin1test", "1234");

        String body = objectMapper.writeValueAsString(admin);
        MvcResult mvcResult = mockMvc.perform(post("/api/admin-area/users/new/admin")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Admin1Test"));
        assertEquals("Admin1Test",adminRepository.findByUsername("admin1test").get().getName());
        //System.out.println(mvcResult.getResolvedException());

        // Reset AdminRepository to 0
        adminRepository.deleteById(adminRepository.findByUsername("admin1test").get().getId());
    }
    @Test
    public void shouldDeleteUser() throws Exception {

        Admin admin = adminRepository.save(new Admin("Admin2Test", "admin2test", "1234") ) ;

        String body = objectMapper.writeValueAsString(admin);
        MvcResult mvcResult = mockMvc.perform(delete("/api/admin-area/users/delete")
                        .param("id", admin.getId().toString())
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        System.out.println(adminRepository.findAll().size());
        assertEquals(0, adminRepository.findAll().size());

    }
    @Test
    public void shouldGetAllUsers() throws Exception {
        Admin admin = adminRepository.save(new Admin("Admin2Test", "admin2test", "1234") ) ;
        Admin admin1 = adminRepository.save(new Admin("Admin2Test", "admin2test", "1234") ) ;

        MvcResult mvcResult = mockMvc.perform(get("/api/admin-area/users/all"))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(userRepository.findAll().size());
        assertEquals(2, userRepository.findAll().size());
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
