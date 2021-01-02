package com.paymybuddy.app.service;

import com.paymybuddy.app.model.MoneyTransaction;

public interface IMoneyTransactionService {
  MoneyTransaction saveTransaction(MoneyTransaction moneyTransfert);
}
