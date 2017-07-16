package com.tarunisrani.swiggyhackathrone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tarunisrani.swiggyhackathrone.R;
import com.tarunisrani.swiggyhackathrone.listener.MenuItemtListener;
import com.tarunisrani.swiggyhackathrone.model.MenuItem;

import java.util.ArrayList;

/**
 * Created by tarunisrani on 7/15/17.
 */

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.ViewHolder>{

    private ArrayList<MenuItem> mList;
    private Context mContext;
    private MenuItemtListener mListener;
    private boolean mFromSummary;

    public MenuListAdapter(Context context, ArrayList<MenuItem> list, boolean fromSummary, MenuItemtListener listener){
        mList = list;
        mContext = context;
        mListener = listener;
        mFromSummary = fromSummary;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_item_layout, parent, false));
//        return null;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MenuItem menuItem = mList.get(position);
        holder.itemNameTextView.setText(menuItem.getItemName());
        holder.itemPriceTextView.setText(String.valueOf(menuItem.getPrice()));
        holder.itemaddremovebutton.setVisibility(mFromSummary?View.GONE:View.VISIBLE);
        holder.itemaddremovebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    if(holder.toBeAdded) {
                        mListener.onItemAdded(position);
                        holder.itemaddremovebutton.setImageResource(R.drawable.minus);
                        holder.itemqtypanel.setVisibility(View.VISIBLE);
                    }else{
                        mListener.onItemRemoved(position);
                        holder.itemaddremovebutton.setImageResource(R.drawable.plus);
                        holder.itemqtypanel.setVisibility(View.GONE);
                    }
                    holder.toBeAdded = !holder.toBeAdded;
                }
            }
        });
        holder.inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.increase();
                if(mListener!=null){
                    mListener.onItemIncreased(position);
                }
            }
        });

        holder.dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.decrease();
                if(mListener!=null){
                    mListener.onItemDecreased(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView itemNameTextView;
        public TextView itemPriceTextView;
        public TextView itemqty;
        public ImageView itemaddremovebutton;
        public LinearLayout itemqtypanel;
        public Button inc, dec;

        public boolean toBeAdded = true;

        public void increase(){
            itemqty.setText(String.valueOf(Integer.parseInt(itemqty.getText().toString())+1));
        }

        public void decrease(){
            itemqty.setText(String.valueOf(Integer.parseInt(itemqty.getText().toString())- (itemqty.getText().toString().equals("0")?0:1)));
        }

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemNameTextView = (TextView) itemView.findViewById(R.id.itemNameTextView);
            this.itemPriceTextView = (TextView) itemView.findViewById(R.id.itemPriceTextView);
            this.itemqty = (TextView) itemView.findViewById(R.id.itemqty);
            this.itemaddremovebutton = (ImageView) itemView.findViewById(R.id.itemaddremovebutton);
            this.itemqtypanel = (LinearLayout) itemView.findViewById(R.id.itemqtypanel);
            this.inc = (Button) itemView.findViewById(R.id.itemqtyinc);
            this.dec = (Button) itemView.findViewById(R.id.itemqtydec);
        }
    }
}
