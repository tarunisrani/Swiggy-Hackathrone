package com.tarunisrani.swiggyhackathrone.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.tarunisrani.swiggyhackathrone.R;
import com.tarunisrani.swiggyhackathrone.adapter.MenuListAdapter;
import com.tarunisrani.swiggyhackathrone.interactor.RestaurantListInteractor;
import com.tarunisrani.swiggyhackathrone.listener.MenuItemtListener;
import com.tarunisrani.swiggyhackathrone.listener.NetworkResponseListener;
import com.tarunisrani.swiggyhackathrone.model.MenuItem;
import com.tarunisrani.swiggyhackathrone.utils.APP_CONSTANTS;
import com.tarunisrani.swiggyhackathrone.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tarunisrani on 7/15/17.
 */

public class MenuListActivity extends AppCompatActivity implements MenuItemtListener {

    private RecyclerView menuListView;
    private MenuListAdapter listAdapter;
    private TextView totalItemsTextView;
    private TextView totalAmountTextView;
    private TextView menuRestaurantName;
    private ImageView doneButton;
    private ProgressBar menuProgressBar;

    private ArrayList<MenuItem> menuItems;
    private ArrayList<MenuItem> finalItems;

    private String restName = "";
    long resId;
    private boolean nextRes = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menulist_layout);

        Intent intent = getIntent();

        finalItems = new ArrayList<>();
        if(intent!=null){
            nextRes = intent.getBooleanExtra(APP_CONSTANTS.NEXT_REST, false);
            resId = intent.getLongExtra(APP_CONSTANTS.RESTAURANT_ID, 0);
            restName = intent.getStringExtra(APP_CONSTANTS.RESTAURANT_NAME);
            menuListView = (RecyclerView) findViewById(R.id.menuListView);
            totalItemsTextView = (TextView) findViewById(R.id.totalItemsTextView);
            totalAmountTextView = (TextView) findViewById(R.id.totalAmountTextView);
            menuRestaurantName = (TextView) findViewById(R.id.menuRestaurantName);
            doneButton = (ImageView) findViewById(R.id.menudone);
            menuProgressBar = (ProgressBar) findViewById(R.id.menuProgressBar);

            menuRestaurantName.setText(restName);

            LinearLayoutManager linearLayout =new LinearLayoutManager(this);
            linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
            menuListView.setLayoutManager(linearLayout);

            menuProgressBar.setVisibility(View.VISIBLE);
            new RestaurantListInteractor().getMenuItems(this, resId, new NetworkResponseListener<JSONArray>() {
                @Override
                public void onResponseReceived(JSONArray jsonObject) {
                    menuProgressBar.setVisibility(View.GONE);
                    onMenuItemsReceived(jsonObject);
                }

                @Override
                public void onErrorReceived(VolleyError error) {

                }
            });

            doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    performDoneOperation();
                }
            });

        }
    }

    private void onMenuItemsReceived(JSONArray jsonArray){
        try {
            menuItems = new RestaurantListInteractor().parseMenuItemList(jsonArray);
            listAdapter = new MenuListAdapter(this, menuItems, false, this);
            menuListView.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();
        }catch (JSONException exp){
            exp.printStackTrace();
        }
    }

    private void performDoneOperation(){
        if(finalItems.size() == 0){
            AppUtils.showAlertBox(this, "Error", "Please add atleast one item in the list", false, null, null);
        }else{

            new RestaurantListInteractor().submitOrderDetails(this, resId, finalItems, new NetworkResponseListener<JSONObject>() {
                @Override
                public void onResponseReceived(JSONObject jsonObject) {
                    onSubmitResponseReceived(jsonObject);
                }

                @Override
                public void onErrorReceived(VolleyError error) {

                }
            });
        }

    }

    private void onSubmitResponseReceived(JSONObject jsonObject){
        try{
            if(jsonObject.getString("status").equalsIgnoreCase("Success")){
                Intent intent = new Intent(this, SummaryActivity.class);
                intent.putParcelableArrayListExtra(APP_CONSTANTS.SUMMARY_ITEMS, finalItems);
                intent.putExtra(APP_CONSTANTS.RESTAURANT_NAME, restName);
                intent.putExtra(APP_CONSTANTS.NEXT_REST, nextRes);
                intent.putExtra(APP_CONSTANTS.RESTAURANT_ID, resId);
                startActivity(intent);
            }else{

            }
        }catch (JSONException exp){
            exp.printStackTrace();
        }
    }


    private double getTotalAmount(){
        double amount = 0.0;
        for(MenuItem item: finalItems){
            amount += item.getPrice()*item.getQty();
        }
        return amount;
    }

    @Override
    public void onMenuItemClick(int position) {

    }

    @Override
    public void onItemAdded(int position) {
        MenuItem item = menuItems.get(position);
        item.setQty(1);
        finalItems.add(item);
        totalItemsTextView.setText(String.format("Total Items: %d", finalItems.size()));
        totalAmountTextView.setText(String.format("Total Items: %g", getTotalAmount()));
    }

    @Override
    public void onItemRemoved(int position) {
        MenuItem item = menuItems.get(position);
        finalItems.remove(item);
        totalItemsTextView.setText(String.format("Total Items: %d", finalItems.size()));
        totalAmountTextView.setText(String.format("Total Items: %g", getTotalAmount()));
    }

    @Override
    public void onItemIncreased(int position) {
//        finalItems.get(finalItems.indexOf(menuItems.get(position))).setQty(finalItems.get(position).getQty()+1);
        MenuItem item = menuItems.get(position);
        item.setQty(item.getQty()+1);
        totalAmountTextView.setText(String.format("Total Items: %g", getTotalAmount()));
    }

    @Override
    public void onItemDecreased(int position) {
//        finalItems.get(finalItems.indexOf(menuItems.get(position))).setQty(finalItems.get(position).getQty()-(finalItems.get(position).getQty() == 0?0:1));
        MenuItem item = menuItems.get(position);
        item.setQty(item.getQty()-(item.getQty() == 0?0:1));
        if(item.getQty() == 0){
            finalItems.remove(item);
            totalItemsTextView.setText(String.format("Total Items: %d", finalItems.size()));
        }
        totalAmountTextView.setText(String.format("Total Items: %g", getTotalAmount()));
    }
}
