package com.tarunisrani.swiggyhackathrone.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.tarunisrani.swiggyhackathrone.R;
import com.tarunisrani.swiggyhackathrone.adapter.MenuListAdapter;
import com.tarunisrani.swiggyhackathrone.interactor.RestaurantListInteractor;
import com.tarunisrani.swiggyhackathrone.listener.MenuItemtListener;
import com.tarunisrani.swiggyhackathrone.listener.NetworkResponseListener;
import com.tarunisrani.swiggyhackathrone.model.MenuItem;
import com.tarunisrani.swiggyhackathrone.model.Order;
import com.tarunisrani.swiggyhackathrone.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity implements MenuItemtListener {

    private RecyclerView checkoutListView_1;
    private RecyclerView checkoutListView_2;
    private TextView checkoutRestaurantName_1;
    private TextView checkoutRestaurantName_2;
    private TextView checkoutAmount;
    private TextView checkoutTotal;
    private TextView checkoutAmountRest_1;
    private TextView checkoutAmountRest_2;
    private TextView checkoutTotal_1;
    private TextView checkoutTotal_2;
    private Button checkout;
    private Button otherRest;
    private RelativeLayout panel_1;
    private RelativeLayout panel_2;

    private MenuListAdapter listAdapter_1;
    private MenuListAdapter listAdapter_2;

    private ArrayList<MenuItem> items;

    private String restName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_screen_layout);


        new RestaurantListInteractor().getCheckoutDetails(this, AppUtils.GLOBAL_ORDER_ID, new NetworkResponseListener<JSONArray>() {
            @Override
            public void onResponseReceived(JSONArray jsonObject) {
                onCheckoutDetailsReceived(jsonObject);
            }

            @Override
            public void onErrorReceived(VolleyError error) {

            }
        });

        checkoutListView_1 = (RecyclerView) findViewById(R.id.checkoutListView_1);
        checkoutListView_2 = (RecyclerView) findViewById(R.id.checkoutListView_2);
        checkoutRestaurantName_1 = (TextView) findViewById(R.id.checkoutRestaurantName_1);
        checkoutRestaurantName_2 = (TextView) findViewById(R.id.checkoutRestaurantName_2);
        checkoutAmount = (TextView) findViewById(R.id.checkoutAmount);
        checkoutAmountRest_1 = (TextView) findViewById(R.id.checkoutAmountRest_1);
        checkoutAmountRest_2 = (TextView) findViewById(R.id.checkoutAmountRest_2);
        checkoutTotal_1 = (TextView) findViewById(R.id.checkoutTotal_1);
        checkoutTotal_2 = (TextView) findViewById(R.id.checkoutTotal_2);
        checkoutTotal = (TextView) findViewById(R.id.checkoutTotal);
        panel_1 = (RelativeLayout) findViewById(R.id.panel_1);
        panel_2 = (RelativeLayout) findViewById(R.id.panel_2);



    }

    private void onCheckoutDetailsReceived(JSONArray jsonArray){

        try{
            ArrayList<Order> orders = new RestaurantListInteractor().parseCheckoutDetails(jsonArray);
            if(orders.size() == 1){
                Log.e("Checkout Screen", "Single Order");
                checkoutRestaurantName_1.setText(orders.get(0).getRestaurant().getRestName());
                checkoutListView_1.setHasFixedSize(true);
                LinearLayoutManager linearLayout =new LinearLayoutManager(this);
                linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
                checkoutListView_1.setLayoutManager(linearLayout);

                listAdapter_1 = new MenuListAdapter(this, orders.get(0).getItems(), true, this);
                checkoutListView_1.setAdapter(listAdapter_1);
                listAdapter_1.notifyDataSetChanged();

                checkoutListView_2.setVisibility(View.GONE);
                checkoutRestaurantName_2.setVisibility(View.GONE);

                panel_1.setVisibility(View.GONE);
                panel_2.setVisibility(View.GONE);

                int total = 0;
                double amount = 0.0;
                for(MenuItem item: orders.get(0).getItems()){
                    total += item.getQty();
                    amount += item.getPrice()*item.getQty();
                }

                checkoutAmount.setText(String.format("Total Items: %d", total));
                checkoutTotal.setText(String.format("Total Amount: %g", amount));


            }else if(orders.size() == 2){

                Log.e("Checkout Screen", "Double Order");
                checkoutRestaurantName_1.setText(orders.get(0).getRestaurant().getRestName());
                checkoutListView_1.setHasFixedSize(true);
                LinearLayoutManager linearLayout_1 =new LinearLayoutManager(this);
                linearLayout_1.setOrientation(LinearLayoutManager.VERTICAL);
                checkoutListView_1.setLayoutManager(linearLayout_1);

                listAdapter_1 = new MenuListAdapter(this, orders.get(0).getItems(), true, this);
                checkoutListView_1.setAdapter(listAdapter_1);
                listAdapter_1.notifyDataSetChanged();

                checkoutRestaurantName_2.setText(orders.get(1).getRestaurant().getRestName());
                checkoutListView_2.setHasFixedSize(true);
                LinearLayoutManager linearLayout_2 =new LinearLayoutManager(this);
                linearLayout_2.setOrientation(LinearLayoutManager.VERTICAL);
                checkoutListView_2.setLayoutManager(linearLayout_2);

                listAdapter_2 = new MenuListAdapter(this, orders.get(1).getItems(), true, this);
                checkoutListView_2.setAdapter(listAdapter_2);
                listAdapter_2.notifyDataSetChanged();

                int total_1 = 0;
                double amount_1 = 0.0;
                int total_2 = 0;
                double amount_2 = 0.0;
                for(MenuItem item: orders.get(0).getItems()){
                    total_1 += item.getQty();
                    amount_1 += item.getPrice()*item.getQty();
                }
                checkoutAmountRest_1.setText(String.format("Restaurant Items: %d", total_1));
                checkoutTotal_1.setText(String.format("Restaurant Amount: %g", amount_1));

                for(MenuItem item: orders.get(1).getItems()){
                    total_2 += item.getQty();
                    amount_2 += item.getPrice()*item.getQty();
                }

                checkoutAmountRest_2.setText(String.format("Restaurant Items: %d", total_2));
                checkoutTotal_2.setText(String.format("Restaurant Amount: %g", amount_2));

                checkoutAmount.setText(String.format("Total Items: %d", total_1+total_2));
                checkoutTotal.setText(String.format("Total Amount: %g", amount_1+amount_2));

            }
        }catch (JSONException exp){
            exp.printStackTrace();
        }
    }

    @Override
    public void onMenuItemClick(int position) {

    }

    @Override
    public void onItemAdded(int position) {

    }

    @Override
    public void onItemRemoved(int position) {

    }

    @Override
    public void onItemIncreased(int position) {

    }

    @Override
    public void onItemDecreased(int position) {

    }
}
