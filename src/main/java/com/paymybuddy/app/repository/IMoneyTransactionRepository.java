package com.paymybuddy.app.repository;

import com.paymybuddy.app.model.MoneyTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IMoneyTransactionRepository extends JpaRepository<MoneyTransaction, Integer> {

  MoneyTransaction findMoneyTransactionById(int id);
  MoneyTransaction save(MoneyTransaction moneyTransaction);

}
