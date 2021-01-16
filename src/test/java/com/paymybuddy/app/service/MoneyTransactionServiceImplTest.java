package com.paymybuddy.app.service;

import com.paymybuddy.app.exception.CantWithdrawException;
import com.paymybuddy.app.exception.NotEnoughFundException;
import com.paymybuddy.app.model.MoneyTransaction;
import com.paymybuddy.app.model.User;
import com.paymybuddy.app.repository.MoneyTransactionRepository;
import com.paymybuddy.app.repository.UserRepository;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MoneyTransactionServiceImplTest {

  @Mock
  MoneyTransactionRepository moneyTransactionRepository;
  @Mock
  UserRepository userRepository;
  @Mock
  User user;

  @Mock
  MoneyTransaction moneyTransaction;

  @InjectMocks
  MoneyTransactionService moneyTransactionService = new MoneyTransactionServiceImpl();

  @Test
  void saveTransaction() {

    when(userRepository.findUserById(moneyTransaction.getIdReceiver())).thenReturn(user);
    when(userRepository.findUserById(moneyTransaction.getIdSender())).thenReturn(user);
    when(moneyTransaction.getAmount()).thenReturn(15.0);
    when(user.getTreasury()).thenReturn(100.0);
    when(user.getId()).thenReturn(1,2);
    when(moneyTransactionRepository.save(moneyTransaction)).thenReturn(moneyTransaction);
    assertDoesNotThrow(() -> moneyTransactionService.saveTransaction(moneyTransaction));
  }

  @Test
  void saveTransactionNotEnoughFund() {
    when(userRepository.findUserById(moneyTransaction.getIdReceiver())).thenReturn(user);
    when(userRepository.findUserById(moneyTransaction.getIdSender())).thenReturn(user);
    when(moneyTransaction.getAmount()).thenReturn(500.0);
    when(user.getTreasury()).thenReturn(100.0);
    assertThrows(NotEnoughFundException.class,
            () -> moneyTransactionService.saveTransaction(moneyTransaction));
  }

  @Test
  void withdrawMoneyNotEnoughFund() {
    when(userRepository.findUserById(moneyTransaction.getIdReceiver())).thenReturn(user);
    when(userRepository.findUserById(moneyTransaction.getIdSender())).thenReturn(user);
    when(moneyTransaction.getAmount()).thenReturn(500.0);
    when(user.getTreasury()).thenReturn(100.0);
    assertThrows(NotEnoughFundException.class,
            () -> moneyTransactionService.withdrawMoney(moneyTransaction));

  }

  @Test
  void withdrawMoneyNotSameUsers() {

    when(userRepository.findUserById(moneyTransaction.getIdReceiver())).thenReturn(user);
    when(userRepository.findUserById(moneyTransaction.getIdSender())).thenReturn(user);
    when(user.getId()).thenReturn(1,2);



    when(moneyTransaction.getAmount()).thenReturn(50.0);
    when(user.getTreasury()).thenReturn(100.0);

    assertThrows(CantWithdrawException.class,
            () -> moneyTransactionService.withdrawMoney(moneyTransaction));

  }

  @Test
  void deleteTransaction() {
    assertDoesNotThrow(()->moneyTransactionService.deleteTransaction(1));
  }

  @Test
  void deleteTransactions() {
    List<Integer> ids =new ArrayList<Integer>(Arrays.asList(1,2,4,5));
    assertDoesNotThrow(()->moneyTransactionService.deleteTransactions(ids));
  }

  @Test
  void findAllTransactionsForOneUser() {
    List<MoneyTransaction> moneyTransactionsSender = new ArrayList<>();
    List<MoneyTransaction> moneyTransactionsReceiver = new ArrayList<>();
    when(moneyTransactionRepository.findMoneyTransactionByIdReceiver(1)).thenReturn(moneyTransactionsSender);
    when(moneyTransactionRepository.findMoneyTransactionByIdReceiver(1)).thenReturn(moneyTransactionsReceiver);
    assertDoesNotThrow(()->moneyTransactionService.findAllTransactionsForOneUser(1));
  }
}