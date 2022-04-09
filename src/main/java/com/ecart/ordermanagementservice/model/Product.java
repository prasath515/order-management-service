package com.ecart.ordermanagementservice.model;

public class Product {
	
	private long id;
	
	private String name;
	
	private double price;
	
	private String description;
	
	private String category;
	
	private String image;

	private double discount_percentage;
	
	private int weight_in_grams;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getCategory() {
		return category;
	}

	public String getImage() {
		return image;
	}

	public double getDiscount_percentage() {
		return discount_percentage;
	}

	public int getWeight_in_grams() {
		return weight_in_grams;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setDiscount_percentage(double discount_percentage) {
		this.discount_percentage = discount_percentage;
	}

	public void setWeight_in_grams(int weight_in_grams) {
		this.weight_in_grams = weight_in_grams;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
