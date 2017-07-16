package com.tarunisrani.swiggyhackathrone.android;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tarunisrani.swiggyhackathrone.R;
import com.tarunisrani.swiggyhackathrone.adapter.MenuListAdapter;
import com.tarunisrani.swiggyhackathrone.listener.MenuItemtListener;
import com.tarunisrani.swiggyhackathrone.model.MenuItem;
import com.tarunisrani.swiggyhackathrone.utils.APP_CONSTANTS;

import java.util.ArrayList;

public class SummaryActivity extends AppCompatActivity implements MenuItemtListener {

    final int CONTACT_PICKER_REQUEST = 100;


    private RecyclerView summaryListView;
    private Button checkout;
    private Button otherRest;
    private Button invite;
    private TextView summaryRestaurantName;
    private TextView summaryTotalAmount;
    private TextView summaryTotalCount;

    private MenuListAdapter listAdapter;

    private ArrayList<MenuItem> items;

    private String restName = "";
    private boolean nextRes = false;
    private long resId;
    private double amount = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_screen_layout);


        Intent intent = getIntent();
        if(intent != null){
            nextRes = intent.getBooleanExtra(APP_CONSTANTS.NEXT_REST, false);
            items = intent.getParcelableArrayListExtra(APP_CONSTANTS.SUMMARY_ITEMS);
            restName = intent.getStringExtra(APP_CONSTANTS.RESTAURANT_NAME);
            resId = intent.getLongExtra(APP_CONSTANTS.RESTAURANT_ID, 0);
        }


        summaryListView = (RecyclerView) findViewById(R.id.summaryListView);
        otherRest = (Button) findViewById(R.id.otherRestButton);
        checkout = (Button) findViewById(R.id.checkoutButton);
        invite = (Button) findViewById(R.id.inviteButton);
        summaryRestaurantName = (TextView) findViewById(R.id.summaryRestaurantName);
        summaryTotalAmount = (TextView) findViewById(R.id.summaryAmount);
        summaryTotalCount = (TextView) findViewById(R.id.summaryTotal);

        summaryRestaurantName.setText(restName);

        if(nextRes){
            otherRest.setVisibility(View.GONE);
        }

        otherRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOtherRestaurantSelection();
            }
        });
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContactScreen();
            }
        });
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckoutSelection();
            }
        });

        summaryListView.setHasFixedSize(true);
        LinearLayoutManager linearLayout =new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        summaryListView.setLayoutManager(linearLayout);


        listAdapter = new MenuListAdapter(this, items, true, this);
        summaryListView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

        summaryTotalCount.setText(String.format("Total Items: %d", items.size()));
        amount = 0.0;
        for(MenuItem item: items){
            amount += item.getPrice()*item.getQty();
        }
        summaryTotalAmount.setText(String.format("Total Amount: %g", amount));
    }

    private void onOtherRestaurantSelection(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(APP_CONSTANTS.NEXT_REST, true);
        startActivity(intent);
        finishAffinity();
    }

    private void onCheckoutSelection(){
        startActivity(new Intent(this, CheckoutActivity.class));
        finish();
    }

    private void openContactScreen(){
        Intent it = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(it, CONTACT_PICKER_REQUEST);
    }

    private void handleContactResult(Uri uri){

        String contact_number = fetchContactDetails(uri, ContactsContract.CommonDataKinds.Phone.NUMBER);

        Toast.makeText(this, contact_number, Toast.LENGTH_SHORT).show();

//        customer_add_contact_input.setText(fetchContactDetails(uri, ContactsContract.CommonDataKinds.Phone.NUMBER));
//        customer_add_name_input.setText(fetchContactDetails(uri, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//        customer_add_organisation_input.setText(fetchContactDetails(uri, ContactsContract.CommonDataKinds.Phone.ADD));
//        customer_add_contact.setImageBitmap(loadContactPhotoThumbnail(this, fetchContactImageUri(uri)));

    }

    private String fetchContactDetails(Uri uri, String field){

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        String stringNumber = "";

        if(cursor.moveToNext()){
            int columnIndex_ID = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactID = cursor.getString(columnIndex_ID);

            int columnIndex_HASPHONENUMBER = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
            String stringHasPhoneNumber = cursor.getString(columnIndex_HASPHONENUMBER);

            if(stringHasPhoneNumber.equalsIgnoreCase("1")){
                Cursor cursorNum = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactID,
                        null,
                        null);

                //Get the first phone number
                if(cursorNum.moveToNext()){
//                    stringNumber = cursorNum.getString(cursorNum.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    stringNumber = cursorNum.getString(cursorNum.getColumnIndex(field));
                    stringNumber = stringNumber.replaceAll(" ", "");
                }

            }
        }else{
            Toast.makeText(this, "NO data!", Toast.LENGTH_LONG).show();
        }
        return stringNumber;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_REQUEST:
                    // handle contact results
                    Uri contactUri = data.getData();
//                    customer_add_contact.assignContactUri(contactUri);
                    handleContactResult(contactUri);
                    break;
            }
        } else {
            // gracefully handle failure

        }
    }
}
