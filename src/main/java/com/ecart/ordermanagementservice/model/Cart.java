package com.ecart.ordermanagementservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "cart")

public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private long product_id;
	
	private int quantity;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProduct_id() {
		return product_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	
}
