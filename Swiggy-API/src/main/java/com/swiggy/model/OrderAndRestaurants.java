package com.swiggy.model;

import java.util.List;

public class OrderAndRestaurants {
	long orderId;
	List<Restaurants> ls;

	public OrderAndRestaurants() {
		super();
	}

	public OrderAndRestaurants(long orderId, List<Restaurants> ls) {
		super();
		this.orderId = orderId;
		this.ls = ls;
	}

	public double getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public List<Restaurants> getLs() {
		return ls;
	}

	public void setLs(List<Restaurants> ls) {
		this.ls = ls;
	}

}
