package com.ecart.ordermanagementservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shipping_charge")
public class ShippingCharge {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
 	private long id;
	
	private float from_weight;
	
	private float to_weight;
	
	private long from_distance;
	
	private long to_distance;
	
	private long amount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getFrom_weight() {
		return from_weight;
	}

	public void setFrom_weight(float from_weight) {
		this.from_weight = from_weight;
	}

	public float getTo_weight() {
		return to_weight;
	}

	public void setTo_weight(float to_weight) {
		this.to_weight = to_weight;
	}

	public long getFrom_distance() {
		return from_distance;
	}

	public void setFrom_distance(long from_distance) {
		this.from_distance = from_distance;
	}

	public long getTo_distance() {
		return to_distance;
	}

	public void setTo_distance(long to_distance) {
		this.to_distance = to_distance;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}
	
}
