package com.tarunisrani.swiggyhackathrone.model;

import java.util.ArrayList;

/**
 * Created by tarunisrani on 7/16/17.
 */

public class Order {
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public ArrayList<MenuItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<MenuItem> items) {
        this.items = items;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    private Restaurant restaurant;
    private ArrayList<MenuItem> items;
    private double amount;
    private int total;

    public Order(Restaurant rst, ArrayList<MenuItem> list, double amnt, int ttl){
        restaurant = rst;
        items = list;
        amount = amnt;
        total = ttl;
    }
}
