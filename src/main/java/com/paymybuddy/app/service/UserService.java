package com.paymybuddy.app.service;

import com.paymybuddy.app.model.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {
  /**
   * Add a user. Verify is the user is already registered by checking existing email.
   * Return false if already registered.
   *
   * @param user User
   * @return boolean
   */
  boolean addUser(User user);

  /**
   * Modify existing user.
   *
   * @param user User
   * @return boolean
   */
  void saveUser(User user);


  User findUserById(Integer id);

  /**
   * Delete a user using email to find him.
   * Return true if delete succeed. False if no user found with the provided email.
   *
   * @param email String
   * @return boolean
   */
  boolean deleteUser(String email);

  /**
   * Delete User who have no transaction linked
   *
   * @param email
   * @return boolean
   */
  boolean deleteUserWithNoTransaction(String email);

  User findUserByEmail(String email);
}
