package com.tarunisrani.swiggyhackathrone.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by tarunisrani on 7/16/17.
 */

public class AppUtils {

    public static long GLOBAL_ORDER_ID = 0;

    public static void showAlertBox(Activity activity, String title, String msg, boolean showCancel, DialogInterface.OnClickListener positivelistener, DialogInterface.OnClickListener negativelistener){
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(title);
        alert.setMessage(msg);
        if(positivelistener!=null) {
            alert.setPositiveButton("YES", positivelistener);
        }
        if(showCancel && negativelistener!=null) {
            alert.setNegativeButton("NO", negativelistener);
        }
        alert.show();
    }
}
