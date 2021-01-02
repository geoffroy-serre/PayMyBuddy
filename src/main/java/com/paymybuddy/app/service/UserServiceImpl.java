package com.paymybuddy.app.service;

import com.paymybuddy.app.model.User;
import com.paymybuddy.app.repository.IUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
  private static final Logger logger = LogManager.getLogger("UserService");

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  IUserRepository userRepository;

  /**
   * @inheritDoc
   */
  @Override
  public User addUser(User user) {
    logger.debug("adding user");
    String mail = user.getEmail();
   if(userRepository.findUserByEmail(mail) !=null){
     logger.debug("user already registered");
     return new User();
   }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    logger.debug("user added");
    return user;
  }

  /**
   * @inheritDoc
   */
  @Override
  public User modifyUser(User user) {
    logger.debug("modifying user");
    String mail = user.getEmail();
    if(userRepository.findUserByEmail(mail)==null){
      logger.debug("user not registered");
      return new User();
    }
    User userRetrieved = userRepository.findUserByEmail(user.getEmail());
    user.setId(userRetrieved.getId());
    userRepository.save(user);
    logger.debug("user modified");
    return user;
  }

  /**
   * @inheritDoc
   */
  @Override
  public User modifyUserWithPasswordUpdate(User user) {
    logger.debug("modifying user");
    String mail = user.getEmail();
    if(userRepository.findUserByEmail(mail)==null){
      logger.debug("user not registered");
      return new User();
    }
    User userRetrieved = userRepository.findUserByEmail(user.getEmail());
    user.setId(userRetrieved.getId());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    logger.debug("user modified");
    return user;
  }

  @Override
  public User findUserById(Integer id) {
    return userRepository.findUserById(id);
  }

  @Override
  public User deleteUser(String email){
    User user = userRepository.findUserByEmail(email);
    if(user ==null){
      return new User();
    }
    userRepository.delete(user);
    return user;
  }

  @Override
  public User findUserByEmail(String email){
    return userRepository.findUserByEmail(email);
  }

}
