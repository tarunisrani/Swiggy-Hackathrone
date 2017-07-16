package com.tarunisrani.swiggyhackathrone.utils;

import android.content.Context;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by tarunisrani on 7/16/17.
 */

public class FirebaseUtils {

    private FirebaseDatabase database;
    private DatabaseReference global_user_reference;
    private static FirebaseUtils mInstance;

    public FirebaseUtils(Context context){
        FirebaseApp.initializeApp(context);
        database = FirebaseDatabase.getInstance();
        global_user_reference = database.getReference("swiggy").child("users");

    }

    public void sendInvite(String number){
        DatabaseReference reference = global_user_reference.child(number).push();
        reference.setValue("Hi");
    }

    public static FirebaseUtils getInstance(Context context){
        if(mInstance == null){
            mInstance = new FirebaseUtils(context);
        }
        return mInstance;
    }
    public void startListener(String number){
        global_user_reference.child(number).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("onChildAdded", dataSnapshot.toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.e("onChildChanged", dataSnapshot.toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e("onChildRemoved", dataSnapshot.toString());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.e("onChildChanged", s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
