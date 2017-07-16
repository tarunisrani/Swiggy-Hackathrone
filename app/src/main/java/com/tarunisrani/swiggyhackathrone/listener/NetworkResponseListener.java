package com.tarunisrani.swiggyhackathrone.listener;

import com.android.volley.VolleyError;

/**
 * Created by tarunisrani on 7/5/17.
 */

public interface NetworkResponseListener<T> {
    void onResponseReceived(T jsonObject);
    void onErrorReceived(VolleyError error);
}
