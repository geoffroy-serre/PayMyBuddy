package com.paymybuddy.app.controller;

import com.paymybuddy.app.model.User;
import com.paymybuddy.app.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {
  private static final Logger logger = LogManager.getLogger("UserController");

  @Autowired
  IUserService userService;
  @Autowired
  PasswordEncoder passwordEncoder;

  @GetMapping("/users/{id}")
  public User getUserById(@PathVariable int id) {
    return userService.findUserById(id);
  }

  @GetMapping("/users")
  public User getUserByEmail(@RequestParam String email) {
    return userService.findUserByEmail(email);
  }

  @PostMapping(value = "/users")
  public void addUser(@RequestBody User user, HttpServletResponse response) {
    if (!userService.addUser(user)) {
      logger.info("Email already known " + user.getEmail());
      response.setStatus(409);
    } else {
      logger.info("User registered sucessfully " + user.toString());
      response.setStatus(200);
    }
  }

  @DeleteMapping(value = "/users")
  public void deleteUser(@RequestParam String email, HttpServletResponse response) {
    if (userService.deleteUser(email)) {
      logger.info("Delete user with email " + email);
      response.setStatus(200);
    } else {
      logger.info("Email unknown " + email);
      response.setStatus(409);
    }

  }

  @PutMapping(value = "/users")
  public void modifyUser(@RequestBody User user, HttpServletResponse response) {
    userService.saveUser(user);
    logger.info("User successfully modified " + user.toString());
    response.setStatus(200);

  }

  @PutMapping(value = "/users/pwd")
  public void modifyUserWithPasswordUpdate(@RequestBody User user, HttpServletResponse response) {

  }


}
