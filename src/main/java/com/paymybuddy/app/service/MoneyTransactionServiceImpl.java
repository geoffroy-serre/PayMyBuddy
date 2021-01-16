package com.paymybuddy.app.service;


import com.paymybuddy.app.exception.CantWithdrawException;
import com.paymybuddy.app.exception.NotEnoughFundException;
import com.paymybuddy.app.model.MoneyTransaction;
import com.paymybuddy.app.model.User;
import com.paymybuddy.app.repository.MoneyTransactionRepository;
import com.paymybuddy.app.repository.UserRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoneyTransactionServiceImpl implements MoneyTransactionService {
  private static final Logger logger = LogManager.getLogger("MoneyTransactionServiceImpl");

  @Autowired
  MoneyTransactionRepository moneyTransactionRepository;

  @Autowired
  UserRepository userRepository;

  /**
   *
   * @inheritDoc
   */
  @Override
  public void saveTransaction(MoneyTransaction moneyTransfert) {
    logger.debug("Entering saveTransaction");
    User receiver = userRepository.findUserById(moneyTransfert.getIdReceiver());
    User sender = userRepository.findUserById(moneyTransfert.getIdSender());

    if (sender.getTreasury() - moneyTransfert.getAmount() < 0 && sender.getId() != receiver.getId()) {
      logger.error("Not enough fund excception. Sender hast not enough money for this transfert");
      throw new NotEnoughFundException();
    }
    if (receiver.getId() != sender.getId()) {
      sender.setTreasury(sender.getTreasury() - moneyTransfert.getAmount());
      logger.debug("Sender balance set");
    }
    if(moneyTransfert.getDate() ==null){
      moneyTransfert.setDate(LocalDate.now());
    }

    receiver.setTreasury(receiver.getTreasury() + moneyTransfert.getAmount());
    logger.debug("Receiver balance set");
    moneyTransactionRepository.save(moneyTransfert);
    logger.debug("MoneyTransaction saved");
  }

  /**
   *
   * @inheritDoc
   */
  @Override
  public void withdrawMoney(MoneyTransaction moneyTransfert) {
    logger.debug("Entering withdrawMoney");
    User receiver =  userRepository.findUserById(moneyTransfert.getIdReceiver());
    User sender =  userRepository.findUserById(moneyTransfert.getIdSender());

    if (sender.getTreasury() - moneyTransfert.getAmount() < 0) {
      logger.error("Not enough fund exception. Sender hast not enough money for this transfert");
      throw new NotEnoughFundException();
    }
   if(sender.getId() != receiver.getId() ){
      logger.error("Cant not withdraw money with receiver and sender differents");
      throw new CantWithdrawException();
    }

    receiver.setTreasury(receiver.getTreasury() - moneyTransfert.getAmount());
    logger.debug("Receiver balance set");
    moneyTransactionRepository.save(moneyTransfert);
    logger.debug("MoneyTransaction saved");
  }

  @Override
  public void deleteTransaction(int id) {
    logger.debug("Entering deleteTransaction");
    moneyTransactionRepository.deleteById(id);
    logger.debug("Transaction deleted");
  }

  @Override
  public void deleteTransactions(List<Integer> ids) {
    logger.debug("Entering deleteTransactions");
    for (Integer id : ids) {
      moneyTransactionRepository.deleteById(id);
      logger.debug("Transaction deleted");
    }
    logger.debug("Transactions deleted");
  }

  /**
   *
   * @inheritDoc
   */
  @Override
  public List<MoneyTransaction> findAllTransactionsForOneUser(int id){
    logger.debug("Entering findAllTransactionsForOneUser");
    List<MoneyTransaction> transactions = new ArrayList<>();
    transactions.addAll(moneyTransactionRepository.findMoneyTransactionByIdReceiver(id));
    transactions.addAll(moneyTransactionRepository.findMoneyTransactionByIdSender(id));
    logger.debug("Return transactions");
    return transactions;
  }


}
