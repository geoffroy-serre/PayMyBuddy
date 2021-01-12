package com.paymybuddy.app.controller;

import com.paymybuddy.app.DTO.Friend;
import com.paymybuddy.app.service.FriendListService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FriendListController {
  private static final Logger logger = LogManager.getLogger("FriendListController");
  @Autowired
  FriendListService friendListService;

  @PostMapping(value="/friends")
  public void addFriend(@RequestParam int userId, @RequestParam int userId2 ){
    friendListService.addFriend(userId, userId2);
    logger.info("Added Friend relationship for user n# "+userId+ " and "+userId2);

  }
  @DeleteMapping (value = "/friends")
  public void deleteFriend(@RequestParam int userId, @RequestParam int userId2){
    friendListService.deleteFriend(userId,userId2);
    logger.info("Deleted Friend relationship for user n# "+userId+ " and "+userId2);
  }

  @GetMapping(value = "/friends")
  public List<Friend> getFriends(@RequestParam Integer userId){
    logger.info("Friend List returned for userId "+userId);
    return friendListService.findFriends(userId);

  }
}
