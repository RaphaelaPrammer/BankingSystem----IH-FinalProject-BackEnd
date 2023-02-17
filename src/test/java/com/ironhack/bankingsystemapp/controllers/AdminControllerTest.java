package com.ironhack.bankingsystemapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ironhack.bankingsystemapp.dtos.StudentAccCheckingAccDTO;
import com.ironhack.bankingsystemapp.models.accounts.CheckingAccount;
import com.ironhack.bankingsystemapp.models.accounts.CreditCard;
import com.ironhack.bankingsystemapp.models.accounts.SavingsAccount;
import com.ironhack.bankingsystemapp.models.users.AccountHolder;
import com.ironhack.bankingsystemapp.models.users.Address;
import com.ironhack.bankingsystemapp.models.users.Admin;
import com.ironhack.bankingsystemapp.models.users.ThirdParty;
import com.ironhack.bankingsystemapp.repositories.accounts.CheckingAccountRepository;
import com.ironhack.bankingsystemapp.repositories.accounts.CreditCardRepository;
import com.ironhack.bankingsystemapp.repositories.accounts.SavingsAccountRepository;
import com.ironhack.bankingsystemapp.repositories.accounts.StudentAccountRepository;
import com.ironhack.bankingsystemapp.repositories.users.AccountHolderRepository;
import com.ironhack.bankingsystemapp.repositories.users.AdminRepository;
import com.ironhack.bankingsystemapp.repositories.users.ThirdPartyRepository;
import com.ironhack.bankingsystemapp.repositories.users.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    CreditCardRepository creditCardRepository;
    @Autowired
    CheckingAccountRepository checkingAccountRepository;
    @Autowired
    StudentAccountRepository studentAccountRepository;
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
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

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
    public void shouldCreateCheckingAccount() throws Exception {
        Address address = new Address("Calle 1", "Barcelona", "08000", "Spain");
        AccountHolder checkingaccountHolder = accountHolderRepository.save(new AccountHolder("Checking Account Holder", "checkingaccountholder", "1234", LocalDate.of(1980, 01, 01), address)) ;

        StudentAccCheckingAccDTO checkingAccountDTO = new StudentAccCheckingAccDTO(BigDecimal.valueOf(1000), checkingaccountHolder.getId(), "ABC");

        String body = objectMapper.writeValueAsString(checkingAccountDTO);

        MvcResult mvcResult = mockMvc.perform(post("/api/admin-area/accounts/new/checking")
                 .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("\"balance\":1000"));
        assertEquals(1,checkingAccountRepository.findAll().size());

    }
    @Test
    public void shouldStudentAccount_whenCreateCheckingAccountMethodCalled_ButAgeLessThan24Years() throws Exception {
        Address address = new Address("Calle 1", "Barcelona", "08000", "Spain");
        AccountHolder youngPerson = accountHolderRepository.save(new AccountHolder("Young Person Pepito", "youngpersonpepito", "1234", LocalDate.of(2010, 01, 01), address)) ;

        StudentAccCheckingAccDTO checkingAccountDTO = new StudentAccCheckingAccDTO(BigDecimal.valueOf(1000), youngPerson.getId(), "ABC");

        String body = objectMapper.writeValueAsString(checkingAccountDTO);

        MvcResult mvcResult = mockMvc.perform(post("/api/admin-area/accounts/new/checking")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("\"balance\":1000"));
        assertEquals(0,checkingAccountRepository.findAll().size());
        assertEquals(1,studentAccountRepository.findAll().size());

    }

    @Test
    public void shouldCreateStudentAccount() throws Exception {
        Address address = new Address("Calle 1", "Barcelona", "08000", "Spain");
        AccountHolder student = accountHolderRepository.save(new AccountHolder("Student", "student", "1234", LocalDate.of(2010, 01, 01), address)) ;

        StudentAccCheckingAccDTO checkingAccountDTO = new StudentAccCheckingAccDTO(BigDecimal.valueOf(1000), student.getId(), "ABC");

        String body = objectMapper.writeValueAsString(checkingAccountDTO);

        MvcResult mvcResult = mockMvc.perform(post("/api/admin-area/accounts/new/student")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("\"balance\":1000"));
        assertEquals(1,studentAccountRepository.findAll().size());
    }

    @Test
    public void shouldCreateCreditCard() throws Exception {
        Address address = new Address("Calle 1", "Barcelona", "08000", "Spain");
        AccountHolder creditCardHolder = accountHolderRepository.save(new AccountHolder("CreditCardHolder", "creditcardholder", "1234", LocalDate.of(1980, 01, 01), address)) ;
        CreditCard newCrediCard = new CreditCard(BigDecimal.valueOf(1050),creditCardHolder);

        String body = objectMapper.writeValueAsString(newCrediCard);
        MvcResult mvcResult = mockMvc.perform(post("/api/admin-area/accounts/new/credit")
                 .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("\"balance\":1050"));
        assertEquals(1,creditCardRepository.findAll().size());

    }
    @Test
    public void shouldCreateSavingsAccount() throws Exception {
        Address address = new Address("Calle 1", "Barcelona", "08000", "Spain");
        AccountHolder savingsAccountHolder = accountHolderRepository.save(new AccountHolder("SavingsAccountHolder", "savingsaccountholder", "1234", LocalDate.of(1980, 01, 01), address)) ;
        SavingsAccount savingsAccount = new SavingsAccount(BigDecimal.valueOf(1050),savingsAccountHolder,"ABC");

        String body = objectMapper.writeValueAsString(savingsAccount);
        MvcResult mvcResult = mockMvc.perform(post("/api/admin-area/accounts/new/savings")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("\"balance\":1050"));
        assertEquals(1,savingsAccountRepository.findAll().size());

    }
   @Test
    public void shouldDeleteAccount() throws Exception {
        //reset savingsaccountrepository
       savingsAccountRepository.deleteAll();

       Address address = new Address("Calle 1", "Barcelona", "08000", "Spain");
       AccountHolder savingsAccountHolder = accountHolderRepository.save(new AccountHolder("SavingsAccountHolder", "savingsaccountholder", "1234", LocalDate.of(1980, 01, 01), address)) ;
       SavingsAccount savingsAccount1 = savingsAccountRepository.save(new SavingsAccount(BigDecimal.valueOf(1050),savingsAccountHolder,"ABC"));
       SavingsAccount savingsAccount2 = savingsAccountRepository.save(new SavingsAccount(BigDecimal.valueOf(1050),savingsAccountHolder,"ABC"));

       String body = objectMapper.writeValueAsString(savingsAccount1);

       MvcResult mvcResult = mockMvc.perform(delete("/api/admin-area/accounts")
                .param("id", savingsAccount1.getId().toString()))
               .andExpect(status().isNotFound())
               .andReturn();
       System.out.println(mvcResult.getResponse().getContentAsString());
       System.out.println(savingsAccountRepository.findAll().size());
       assertEquals(1, savingsAccountRepository.findAll().size());
   }
   @Test
    public void shouldGetAccounts(){

   }
    @Test
    public void shouldUpdateBalanceADD() throws Exception {
        Address address = new Address("Calle 1", "Barcelona", "08000", "Spain");
        AccountHolder savingsAccountHolder = accountHolderRepository.save(new AccountHolder("SavingsAccountHolder", "savingsaccountholder", "1234", LocalDate.of(1980, 01, 01), address)) ;
        SavingsAccount savingsAccount3 = savingsAccountRepository.save(new SavingsAccount(BigDecimal.valueOf(1050),savingsAccountHolder,"ABC"));


        MvcResult mvcResult = mockMvc.perform(patch("/api/admin-area/accounts/update-balance-add")
                        .param("id", savingsAccount3.getId().toString())
                        .param("amount", BigDecimal.valueOf(50).toString()))
                        .andExpect(status().isOk())
                        .andReturn();

        assertEquals("1100.00", savingsAccountRepository.findById(savingsAccount3.getId()).get().getBalance().toString());

    }

    @Test
    public void shouldUpdateBalanceSUBTRACT() throws Exception {
        Address address = new Address("Calle 1", "Barcelona", "08000", "Spain");
        AccountHolder savingsAccountHolder = accountHolderRepository.save(new AccountHolder("SavingsAccountHolder", "savingsaccountholder", "1234", LocalDate.of(1980, 01, 01), address)) ;
        SavingsAccount savingsAccount4 = savingsAccountRepository.save(new SavingsAccount(BigDecimal.valueOf(1050),savingsAccountHolder,"ABC"));

        MvcResult mvcResult = mockMvc.perform(patch("/api/admin-area/accounts/update-balance-subtract")
                        .param("id", savingsAccount4.getId().toString())
                        .param("amount", BigDecimal.valueOf(50).toString()))
                        .andExpect(status().isOk())
                        .andReturn();

        assertEquals("1000.00", savingsAccountRepository.findById(savingsAccount4.getId()).get().getBalance().toString());
    }

    @Test
    public void shouldGetBalance() throws Exception {
        Address address = new Address("Calle 1", "Barcelona", "08000", "Spain");
        AccountHolder accountHolderX = accountHolderRepository.save(new AccountHolder("User2test", "user2test", "1234", LocalDate.of(1980, 01, 01), address)) ;
        SavingsAccount savingsAccount5 = savingsAccountRepository.save(new SavingsAccount(BigDecimal.valueOf(1050),accountHolderX,"ABC"));

        MvcResult mvcResult=mockMvc.perform(get("/api/admin-area/accounts/balance")
                        .param("id",savingsAccount5.getId().toString()))
                        .andExpect(status().isOk())
                        .andReturn();

        assertEquals("1050.00", savingsAccountRepository.findById(savingsAccount5.getId()).get().getBalance().toString());
    }
}
