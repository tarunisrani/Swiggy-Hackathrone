package com.tarunisrani.swiggyhackathrone.interactor;

import android.content.Context;
import android.util.Log;

import com.tarunisrani.swiggyhackathrone.helper.NetworkCall;
import com.tarunisrani.swiggyhackathrone.listener.NetworkResponseListener;
import com.tarunisrani.swiggyhackathrone.model.MenuItem;
import com.tarunisrani.swiggyhackathrone.model.Order;
import com.tarunisrani.swiggyhackathrone.model.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tarunisrani.swiggyhackathrone.utils.AppUtils.GLOBAL_ORDER_ID;

/**
 * Created by tarunisrani on 7/15/17.
 */

public class RestaurantListInteractor {
    public void getListOfRestaurants(Context context, boolean nextRes, String custId, double lat, double lng, NetworkResponseListener listener){

        /*String restList = "[\n" +
                "  {\n" +
                "    \"rest_Id\": 107545769,\n" +
                "    \"rest_Name\": \"rest_107545769\",\n" +
                "    \"rating\": 4.3\n" +
                "  },\n" +
                "  {\n" +
                "    \"rest_Id\": 139013004,\n" +
                "    \"rest_Name\": \"rest_139013004\",\n" +
                "    \"rating\": 4.3\n" +
                "  },\n" +
                "  {\n" +
                "    \"rest_Id\": 174945365,\n" +
                "    \"rest_Name\": \"rest_174945365\",\n" +
                "    \"rating\": 4.1\n" +
                "  },\n" +
                "  {\n" +
                "    \"rest_Id\": 139015682,\n" +
                "    \"rest_Name\": \"rest_139015682\",\n" +
                "    \"rating\": 3.8\n" +
                "  },\n" +
                "  {\n" +
                "    \"rest_Id\": 73321398,\n" +
                "    \"rest_Name\": \"rest_73321398\",\n" +
                "    \"rating\": 3.5\n" +
                "  },\n" +
                "  {\n" +
                "    \"rest_Id\": 118131775,\n" +
                "    \"rest_Name\": \"rest_118131775\",\n" +
                "    \"rating\": 3.2\n" +
                "  },\n" +
                "  {\n" +
                "    \"rest_Id\": 81816984,\n" +
                "    \"rest_Name\": \"rest_81816984\",\n" +
                "    \"rating\": 2.8\n" +
                "  },\n" +
                "  {\n" +
                "    \"rest_Id\": 85753651,\n" +
                "    \"rest_Name\": \"rest_85753651\",\n" +
                "    \"rating\": 2.8\n" +
                "  },\n" +
                "  {\n" +
                "    \"rest_Id\": 127143677,\n" +
                "    \"rest_Name\": \"rest_127143677\",\n" +
                "    \"rating\": 2.6\n" +
                "  }\n" +
                "]";


        ArrayList<Restaurant> itemList = new ArrayList<>();


        JSONArray jsonArray = new JSONArray();
        try{
            jsonArray = new JSONArray(restList);

        }catch (JSONException exp){
            exp.printStackTrace();
        }

        for(int i = 0;i<jsonArray.length();i++){
            try {
                itemList.add(new Restaurant(jsonArray.getJSONObject(i).getLong("rest_Id"), jsonArray.getJSONObject(i).getString("rest_Name"), jsonArray.getJSONObject(i).getDouble("rating")));
            }catch (JSONException exp){
                exp.printStackTrace();
            }
        }*/


        new NetworkCall().getRestaurantList(context, nextRes, custId, lat, lng, listener);
    }

    public ArrayList<Restaurant> parseRestList(JSONObject restList) throws JSONException{
        ArrayList<Restaurant> itemList = new ArrayList<>();
        GLOBAL_ORDER_ID = restList.getLong("orderId");

        JSONArray jsonArray = restList.getJSONArray("ls");


        for(int i = 0;i<jsonArray.length();i++){
            try {
                itemList.add(new Restaurant(jsonArray.getJSONObject(i).getLong("rest_Id"), jsonArray.getJSONObject(i).getString("rest_Name"), jsonArray.getJSONObject(i).getDouble("rating")));
            }catch (JSONException exp){
                exp.printStackTrace();
            }
        }

        return itemList;
    }

    public ArrayList<Restaurant> parseRestList(JSONArray jsonArray) throws JSONException{
        ArrayList<Restaurant> itemList = new ArrayList<>();

        for(int i = 0;i<jsonArray.length();i++){
            try {
                itemList.add(new Restaurant(jsonArray.getJSONObject(i).getLong("rest_Id"), jsonArray.getJSONObject(i).getString("rest_Name"), jsonArray.getJSONObject(i).getDouble("rating")));
            }catch (JSONException exp){
                exp.printStackTrace();
            }
        }

        return itemList;
    }

    public void getMenuItems(Context context, long resId, NetworkResponseListener listener){
        new NetworkCall().getMenuItemList(context, resId, listener);
    }

    public ArrayList<MenuItem> parseMenuItemList(JSONArray jsonArray) throws JSONException{
//        JSONArray jsonArray = new JSONArray();
//        try{
//            jsonArray = new JSONArray(menuItems);
//
//        }catch (JSONException exp){
//            exp.printStackTrace();
//        }
        ArrayList<MenuItem> list = new ArrayList<>();
        for(int i = 0;i<jsonArray.length();i++){
            list.add(new MenuItem(jsonArray.getJSONObject(i).getLong("itemId"), jsonArray.getJSONObject(i).getString("itemName"), jsonArray.getJSONObject(i).getDouble("price")));
        }

        return list;
    }

