package com.paymybuddy.app.service;

import com.paymybuddy.app.model.MoneyTransaction;
import com.paymybuddy.app.model.User;
import com.paymybuddy.app.repository.MoneyTransactionRepository;
import com.paymybuddy.app.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private static final Logger logger = LogManager.getLogger("UserService");

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  UserRepository userRepository;

  @Autowired
  MoneyTransactionRepository moneyTransactionRepository;

  /**
   * @inheritDoc
   */
  @Override
  public boolean addUser(User user) {
    logger.debug("adding user");
    String mail = user.getEmail();
    if (userRepository.findUserByEmail(mail) != null) {
      logger.debug("user already registered");
      return false;
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    logger.debug("user added");
    return true;
  }

  /**
   * @inheritDoc
   */
  @Override
  public void saveUser(User user) {
    logger.debug("saving user");
    userRepository.save(user);
    logger.debug("user saved");
  }


  @Override
  public User findUserById(Integer id) {
    return userRepository.findUserById(id);
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean deleteUser(String email) {
    User user = userRepository.findUserByEmail(email);
    if (user == null) {
      return false;
    }


    userRepository.delete(user);
    return true;
  }

  @Override
  public User findUserByEmail(String email) {

    return userRepository.findUserByEmail(email);
  }

}
