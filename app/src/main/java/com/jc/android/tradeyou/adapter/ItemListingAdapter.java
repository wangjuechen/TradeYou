package com.jc.android.tradeyou.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jc.android.tradeyou.DetailsActivity;
import com.jc.android.tradeyou.R;
import com.jc.android.tradeyou.models.ItemDetailsFromListing;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemListingAdapter extends RecyclerView.Adapter<ItemListingAdapter.ItemListingAdapterViewHolder> {

    private Context mContext;

    private List<ItemDetailsFromListing> mItemDetailsListing = new ArrayList<>();

    public ItemListingAdapter(Context context, List<ItemDetailsFromListing> itemDetailsList) {
        this.mContext = context;
        this.mItemDetailsListing = itemDetailsList;
    }

    @Override
    public ItemListingAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        boolean shouldAttachToParent = false;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listing, parent, shouldAttachToParent);

        ItemListingAdapterViewHolder listingViewHolder = new ItemListingAdapterViewHolder(view);

        return listingViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemListingAdapterViewHolder holder, int position) {

        holder.tv_listingTitle.setText(mItemDetailsListing.get(position).getItemTitle());

        Glide.with(mContext)
                .load(mItemDetailsListing.get(position).getItemPictureUrl())
                .apply(new RequestOptions()
                .placeholder(R.drawable.placeholder))
                .into(holder.iv_listingImage);
    }

    @Override
    public int getItemCount() {
        int displayedCount = 20;
        return (mItemDetailsListing.size() < 20 ? mItemDetailsListing.size() : displayedCount);
    }

    public class ItemListingAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        @BindView(R.id.tv_item_listingTitle)
        TextView tv_listingTitle;

        @BindView(R.id.iv_item_listingPhoto)
        ImageView iv_listingImage;


        public ItemListingAdapterViewHolder(View itemView) {

            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int clickedListingId = mItemDetailsListing.get(getAdapterPosition()).getItemListingId();

            startDetailsActivity(clickedListingId);

            Log.d("ItemListingAdapter: ", "Clicked listing Id, " +  clickedListingId);

        }

        private void startDetailsActivity(int listingId) {

            Bundle bundle = new Bundle();

            bundle.putInt(DetailsActivity.CLICKEDLISTINGID_TAG, listingId);

            Intent intent = new Intent(mContext, DetailsActivity.class);

            intent.putExtras(bundle);

            mContext.startActivity(intent);

        }
    }
}
