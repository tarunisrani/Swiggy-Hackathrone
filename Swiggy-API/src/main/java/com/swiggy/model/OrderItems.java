package com.swiggy.model;

public class OrderItems {

	long itemId;
	int qty;

	public OrderItems() {
		super();
	}

	public OrderItems(long itemId, int qty) {
		super();
		this.itemId = itemId;
		this.qty = qty;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

}
