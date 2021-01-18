package com.paymybuddy.app.service;

import com.paymybuddy.app.exception.NoFriendShipFound;
import com.paymybuddy.app.model.FriendList;
import com.paymybuddy.app.model.User;
import com.paymybuddy.app.repository.FriendListRepository;
import com.paymybuddy.app.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FriendListServiceImplTest {

  @Mock
  UserRepository userRepository;
  @Mock
  FriendListRepository friendListRepository;
  @Mock
  User user;
  @Mock
  FriendList friendList;

  @InjectMocks
  FriendListService friendListService = new FriendListServiceImpl();

  @Test
  void addFriend() {
    when(userRepository.findUserById(1)).thenReturn(user);
    when(userRepository.findUserById(2)).thenReturn(user);
    assertTrue(friendListService.addFriend(1,2));
  }

  @Test
  void addFriendOneIsUnknown() {
    when(userRepository.findUserById(1)).thenReturn(null);
    assertFalse(friendListService.addFriend(1,2));
  }

  @Test
  void addFriendTwoIsUnknown() {
    when(userRepository.findUserById(2)).thenReturn(null);
    when(userRepository.findUserById(1)).thenReturn(user);
    assertFalse(friendListService.addFriend(1,2));
  }

  @Test
  void deleteFriend() {
    when(userRepository.findUserById(1)).thenReturn(user);
    when(userRepository.findUserById(2)).thenReturn(user);
    when(friendListRepository.findFriendListByIdUserAndIdUser2(1,2)).thenReturn(friendList);
    assertTrue(friendListService.deleteFriend(1,2));

  }

  @Test
  void deleteFriendRelationUnExisting() {
    when(userRepository.findUserById(1)).thenReturn(user);
    when(userRepository.findUserById(2)).thenReturn(user);
    when(friendListRepository.findFriendListByIdUserAndIdUser2(1,2)).thenReturn(null);
    assertThrows(NoFriendShipFound.class, ()-> friendListService.deleteFriend(1,2));

  }

}