package com.paymybuddy.app.model;

import javax.persistence.*;

@Entity
@Table(name="friendlists")
public class FriendList {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name="id_user")
  Integer idUser;

  @Column(name="id_user2")
  Integer idUser2;

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
