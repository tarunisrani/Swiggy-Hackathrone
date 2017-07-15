package com.swiggy.model;

public class OrderDetails {
	long order_id;
    long rest_Id;
    long item_id;
    int quantity;
	String rest_Name;
	String item_Name;
	

	public OrderDetails() {
		super();
	}

	public OrderDetails(long order_id,long rest_Id,long item_id, int quantity, String rest_Name, String item_Name) {
		super();
		this.order_id = order_id;
        this.rest_Id = rest_Id;
        this.item_id = item_id;
        this.quantity = quantity;
		this.rest_Name = rest_Name;
		this.item_Name = item_Name;
	}

    public long getOrder_Id() {
		return order_id;
	}

	public void setOrder_Id(long order_id) {
		this.order_id = order_id;
	}

	public long getRest_Id() {
		return rest_Id;
	}

	public void setRest_Id(long rest_Id) {
		this.rest_Id = rest_Id;
	}

    public long getItem_Id(){
        return item_id;
    }

    public void setItem_Id(long item_id){
        this.item_id = item_id;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

	public String getRest_Name() {
		return rest_Name;
	}

	public void setRest_Name(String rest_Name) {
		this.rest_Name = rest_Name;
	}

	public String getItem_Name() {
		return item_Name;
	}

	public void setItem_Name(String item_Name) {
		this.item_Name = item_Name;
	}
}
