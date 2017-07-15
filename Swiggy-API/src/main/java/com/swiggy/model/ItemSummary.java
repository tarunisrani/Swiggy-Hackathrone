package com.swiggy.model;

public class ItemSummary {
	long item_id;
	String item_name;
	float item_price;
	int item_qty;

	public ItemSummary() {
		super();
	}

	public ItemSummary(long item_id, String item_name, float item_price, int item_qty) {
		super();
		this.item_id = item_id;
		this.item_name = item_name;
		this.item_price = item_price;
		this.item_qty = item_qty;
	}

	public long getItem_id() {
		return item_id;
	}

	public void setItem_id(long item_id) {
		this.item_id = item_id;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public float getItem_price() {
		return item_price;
	}

	public void setItem_price(float item_price) {
		this.item_price = item_price;
	}

	public int getItem_qty() {
		return item_qty;
	}

	public void setItem_qty(int item_qty) {
		this.item_qty = item_qty;
	}

}
