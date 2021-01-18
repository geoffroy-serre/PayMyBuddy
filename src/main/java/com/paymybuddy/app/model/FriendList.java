package com.paymybuddy.app.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "friendlists")
public class FriendList {
  @Column(name = "id_user")
  @NotNull
  Integer idUser;
  @Column(name = "id_user2")
  @NotNull
  Integer idUser2;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getIdUser() {
    return idUser;
  }

  public void setIdUser(Integer idUser) {
    this.idUser = idUser;
  }

  public Integer getIdUser2() {
    return idUser2;
  }

  public void setIdUser2(Integer idUser2) {
    this.idUser2 = idUser2;
  }
}
