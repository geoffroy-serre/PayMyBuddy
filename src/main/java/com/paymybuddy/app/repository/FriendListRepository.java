package com.paymybuddy.app.repository;

import com.paymybuddy.app.model.FriendList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface FriendListRepository extends JpaRepository<FriendList, Integer> {

  FriendList save(FriendList friendlist);

  void delete(FriendList friendlist);

  FriendList findFriendListByIdUserAndIdUser2(Integer idUser, Integer idUser2);

  FriendList findFriendListById(Integer id);

  List<FriendList> findFriendListByIdUser(Integer id);

}
