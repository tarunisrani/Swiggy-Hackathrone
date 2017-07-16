package com.tarunisrani.swiggyhackathrone.helper;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tarunisrani.swiggyhackathrone.listener.NetworkResponseListener;
import com.tarunisrani.swiggyhackathrone.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.tarunisrani.swiggyhackathrone.utils.APP_CONSTANTS.BASE_URL;

/**
 * Created by tarunisrani on 7/15/17.
 */

public class NetworkCall {

    public void getRestaurantList(Context context, boolean nextRes, String custId, double lat, double lng, final NetworkResponseListener listener){

        RequestQueue queue = Volley.newRequestQueue(context);
        String final_url = BASE_URL+"/getrestaurants?lat="+lat+"&lng="+lng+"&custId="+custId;

        if(nextRes){
            final_url = BASE_URL+"/getsecondrestaurants?orderId="+ AppUtils.GLOBAL_ORDER_ID;
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, final_url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.e("Response", response.toString());
                    listener.onResponseReceived(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onErrorReceived(error);
                }
            });
//        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


            queue.add(request);
        }else{
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, final_url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("Response", response.toString());
                    listener.onResponseReceived(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onErrorReceived(error);
                }
            });
//        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


            queue.add(request);
        }

        Log.e("Request", final_url);

    }

    public void getMenuItemList(Context context, long restId, final NetworkResponseListener listener){

        String final_url = BASE_URL+"/getrestitems?rest_Id="+restId;

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, final_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("Response", response.toString());
                listener.onResponseReceived(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorReceived(error);
            }
        });
//        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public void submitOrderSummary(Context context, JSONObject jsonObject, final NetworkResponseListener listener){
        String final_url = BASE_URL+"/saveorder";

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, final_url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response", response.toString());
                listener.onResponseReceived(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorReceived(error);
            }
        });
//        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public void fetchCheckoutDetails(Context context, long orderId, final NetworkResponseListener listener){
        String final_url = BASE_URL+"/ordersummary?orderId="+orderId;

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, final_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("Response", response.toString());
                listener.onResponseReceived(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorReceived(error);
            }
        });
//        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

}