    public ArrayList<Order> getFinalOrderDetails(){
        String checkout_string = "{\n" +
                "\"restaurants\": [{\n" +
                "\"restaurant_id\": 123,\n" +
                "\"restaurant_name\": \"Rest-1\",\n" +
                "\"quantity\": 7\n" +
                "\"amount\": 2640.0,\n" +
                "\"items\": [{\n" +
                "\"item_id\": 1,\n" +
                "\"item_name\": \"Item 1 1\",\n" +
                "\"item_price\": 2.0,\n" +
                "\"item_qty\": 3\n" +
                "},\n" +
                "{\n" +
                "\"item_id\": 2,\n" +
                "\"item_name\": \"Item 1 2\",\n" +
                "\"item_price\": 12.0,\n" +
                "\"item_qty\": 4\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"restaurant_id\": 456,\n" +
                "\"restaurant_name\": \"Rest-2\",\n" +
                "\"quantity\": 7\n" +
                "\"amount\": 12231.0,\n" +
                "\"items\": [{\n" +
                "\"item_id\": 1,\n" +
                "\"item_name\": \"Item 2 1\",\n" +
                "\"item_price\": 2.0,\n" +
                "\"item_qty\": 3\n" +
                "},\n" +
                "{\n" +
                "\"item_id\": 2,\n" +
                "\"item_name\": \"Item 2 2\",\n" +
                "\"item_price\": 12.0,\n" +
                "\"item_qty\": 4\n" +
                "}\n" +
                "]\n" +
                "}\n" +
                "]\n" +
                "}";

        JSONObject jsonObject = new JSONObject();
        ArrayList<Order> orders = new ArrayList<>();
        try{
            jsonObject = new JSONObject(checkout_string);
            JSONArray restaurants = jsonObject.getJSONArray("restaurants");
            for(int i=0;i<restaurants.length();i++){
                JSONObject restaurant = restaurants.getJSONObject(i);
                Restaurant rst = new Restaurant(restaurant.getLong("restaurant_id"), restaurant.getString("restaurant_name"), 0.0);
//                int total_qty = restaurant.getInt("quantity");
//                int total_amount = restaurant.getInt("amount");
                JSONArray item_list = restaurant.getJSONArray("items");
                double amount = restaurant.getDouble("amount");
                int quantity = restaurant.getInt("quantity");
                ArrayList<MenuItem> lst = new ArrayList<>();
                for(int j=0;j<item_list.length();j++){
                    JSONObject itm = item_list.getJSONObject(j);
                    lst.add(new MenuItem(itm.getLong("item_id"), itm.getString("item_name"), itm.getDouble("item_price")));
                }
                orders.add(new Order(rst, lst, amount, quantity));
            }
        }catch (JSONException exp){
            exp.printStackTrace();
        }

        return orders;

    }

    public void getCheckoutDetails(Context context, long orderId, NetworkResponseListener listener){
        new NetworkCall().fetchCheckoutDetails(context, orderId, listener);
    }

    public ArrayList<Order> parseCheckoutDetails(JSONArray jsonArray) throws JSONException{
        ArrayList<Order> orders = new ArrayList<>();
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int quantity = jsonObject.getInt("quantity");
            double amount = jsonObject.getInt("amount");
            Restaurant restaurant = new Restaurant(jsonObject.getLong("restaurant_id"), jsonObject.getString("restaurant_name"), 0.0);
            JSONArray array = jsonObject.getJSONArray("items");
            ArrayList<MenuItem> items = new ArrayList<>();
            for(int j=0;j< array.length();j++){
                JSONObject obj = array.getJSONObject(j);
                items.add(new MenuItem(obj.getLong("item_id"), obj.getString("item_name"), obj.getDouble("item_price"), obj.getInt("item_qty")));
                Log.e("Checkout Item", obj.getString("item_name"));
            }
            orders.add(new Order(restaurant, items, amount, quantity));
        }

        return orders;
    }

    public void submitOrderDetails(Context context, long restId, ArrayList<MenuItem> list, NetworkResponseListener listener){
        String KEY_ORDERID = "orderId";
        String KEY_RESTID = "restId";
        String KEY_ITEMS = "oi";
        String KEY_ITEMID = "itemId";
        String KEY_QTY = "qty";
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put(KEY_ORDERID, String.valueOf(GLOBAL_ORDER_ID));
            jsonObject.put(KEY_RESTID, String.valueOf(restId));

            JSONArray itemarray = new JSONArray();
            for (MenuItem item : list){
                JSONObject obj = new JSONObject();
                obj.put(KEY_ITEMID, String.valueOf(item.getItemId()));
                obj.put(KEY_QTY, String.valueOf(item.getQty()));
                itemarray.put(obj);
            }
            jsonObject.put(KEY_ITEMS, itemarray);

        }catch (JSONException exp){
            exp.printStackTrace();
        }

        Log.e("POST", jsonObject.toString());

        new NetworkCall().submitOrderSummary(context, jsonObject, listener);

    }


}
