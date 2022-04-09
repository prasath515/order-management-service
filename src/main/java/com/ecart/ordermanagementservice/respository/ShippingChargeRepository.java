package com.ecart.ordermanagementservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecart.ordermanagementservice.model.ShippingCharge;

public interface ShippingChargeRepository extends JpaRepository<ShippingCharge, Long>{

}
