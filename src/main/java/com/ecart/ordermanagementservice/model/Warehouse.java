package com.ecart.ordermanagementservice.model;

public class Warehouse {

  private long postal_code;

  private float distance_in_kilometers;

  public long getPostal_code() {
    return postal_code;
  }

  public void setPostal_code(long postal_code) {
    this.postal_code = postal_code;
  }

  public float getDistance_in_kilometers() {
    return distance_in_kilometers;
  }

  public void setDistance_in_kilometers(float distance_in_kilometers) {
    this.distance_in_kilometers = distance_in_kilometers;
  }


}
