package com.ecart.ordermanagementservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shipping_cost")
public class ShippingCost {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private float weight;

  private long distance;

  private long amount;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public float getWeight() {
    return weight;
  }

  public void setWeight(float weight) {
    this.weight = weight;
  }

  public long getDistance() {
    return distance;
  }

  public void setDistance(long distance) {
    this.distance = distance;
  }

  public long getAmount() {
    return amount;
  }

  public void setAmount(long amount) {
    this.amount = amount;
  }

}
