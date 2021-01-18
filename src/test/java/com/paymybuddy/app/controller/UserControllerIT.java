package com.paymybuddy.app.controller;

import com.paymybuddy.app.model.User;
import com.paymybuddy.app.service.UserService;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserControllerIT {

  @Autowired
  UserService userService;
  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  UserController userController;

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }


  @Test
  void getUserById() {
    User user = userController.getUserById(1);
    assertEquals(1, user.getId());
    assertEquals("Geff", user.getFirstName());
    assertEquals("Serre", user.getLastName());
    assertEquals("1982-04-14", user.getBirthDate().toString());
    assertEquals("85 rue Jean Jaures appt 05", user.getAddress());
    assertEquals("Rochefort", user.getCity());
    assertEquals("17300", user.getZip());
    assertEquals("0619457854", user.getPhone());
    assertEquals(0, user.getTreasury());
  }

  @Test
  void getUserByEmail() {
    User user = userController.getUserByEmail("adapting@smite.com");
    assertEquals(2, user.getId());
    assertEquals("Kennet", user.getFirstName());
    assertEquals("Roos", user.getLastName());
    assertEquals("1990-04-14", user.getBirthDate().toString());
    assertEquals("15 Vanhouten Strasse", user.getAddress());
    assertEquals("Heineken", user.getCity());
    assertEquals("65Ac85", user.getZip());
    assertEquals("18954754", user.getPhone());
    assertEquals(544.75, user.getTreasury());
  }

  @Test
  void addUser() throws Exception {
    User newUser = new User();
    newUser.setLastName("Starman");
    newUser.setFirstName("Emil");
    newUser.setTreasury(0.0);
    newUser.setAddress("15 rue des mimosas");
    newUser.setEmail("emilito@smite.com");
    newUser.setPhone("0102030405");
    newUser.setCity("Frankfurt");
    newUser.setPassword("weshnioupass45");
    newUser.setZip("654485");
    newUser.setBirthDate(LocalDate.of(1880, 05, 12));

    String jsonRequest = "{ \"firstName\":\"Emil\",\"lastName\":\"Starman\"," +
            "\"email\":\"emilito@smite.com\"," +
            "\"password\":\"weshnioupass45\",\"address\":\"15 rue des mimosas\"," +
            "\"city\":\"Frankfurt\"," +
            "\"zip\":\"654485\",\"phone\":\"0102030405\",\"birthDate\":\"1982-04-14\"," +
            "\"treasury\":0.0}";

    this.mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
            .andExpect(status().is(200));


    userService.deleteUserWithNoTransaction(newUser.getEmail());

  }

  @Test
  void addUserAlreadyExisting() throws Exception {


    String jsonRequest = "{ \"firstName\":\"Emil\",\"lastName\":\"Starman\"," +
            "\"email\":\"geff1982@gmail.com\"," +
            "\"password\":\"weshnioupass45\",\"address\":\"15 rue des mimosas\"," +
            "\"city\":\"Frankfurt\"," +
            "\"zip\":\"654485\",\"phone\":\"0102030405\",\"birthDate\":\"1982-04-14\"," +
            "\"treasury\":0.0}";

    this.mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
            .andExpect(status().is(409));
  }

  @Test
  void addUserMissingParam() throws Exception {

    String jsonRequest = "{ \"firstName\":\"Emil\",\"lastName\":\"Starman\"," +
            "\"email\":\"toto@user.fr.com\"," +
            "\"password\":\"weshnioupass45\",\"address\":\"15 rue des mimosas\"," +
            "\"city\":\"Frankfurt\"," +
            "\"zip\":\"654485\",\"phone\":\"0102030405\",\"birthDate\":\"1982-04-14\"}";

    this.mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
            .andExpect(status().is(400));

  }

  @Test
  void deleteUser() throws Exception {
    String jsonRequest = "{ \"id\":\"1\",\"firstName\":\"Geff\",\"lastName\":\"Serre\"," +
            "\"email\":\"geff1982@gmail.com\"," +
            "\"password\":\"$2a$10$byLuIRvPZJp2aD8FeOh2oOu0sxn1IZWcILDTC3vPm.rEv8QKAaAgK\",\"address\":\"85 rue Jean Jaures appt 05\"," +
            "\"city\":\"Rochefort\"," +
            "\"zip\":\"17300\",\"phone\":\"0619457854\",\"birthDate\":\"1982-04-14\"," +
            "\"treasury\":0.0}";

    this.mockMvc.perform(delete("/users").contentType(MediaType.APPLICATION_JSON)
            .param("email","geff1982@gmail.com"))
            .andExpect(status().is(200));

    this.mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON)
           .content(jsonRequest));

  }
  @Test
  void deleteUserWithFunds() throws Exception {
    String jsonRequest = "{ \"id\":\"1\",\"firstName\":\"Geff\",\"lastName\":\"Serre\"," +
            "\"email\":\"geff1982@gmail.com\"," +
            "\"password\":\"$2a$10$byLuIRvPZJp2aD8FeOh2oOu0sxn1IZWcILDTC3vPm.rEv8QKAaAgK\",\"address\":\"85 rue Jean Jaures appt 05\"," +
            "\"city\":\"Rochefort\"," +
            "\"zip\":\"17300\",\"phone\":\"0619457854\",\"birthDate\":\"1982-04-14\"," +
            "\"treasury\":0.0}";

    User geff = userService.findUserById(1);
    geff.setTreasury(100.0);
    userService.saveUser(geff);

    this.mockMvc.perform(delete("/users").contentType(MediaType.APPLICATION_JSON)
            .param("email","geff1982@gmail.com"))
            .andExpect(status().is(400));

    this.mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest));
    userService.findUserById(1).setTreasury(-100.0);
  }

  @Test
  void modifyUser() throws Exception {
    String jsonRequest = "{ \"id\":\"1\",\"firstName\":\"Geff\",\"lastName\":\"Serre\"," +
            "\"email\":\"maildetest@test.fr\"," +
            "\"password\":\"$2a$10$byLuIRvPZJp2aD8FeOh2oOu0sxn1IZWcILDTC3vPm.rEv8QKAaAgK\",\"address\":\"85 rue Jean Jaures appt 05\"," +
            "\"city\":\"Rochefort\"," +
            "\"zip\":\"17300\",\"phone\":\"0619457854\",\"birthDate\":\"1982-04-14\"," +
            "\"treasury\":0.0}";

    String jsonRequest2 = "{ \"id\":\"1\",\"firstName\":\"Geff\",\"lastName\":\"Serre\"," +
            "\"email\":\"geff1982@gmail.com\"," +
            "\"password\":\"$2a$10$byLuIRvPZJp2aD8FeOh2oOu0sxn1IZWcILDTC3vPm.rEv8QKAaAgK\",\"address\":\"85 rue Jean Jaures appt 05\"," +
            "\"city\":\"Rochefort\"," +
            "\"zip\":\"17300\",\"phone\":\"0619457854\",\"birthDate\":\"1982-04-14\"," +
            "\"treasury\":0.0}";


    this.mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
            .andExpect(status().is(200));
    User expected = userController.getUserByEmail("maildetest@test.fr");
    assertEquals("maildetest@test.fr",expected.getEmail());


    this.mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest2));

  }

}