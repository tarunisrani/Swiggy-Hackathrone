package com.tarunisrani.swiggyhackathrone.model;

/**
 * Created by tarunisrani on 7/15/17.
 */

public class Restaurant {

    long restId;

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    double rating;

    public long getRestId() {
        return restId;
    }

    public void setRestId(long restId) {
        this.restId = restId;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public Restaurant(long id, String name, double rt){
        this.restId = id;
        this.restName = name;
        this.rating = rt;
    }

    String restName;

}
