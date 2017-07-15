package com.swiggy.model;

public class Restaurants {
	long rest_Id;
	String rest_Name;
	float rating;

	public Restaurants() {
		super();
	}

	public Restaurants(long rest_Id, String rest_Name, float rating) {
		super();
		this.rest_Id = rest_Id;
		this.rest_Name = rest_Name;
		this.rating = rating;
	}

	public long getRest_Id() {
		return rest_Id;
	}

	public void setRest_Id(long rest_Id) {
		this.rest_Id = rest_Id;
	}

	public String getRest_Name() {
		return rest_Name;
	}

	public void setRest_Name(String rest_Name) {
		this.rest_Name = rest_Name;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

}
