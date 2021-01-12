package com.paymybuddy.app.model;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "transactions")
public class MoneyTransaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "amount_ht")
  private Double amount;

  @Column(name = "date")
  private LocalDate date;

  @Column(name = "description")
  private String description;


  @Column(name = "id_receiver")
  private Integer idReceiver;

  @Column(name = "id_sender")
  private Integer idSender;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Integer getIdReceiver() {
    return idReceiver;
  }

  public void setIdReceiver(Integer idReceiver) {
    this.idReceiver = idReceiver;
  }

  public Integer getIdSender() {
    return idSender;
  }

  public void setIdSender(Integer idSender) {
    this.idSender = idSender;
  }
}
