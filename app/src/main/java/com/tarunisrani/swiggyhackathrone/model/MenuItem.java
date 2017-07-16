package com.tarunisrani.swiggyhackathrone.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tarunisrani on 7/15/17.
 */

public class MenuItem implements Parcelable{

    protected MenuItem(Parcel in) {
        itemId = in.readLong();
        itemName = in.readString();
        price = in.readDouble();
        qty = in.readInt();
    }

    public static final Creator<MenuItem> CREATOR = new Creator<MenuItem>() {
        @Override
        public MenuItem createFromParcel(Parcel in) {
            return new MenuItem(in);
        }

        @Override
        public MenuItem[] newArray(int size) {
            return new MenuItem[size];
        }
    };

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public MenuItem(long id, String name, double prc){
        this.itemId = id;
        this.itemName = name;
        this.price = prc;
    }

    public MenuItem(long id, String name, double prc, int qty){
        this.itemId = id;
        this.itemName = name;
        this.price = prc;
        this.qty = qty;
    }

    long itemId;
    String itemName;
    double price;

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    int qty = 0;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(itemId);
        dest.writeString(itemName);
        dest.writeDouble(price);
        dest.writeInt(qty);
    }
}
