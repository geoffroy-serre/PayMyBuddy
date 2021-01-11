package com.paymybuddy.app.service;

import com.paymybuddy.app.model.MoneyTransaction;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IMoneyTransactionService {
  /**
   * Before saving the transaction, we check if the send have enough fund.
   * If true the amount is substracted from sender and added to receiver.
   * If not a new NotEnoughFoundException is thrown.
   * If the sender and receiver id are the same, the amount is added to the account.
   * This case is processed as if user add money in his own account.
   * @param moneyTransfert MoneyTransaction
   */
  void saveTransaction(MoneyTransaction moneyTransfert);

  void deleteTransaction(int id);

  void deleteTransactions(List<Integer> ids);

  /**
   * Return a list of MoneyTransactions if any found. Else the list returned is empty.
   *
   * @param id userId
   * @return list MoneyTransaction
   */
  List<MoneyTransaction> findAllTransactionsForOneUser(int id);
}
