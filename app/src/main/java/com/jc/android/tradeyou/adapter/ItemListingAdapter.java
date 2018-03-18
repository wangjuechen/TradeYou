package com.jc.android.tradeyou.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.jc.android.tradeyou.models.ItemDetails;

import java.util.List;

public class ItemListingAdapter extends RecyclerView.Adapter<ItemListingAdapter.ItemListingAdapterViewHolder> {

    private Context mContext;

    private List<ItemDetails> mItemDetailsListing;

    public ItemListingAdapter(Context context, List<ItemDetails> itemDetailsList) {
        this.mContext = context;
        this.mItemDetailsListing = itemDetailsList;
    }

    @Override
    public ItemListingAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ItemListingAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return (mItemDetailsListing != null ? mItemDetailsListing.size() : 0);
    }

    public class ItemListingAdapterViewHolder extends RecyclerView.ViewHolder {
        public ItemListingAdapterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
