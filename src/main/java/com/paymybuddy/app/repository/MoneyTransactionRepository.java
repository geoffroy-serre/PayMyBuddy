package com.paymybuddy.app.repository;

import com.paymybuddy.app.model.MoneyTransaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface MoneyTransactionRepository extends JpaRepository<MoneyTransaction, Integer> {

  MoneyTransaction findMoneyTransactionById(int id);
  MoneyTransaction save(MoneyTransaction moneyTransaction);
  List<MoneyTransaction> findMoneyTransactionByIdReceiver(int id);
  List<MoneyTransaction> findMoneyTransactionByIdSender(int id);

}
