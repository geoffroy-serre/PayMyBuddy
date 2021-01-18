package com.paymybuddy.app.controller;

import com.paymybuddy.app.model.MoneyTransaction;
import com.paymybuddy.app.model.User;
import com.paymybuddy.app.service.MoneyTransactionService;
import com.paymybuddy.app.service.UserService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class MoneyTransactionControllerIT {

  @Autowired
  MoneyTransactionService moneyTransactionService;

  @Autowired
  MoneyTransactionController moneyTransactionController;

  @Autowired
  UserService userService;

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

  }

  @AfterEach
  void reset() {


  }

  @Test
  void postTransaction() throws Exception {

    String request = "{\"amount\":\"100\",\"idReceiver\":\"1\",\"idSender\":\"2\",\"date\":\"\"," +
            "\"description\":\"Test\"}";


    this.mockMvc.perform(post("/moneyTransaction/").contentType(MediaType.APPLICATION_JSON)
            .content(request))
            .andExpect(status().is(200));
    User user1 = userService.findUserById(1);
    User user2 = userService.findUserById(2);
    assertEquals(100, user1.getTreasury());
    assertEquals(444.75, user2.getTreasury());

    MoneyTransaction todelete = moneyTransactionService.findMoneyTransactionByDescription("Test");
    moneyTransactionService.deleteTransaction(todelete.getId());

    //Reset user's treasury as they were before this test
    user1.setTreasury(user1.getTreasury() - 100);
    user2.setTreasury(user2.getTreasury() + 100);
    userService.saveUser(user1);
    userService.saveUser(user2);
  }

  @Test
  void postTransactionWithNotEnoughFunds() throws Exception {

    String request = "{\"amount\":\"100\",\"idReceiver\":\"2\",\"idSender\":\"1\",\"date\":\"\"," +
            "\"description\":\"Test\"}";

    this.mockMvc.perform(post("/moneyTransaction/").contentType(MediaType.APPLICATION_JSON)
            .content(request))
            .andExpect(status().is(400));

  }

  @Test
  void getMoneyTransactionForAnId() throws Exception {

    MvcResult result =
            this.mockMvc.perform(get("/moneyTransaction").contentType(MediaType.APPLICATION_JSON)
                    .param("id", String.valueOf(1)))
                    .andExpect(status().is(200))
                    .andReturn();

    assertTrue(result.getResponse().getContentAsString().contains("Put money on my paybuddy " +
            "account"));
    assertTrue(result.getResponse().getContentAsString().contains("Adam to Geff"));

  }

  @Test
  void withdrawMoney() throws Exception {
    String request = "{\"amount\":\"100\",\"idReceiver\":\"2\",\"idSender\":\"2\",\"date\":\"\"," +
            "\"description\":\"Test\"}";
    User user = userService.findUserById(2);
    double treasury = user.getTreasury();
    this.mockMvc.perform(post("/moneyTransaction/withdraw").contentType(MediaType.APPLICATION_JSON)
            .content(request))
            .andExpect(status().is(200));
    user = userService.findUserById(2);
    assertEquals(treasury-100, user.getTreasury());
    user.setTreasury(544.75);
    userService.saveUser(user);
    MoneyTransaction todelete = moneyTransactionService.findMoneyTransactionByDescription("Test");
    moneyTransactionService.deleteTransaction(todelete.getId());

  }

  @Test
  void withdrawMoneyFail() throws Exception {
    String request = "{\"amount\":\"100\",\"idReceiver\":\"1\",\"idSender\":\"2\",\"date\":\"\"," +
            "\"description\":\"Test\"}";
    this.mockMvc.perform(post("/moneyTransaction/withdraw").contentType(MediaType.APPLICATION_JSON)
            .content(request))
            .andExpect(status().is(400));

  }
}