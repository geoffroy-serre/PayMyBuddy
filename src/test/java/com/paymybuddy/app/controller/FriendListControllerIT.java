package com.paymybuddy.app.controller;

import com.paymybuddy.app.service.FriendListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class FriendListControllerIT {

  @Autowired
  FriendListService friendListService;

  @Autowired
  FriendListController friendListController;

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

  }

  @Test
  void addFriendAllreadyFriends() throws Exception {
    this.mockMvc.perform(post("/friends").contentType(MediaType.APPLICATION_JSON)
            .param("userId", String.valueOf(1))
            .param("userId2", String.valueOf(2)))
            .andExpect(status().is(400));
  }

  @Test
  void addFriendFriendThemSelf() throws Exception {
    this.mockMvc.perform(post("/friends").contentType(MediaType.APPLICATION_JSON)
            .param("userId", String.valueOf(1))
            .param("userId2", String.valueOf(1)))
            .andExpect(status().is(400));
  }

  @Test
  void addFriendFriend() throws Exception {
    this.mockMvc.perform(post("/friends").contentType(MediaType.APPLICATION_JSON)
            .param("userId", String.valueOf(4))
            .param("userId2", String.valueOf(1)))
            .andExpect(status().is(200));
    assertNotNull(friendListService.findFriends(4));

    friendListController.deleteFriend(4,1);

  }

  @Test
  void deleteFriend() throws Exception {
    friendListController.addFriend(4, 1);
    this.mockMvc.perform(delete("/friends").contentType(MediaType.APPLICATION_JSON)
            .param("userId", String.valueOf(4))
            .param("userId2", String.valueOf(1)))
            .andExpect(status().is(200));

    assertEquals("[]", friendListController.getFriends(4).toString());


  }

  @Test
  void deleteFriendNoRelationShip() throws Exception {

    this.mockMvc.perform(delete("/friends").contentType(MediaType.APPLICATION_JSON)
            .param("userId", String.valueOf(4))
            .param("userId2", String.valueOf(1)))
            .andExpect(status().is(400));

  }

  @Test
  void getFriendsNoFriends() throws Exception {
    this.mockMvc.perform(get("/friends").contentType(MediaType.APPLICATION_JSON)
            .param("userId", String.valueOf(4)))
            .andExpect(status().is(200));

    assertEquals("[]", friendListService.findFriends(4).toString());
  }

  @Test
  void getFriends() throws Exception {
    MvcResult result = this.mockMvc.perform(get("/friends").contentType(MediaType.APPLICATION_JSON)
            .param("userId", String.valueOf(3)))
            .andExpect(status().is(200))
            .andReturn();

    assertTrue(result.getResponse().getContentAsString().contains("Geff"));
    assertTrue(result.getResponse().getContentAsString().contains("Francine"));
  }
}