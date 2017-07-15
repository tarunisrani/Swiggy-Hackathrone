package com.swiggy.model;

import java.util.List;

public class OrderSummary {
	
	long restaurant_id;
	String restaurant_name;
	int quantity;
	double amount;
	List<ItemSummary> ls;

	public OrderSummary() {
		super();
	}

	public OrderSummary(long restaurant_id, String restaurant_name, int quantity, double amount, List<ItemSummary> ls) {
		super();
		this.restaurant_id = restaurant_id;
		this.restaurant_name = restaurant_name;
		this.quantity = quantity;
		this.amount = amount;
		this.ls = ls;
	}

	public long getRestaurant_id() {
		return restaurant_id;
	}

	public void setRestaurant_id(long restaurant_id) {
		this.restaurant_id = restaurant_id;
	}

	public String getRestaurant_name() {
		return restaurant_name;
	}

	public void setRestaurant_name(String restaurant_name) {
		this.restaurant_name = restaurant_name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public List<ItemSummary> getLs() {
		return ls;
	}

	public void setLs(List<ItemSummary> ls) {
		this.ls = ls;
	}

}
