package com.paymybuddy.app.service;

import com.paymybuddy.app.model.FriendList;
import com.paymybuddy.app.model.User;
import com.paymybuddy.app.repository.FriendListRepository;
import com.paymybuddy.app.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendListServiceImpl implements FriendListService {
  private static final Logger logger = LogManager.getLogger("FriendListServiceImpl");
  @Autowired
  FriendListRepository friendListRepository;

  @Autowired
  UserRepository userRepository;

  /**
   *
   * @inheritDoc
   */
  @Override
  public boolean addFriend(Integer id, Integer id2) {
    logger.debug("entering addfriend method");
    if (userRepository.findUserById(id) != null && userRepository.findUserById(id2) != null) {
      FriendList friendList = new FriendList();
      friendList.setIdUser(id);
      friendList.setIdUser2(id2);
      friendListRepository.save(friendList);
      logger.debug("friend added");
      return true;
    }
    logger.debug("error while adding friend");
    return false;
  }

  /**
   *
   * @inheritDoc
   */
  @Override
  public boolean deleteFriend(Integer userId, Integer userId2) {
    logger.debug("Entering deleteFriend");
    if (userRepository.findUserById(userId) != null && userRepository.findUserById(userId2) != null
            && friendListRepository.findFriendListByIdUserAndIdUser2(userId, userId2) != null) {
      friendListRepository.delete(friendListRepository.findFriendListByIdUserAndIdUser2(userId,
              userId2));
      logger.debug("friend relationship deleted");
      return true;
    }
    logger.debug("cant delete this relationship");
    return false;
  }

  /**
   *
   * @inheritDoc
   */
  @Override
  public List<User> findFriends(Integer id){
    logger.debug("Entering findFriends");
    List<FriendList> friendLists = new ArrayList<>( friendListRepository.findFriendListByIdUser(id));
    List<User> users = new ArrayList<>();
    User userRetrieved = new User();
    for (FriendList friend: friendLists ) {

      userRetrieved.setFirstName(userRepository.findUserById(friend.getIdUser2()).getFirstName());
      userRetrieved.setLastName(userRepository.findUserById(friend.getIdUser2()).getLastName());
      userRetrieved.setEmail(userRepository.findUserById(friend.getIdUser2()).getEmail());

      users.add(userRetrieved);
    }
    logger.debug("List of Friend returned");
    return users;

  }
}
