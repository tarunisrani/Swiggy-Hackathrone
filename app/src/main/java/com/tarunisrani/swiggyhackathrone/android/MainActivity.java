package com.tarunisrani.swiggyhackathrone.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.tarunisrani.swiggyhackathrone.R;
import com.tarunisrani.swiggyhackathrone.adapter.RestaurantListAdapter;
import com.tarunisrani.swiggyhackathrone.interactor.RestaurantListInteractor;
import com.tarunisrani.swiggyhackathrone.listener.NetworkResponseListener;
import com.tarunisrani.swiggyhackathrone.listener.RestaurantListener;
import com.tarunisrani.swiggyhackathrone.model.Restaurant;
import com.tarunisrani.swiggyhackathrone.utils.APP_CONSTANTS;
import com.tarunisrani.swiggyhackathrone.utils.SharedPrefUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/*
* 17.936483 75.615045
* 17.934378 75.614938
* 17.937087 75.619478
* */

public class MainActivity extends AppCompatActivity implements RestaurantListener {

    private RecyclerView restListView;
    private RestaurantListAdapter listAdapter;
    private ProgressBar restProgressBar;

    private ArrayList<Restaurant> restaurants;
    private boolean nextRes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if(intent != null){
            nextRes = intent.getBooleanExtra(APP_CONSTANTS.NEXT_REST, false);
        }


        restListView = (RecyclerView) findViewById(R.id.restListView);
        restProgressBar = (ProgressBar) findViewById(R.id.restProgressBar);

        restListView.setHasFixedSize(true);
        LinearLayoutManager linearLayout =new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        restListView.setLayoutManager(linearLayout);

        String location = new SharedPrefUtils().fetchUserLocation(this);
        String str[] = location.trim().split(" ");

//        Location location  = new GPSTracker(this).getLocation();
//        if(location!=null){
//            Log.e("Location", location.getLatitude()+" , "+location.getLongitude());

            String custId = new SharedPrefUtils().fetchUserID(this);


        restProgressBar.setVisibility(View.VISIBLE);
        if(nextRes){
            new RestaurantListInteractor().getListOfRestaurants(this, nextRes, custId, Double.parseDouble(str[0].trim()), Double.parseDouble(str[1].trim()), new NetworkResponseListener<JSONArray>() {
                @Override
                public void onResponseReceived(JSONArray jsonObject) {
                    restProgressBar.setVisibility(View.GONE);
                    onRestaurantListReceived(jsonObject);
                }

                @Override
                public void onErrorReceived(VolleyError error) {

                }
            });
        }else{
            new RestaurantListInteractor().getListOfRestaurants(this, nextRes, custId, Double.parseDouble(str[0].trim()), Double.parseDouble(str[1].trim()), new NetworkResponseListener<JSONObject>() {
                @Override
                public void onResponseReceived(JSONObject jsonObject) {
                    restProgressBar.setVisibility(View.GONE);
                    onRestaurantListReceived(jsonObject);
                }

                @Override
                public void onErrorReceived(VolleyError error) {

                }
            });
        }



//            new GPSTracker(this).stopUsingGPS();


    }

    @Override
    public void onRestClick(long position) {
//        Toast.makeText(this, restaurants.get((int)position).getRestName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MenuListActivity.class);
        intent.putExtra(APP_CONSTANTS.RESTAURANT_ID, restaurants.get((int)position).getRestId());
        intent.putExtra(APP_CONSTANTS.RESTAURANT_NAME, restaurants.get((int)position).getRestName());
        intent.putExtra(APP_CONSTANTS.NEXT_REST, nextRes);
        startActivity(intent);
    }

    private void onRestaurantListReceived(JSONObject jsonObject){
//        ArrayList<Restaurant> restList = new ArrayList<>();
        try{
            restaurants = new RestaurantListInteractor().parseRestList(jsonObject);
            listAdapter = new RestaurantListAdapter(this, restaurants, this);
            restListView.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();
        }catch (JSONException exp){
            exp.printStackTrace();
        }
    }

    private void onRestaurantListReceived(JSONArray jsonObject){
//        ArrayList<Restaurant> restList = new ArrayList<>();
        try{
            restaurants = new RestaurantListInteractor().parseRestList(jsonObject);
            listAdapter = new RestaurantListAdapter(this, restaurants, this);
            restListView.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();
        }catch (JSONException exp){
            exp.printStackTrace();
        }
    }
}
