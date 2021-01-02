package com.paymybuddy.app.service;


import com.paymybuddy.app.model.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IUserService {
  /**
   * Add a user. Verify is the user is already registered by checking existing email.
   * Return new User if already registered.
   * @param user User
   * @return user User
   */
  User addUser(User user);

  User modifyUser(User user);

  User modifyUserWithPasswordUpdate(User user);

  User findUserById(Integer id);

  User deleteUser(String email);

  User findUserByEmail(String email);
}
