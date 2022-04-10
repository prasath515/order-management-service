package com.ecart.ordermanagementservice.model;

import java.util.Set;

import com.ecart.ordermanagementservice.dto.ProductDto;

public class CartItems {
	
	private String status;
	
	private String message;
	
	private Set<ProductDto> items;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Set<ProductDto> getItems() {
		return items;
	}

	public void setItems(Set<ProductDto> items) {
		this.items = items;
	}

}
