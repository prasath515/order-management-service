package com.ecart.ordermanagementservice.dto;

public class ProductDto {
	
	private long product_id;
	
	private String description;
	
	private int quantity;
	
	
	public ProductDto() {}

	public ProductDto(long product_id, String description, int quantity) {
		super();
		this.product_id = product_id;
		this.description = description;
		this.quantity = quantity;
		
	}

	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
