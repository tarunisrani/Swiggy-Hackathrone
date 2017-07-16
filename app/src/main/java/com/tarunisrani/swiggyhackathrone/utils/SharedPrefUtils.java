package com.tarunisrani.swiggyhackathrone.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tarunisrani on 7/16/17.
 */

public class SharedPrefUtils {
    public void setUserID(Context context, String uid){
        SharedPreferences sharedPreferences = context.getSharedPreferences("CUSTOMER", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("MOBNO", uid).commit();
    }

    public String fetchUserID(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("CUSTOMER", Context.MODE_PRIVATE);
        return sharedPreferences.getString("MOBNO", "");
    }

    public void setUserLocation(Context context, String uid){
        SharedPreferences sharedPreferences = context.getSharedPreferences("CUSTOMER", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("LOCATION", uid).commit();
    }

    public String fetchUserLocation(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("CUSTOMER", Context.MODE_PRIVATE);
        return sharedPreferences.getString("LOCATION", "");
    }
}
