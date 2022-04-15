package com.ecart.ordermanagementservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecart.ordermanagementservice.model.ShippingCost;

public interface ShippingCostRepository extends JpaRepository<ShippingCost, Long> {

  @Query(value = "SELECT * FROM shipping_cost where weight >= ?1 and distance >= ?2 limit 0,1", nativeQuery = true)
  ShippingCost findShippingAmount(double weight, long distance);

}
