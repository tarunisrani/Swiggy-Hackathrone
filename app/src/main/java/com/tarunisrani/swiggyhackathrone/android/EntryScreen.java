package com.tarunisrani.swiggyhackathrone.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.tarunisrani.swiggyhackathrone.R;
import com.tarunisrani.swiggyhackathrone.utils.APP_CONSTANTS;
import com.tarunisrani.swiggyhackathrone.utils.FirebaseUtils;
import com.tarunisrani.swiggyhackathrone.utils.SharedPrefUtils;

import java.util.ArrayList;

/**
 * Created by tarunisrani on 7/15/17.
 */

public class EntryScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText mobno;
    private FirebaseUtils firebaseUtils;
    private Spinner locationList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.entry_screen_layout);

//        firebaseUtils = FirebaseUtils.getInstance(this);

        CardView submitButton = (CardView) findViewById(R.id.entrySubmitButton);
        mobno = (EditText) findViewById(R.id.entryMobInput);
        locationList = (Spinner) findViewById(R.id.locationList);

        ArrayList<String> location_lst = new ArrayList<>();
        location_lst.add("17.93356 75.601455");
        location_lst.add("17.936483 75.615045");
        location_lst.add("17.934378 75.614938");
        location_lst.add("17.937087 75.619478");


        ArrayAdapter<String> payment_type_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, location_lst);

        locationList.setAdapter(payment_type_adapter);
        locationList.setOnItemSelectedListener(this);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSubmit();
            }
        });
    }

    private void performSubmit(){
        String mob_no = mobno.getText().toString();

//        firebaseUtils.startListener(mob_no);

        new SharedPrefUtils().setUserID(this, mob_no);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(APP_CONSTANTS.MOBNO, mob_no);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected_item = (String)parent.getSelectedItem();
        switch (parent.getId()){
            case R.id.locationList:
                new SharedPrefUtils().setUserLocation(this, selected_item);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
