package com.tarunisrani.swiggyhackathrone.listener;

/**
 * Created by tarunisrani on 7/15/17.
 */

public interface MenuItemtListener {
    void onMenuItemClick(int position);
    void onItemAdded(int position);
    void onItemRemoved(int position);
    void onItemIncreased(int position);
    void onItemDecreased(int position);
}
