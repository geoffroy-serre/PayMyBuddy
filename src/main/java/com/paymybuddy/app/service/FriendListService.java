package com.paymybuddy.app.service;

import com.paymybuddy.app.model.User;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FriendListService {

  /**
   * Add a friend relationship.
   * Check if user1 and user2 exists. If yes it save the relationship and return true.
   * Else it return false.
   * @param id Integer
   * @param id2 Integer
   * @return boolean
   */
  boolean addFriend(Integer id, Integer id2);

  /**
   * Delete a Friend relationship between two users.
   * Check if users exists, and if the relation between the users exists.
   * Return false if one of the conditions are not met.
   * Else return true if it has been processed.
   * @param userId Integer
   * @param userId2 Integer
   * @return boolean
   */
  boolean deleteFriend(Integer userId, Integer userId2);

  /**
   * Get all friendList relationship with idUser as id. It get the friendship added by the user
   * only.
   * Return a friend list with Friend Objects, containing firstName, lastName and email.
   * Return empty list if no friend are found.
   * @param id Integer
   * @return List Friend
   */
  List<User> findFriends(Integer id);
}
