package com.tarunisrani.swiggyhackathrone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tarunisrani.swiggyhackathrone.R;
import com.tarunisrani.swiggyhackathrone.listener.RestaurantListener;
import com.tarunisrani.swiggyhackathrone.model.Restaurant;

import java.util.ArrayList;

/**
 * Created by tarunisrani on 7/15/17.
 */

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder>{

    private ArrayList<Restaurant> mList;
    private Context mContext;
    private RestaurantListener mListener;

    public RestaurantListAdapter(Context context, ArrayList<Restaurant> list, RestaurantListener listener){
        mList = list;
        mContext = context;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item_layout, parent, false));
//        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Restaurant restaurant = mList.get(position);
        holder.restNameTextView.setText(restaurant.getRestName());
        holder.restRatingTextView.setText(String.valueOf(restaurant.getRating()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.onRestClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView restNameTextView;
        public TextView restRatingTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.restNameTextView = (TextView) itemView.findViewById(R.id.restNameTextView);
            this.restRatingTextView = (TextView) itemView.findViewById(R.id.restRatingTextView);
        }
    }
}
