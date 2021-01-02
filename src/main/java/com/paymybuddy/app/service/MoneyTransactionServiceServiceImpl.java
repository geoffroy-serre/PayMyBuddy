package com.paymybuddy.app.service;


import com.paymybuddy.app.exception.NotEnoughFundException;
import com.paymybuddy.app.model.MoneyTransaction;
import com.paymybuddy.app.model.User;
import com.paymybuddy.app.repository.IMoneyTransactionRepository;
import com.paymybuddy.app.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoneyTransactionServiceServiceImpl implements IMoneyTransactionService {

  @Autowired
  IMoneyTransactionRepository moneyTransactionRepository;

  @Autowired
  IUserRepository userRepository;

  @Override
  public MoneyTransaction saveTransaction(MoneyTransaction moneyTransfert) {
    User receiver = userRepository.findUserById(moneyTransfert.getIdReceiver());
    User sender = userRepository.findUserById(moneyTransfert.getIdSender());

    if (sender.getTreasury() - moneyTransfert.getAmount() < 0) {
      throw new NotEnoughFundException();
    }

    receiver.setTreasury(receiver.getTreasury() + moneyTransfert.getAmount());
    sender.setTreasury(sender.getTreasury() - moneyTransfert.getAmount());
    moneyTransactionRepository.save(moneyTransfert);


    return moneyTransfert;
  }

  
}
