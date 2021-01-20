package com.paymybuddy.app.controller;

import com.paymybuddy.app.model.MoneyTransaction;
import com.paymybuddy.app.service.MoneyTransactionService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MoneyTransactionController {
  private static final Logger logger = LogManager.getLogger("paymybuddy" +
          ".MoneyTransactionController");

  @Autowired
  MoneyTransactionService moneyTransaction;

  @PostMapping(value = "/moneyTransaction/")
  public void postTransaction(@RequestBody MoneyTransaction moneyTransfert) {
    moneyTransaction.saveTransaction(moneyTransfert);
    logger.info("transactionsaved");

  }

  @GetMapping("/moneyTransaction")
  public List<MoneyTransaction> getMoneyTransactionForAnId(@RequestParam Integer id) {
    logger.info("returning transactions");
    return moneyTransaction.findAllTransactionsForOneUser(id);
  }
  @GetMapping("/moneyTransaction/desc")
  public MoneyTransaction getMoneyTransactionForADescription(@RequestParam String description) {
    logger.info("returning transactions");
    return moneyTransaction.findMoneyTransactionByDescription(description);
  }

  @PostMapping(value = "/moneyTransaction/withdraw")
  public void withdrawMoney(@RequestBody MoneyTransaction moneyTransfert) {
    moneyTransaction.withdrawMoney(moneyTransfert);
    logger.info("transaction saved");

  }

  @DeleteMapping(value = "/moneyTransaction")
  public void deleteTransaction(@RequestParam int id) {
    moneyTransaction.deleteTransaction(id);
    logger.info("transaction deleted");

  }

}
