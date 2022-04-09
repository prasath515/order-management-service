package com.ecart.ordermanagementservice.dto;

import com.ecart.ordermanagementservice.model.Product;

public class ProductResponse {

	private String message;
	
	private Product product;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
}
