package com.paymybuddy.app.controller;

import com.paymybuddy.app.model.MoneyTransaction;
import com.paymybuddy.app.service.IMoneyTransactionService;
import java.time.LocalDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class MoneyTransactionController {
  private static final Logger logger = LogManager.getLogger("MoneyTransactionController");

  @Autowired
  IMoneyTransactionService moneyTransaction;

  @PostMapping(value = "/moneyTransaction/")
  public void postTransaction(@RequestBody MoneyTransaction moneyTransfert,
                              HttpServletResponse response) {
    moneyTransfert.setDate(LocalDate.now());
    if (moneyTransaction.saveTransaction(moneyTransfert).getId() == null) {
      response.setStatus(404);
    }
  }

  @GetMapping("/moneyTransaction")
  public void getMoneyTransactionForAnEmail(@RequestParam String email) {

  }

}
