package com.paymybuddy.app.repository;

import com.paymybuddy.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
  User findUserById(int id);
  User save(User user);
  User findUserByEmail(String  mail);

}
