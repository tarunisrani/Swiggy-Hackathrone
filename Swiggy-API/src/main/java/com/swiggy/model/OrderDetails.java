package com.swiggy.model;

import java.util.List;

public class OrderDetails {
	long orderId;
	long restId;
	List<OrderItems> oi;

	public OrderDetails() {
		super();
	}

	public OrderDetails(long orderId, long restId, List<OrderItems> oi) {
		super();
		this.orderId = orderId;
		this.restId = restId;
		this.oi = oi;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getRestId() {
		return restId;
	}

	public void setRestId(long restId) {
		this.restId = restId;
	}

	public List<OrderItems> getOi() {
		return oi;
	}

	public void setOi(List<OrderItems> oi) {
		this.oi = oi;
	}

}
