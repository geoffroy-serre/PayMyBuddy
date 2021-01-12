package com.paymybuddy.app.service;

import com.paymybuddy.app.model.MoneyTransaction;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MoneyTransactionService {
  /**
   * Before saving the transaction, we check if the send have enough fund.
   * If true the amount is substracted from sender and added to receiver.
   * If not a new NotEnoughFoundException is thrown.
   * If the sender and receiver id are the same, the amount is added to the account.
   * This case is processed as if user add money in his own account.
   * @param moneyTransfert MoneyTransaction
   */
  void saveTransaction(MoneyTransaction moneyTransfert);

  /**
   * Need a complete MoneyTransaction object.
   * You need to set the idreceiver and isender the same form withdraw.
   * Before saving the transaction, we check if the send have enough fund.
   * If not a new NotEnoughFoundException is thrown.
   * The amount is substracted from balanc of idReceiver.
   * @param moneyTransfert MoneyTransaction
   */
  void withdrawMoney(MoneyTransaction moneyTransfert);

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
