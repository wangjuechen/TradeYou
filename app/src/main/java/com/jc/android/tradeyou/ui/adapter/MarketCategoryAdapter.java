package com.jc.android.tradeyou.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jc.android.tradeyou.BuildConfig;
import com.jc.android.tradeyou.R;
import com.jc.android.tradeyou.data.models.category.Subcategory;
import com.jc.android.tradeyou.ui.activity.ListingActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarketCategoryAdapter extends RecyclerView.Adapter<MarketCategoryAdapter.CategoryAdapterViewHolder> {

    private static final String TAG = MarketCategoryAdapter.class.getSimpleName();

    private final Context mContext;
    private List<Subcategory> mSubcategoryList = new ArrayList<>();

    /**
     *
     * @param context
     * @param CategoryList is entire category including marketplace, jobs, motors, and properties,
     *                     a sublist is needed for marketPlace category
     */
    public MarketCategoryAdapter(Context context, List<Subcategory> CategoryList) {
        this.mContext = context;
        int mMarketPlaceSubcategoryStartIndex = 3;
        this.mSubcategoryList = CategoryList.subList(mMarketPlaceSubcategoryStartIndex, CategoryList.size());
    }

    @Override
    public CategoryAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        boolean shouldAttachToParent = false;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, shouldAttachToParent);

        return new CategoryAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryAdapterViewHolder holder, int position) {

        holder.tv_categoryName.setText(mSubcategoryList.get(holder.getAdapterPosition()).getName());

        displayAlphabet(holder);

    }

    /**
     * For display alphabet letter before category name
     * @param holder
     */
    private void displayAlphabet(CategoryAdapterViewHolder holder) {

        holder.tv_alphabet.setText(mSubcategoryList.get(holder.getAdapterPosition()).getName().substring(0, 1));

        if (holder.getAdapterPosition() > 0) {

            if (mSubcategoryList.get(holder.getAdapterPosition()).getName().substring(0, 1)
                    .equals(mSubcategoryList.get(holder.getAdapterPosition() - 1).getName().substring(0, 1))) {

                holder.tv_alphabet.setVisibility(View.INVISIBLE);

            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return (null != mSubcategoryList ? mSubcategoryList.size() : 0);
    }

    public class CategoryAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.text_category_name)
        TextView tv_categoryName;

        @BindView(R.id.text_alphabet)
        TextView tv_alphabet;

        public CategoryAdapterViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            ButterKnife.bind(this, itemView);

        }

        @Override
        public void onClick(View v) {

            String clickedCategoryName = mSubcategoryList.get(getAdapterPosition()).getName();

            String clickedCategoryNumber = mSubcategoryList.get(getAdapterPosition()).getIdentifier_number();

            startCategoryListActivity(clickedCategoryName, clickedCategoryNumber);

            if (BuildConfig.DEBUG) Log.d(TAG, "Clicked Category in subCategory, " + clickedCategoryName + clickedCategoryNumber);

        }

        private void startCategoryListActivity(String CategoryName, String CategoryNumber) {

            Bundle bundle = new Bundle();

            bundle.putString(ListingActivity.EXTRA_LISTING_NAME, CategoryName);

            bundle.putString(ListingActivity.EXTRA_LISTING_NUMBER, CategoryNumber);

            Intent intent = new Intent(mContext, ListingActivity.class);

            intent.putExtras(bundle);

            mContext.startActivity(intent);

        }
    }
}
