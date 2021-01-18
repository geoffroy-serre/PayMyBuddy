package com.paymybuddy.app.service;

import com.paymybuddy.app.model.User;
import com.paymybuddy.app.repository.MoneyTransactionRepository;
import com.paymybuddy.app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  PasswordEncoder passwordEncoder;
  @Mock
  UserRepository userRepository;
  @Mock
  MoneyTransactionRepository moneyTransactionRepository;
  @Mock
  User user;

  @InjectMocks
  UserService userService = new UserServiceImpl();


  @Test
  void addUser() {
    when(user.getEmail()).thenReturn("email@test.fr");
    when(userRepository.findUserByEmail("email@test.fr")).thenReturn(null);
    assertTrue(userService.addUser(user));

  }

  @Test
  void addUserAlreadyExisting() {
    when(user.getEmail()).thenReturn("email@test.fr");
    when(userRepository.findUserByEmail("email@test.fr")).thenReturn(user);
    assertFalse(userService.addUser(user));

  }


}