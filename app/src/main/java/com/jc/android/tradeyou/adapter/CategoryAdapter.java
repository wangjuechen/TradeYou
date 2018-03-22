package com.jc.android.tradeyou.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jc.android.tradeyou.ListingActivity;
import com.jc.android.tradeyou.R;
import com.jc.android.tradeyou.models.SubcategoryA;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryAdapterViewHolder> {

    private Context mContext;
    private List<SubcategoryA> mSubcategoryAList = new ArrayList<>();
    private final int mMarketPlaceSubcategoryStartIndex = 3;
    private String lastRowCategoryName;

    public CategoryAdapter(Context context, List<SubcategoryA> CategoryList) {
        this.mContext = context;
        this.mSubcategoryAList = CategoryList.subList(mMarketPlaceSubcategoryStartIndex, CategoryList.size());
    }

    @Override
    public CategoryAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        boolean shouldAttachToParent = false;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, shouldAttachToParent);

        CategoryAdapterViewHolder categoryViewHolder = new CategoryAdapterViewHolder(view);

        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryAdapterViewHolder holder, int position) {

        holder.tv_categoryName.setText(mSubcategoryAList.get(holder.getAdapterPosition()).getName());

        holder.tv_alphabet.setText(mSubcategoryAList.get(holder.getAdapterPosition()).getName().substring(0, 1));

        if (holder.getAdapterPosition() > 0) {

            if (mSubcategoryAList.get(holder.getAdapterPosition()).getName().substring(0, 1).equals(mSubcategoryAList.get(holder.getAdapterPosition() - 1).getName().substring(0, 1))) {

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
        return (null != mSubcategoryAList ? mSubcategoryAList.size() : 0);
    }

    public class CategoryAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_category_name)
        TextView tv_categoryName;

        @BindView(R.id.tv_alphabet)
        TextView tv_alphabet;

        public CategoryAdapterViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            ButterKnife.bind(this, itemView);

        }

        @Override
        public void onClick(View v) {

            String clickedCategoryName = mSubcategoryAList.get(getAdapterPosition()).getName();

            String clickedCategoryNumber = mSubcategoryAList.get(getAdapterPosition()).getIdentifier_number();

            startCategoryListActivity(clickedCategoryName, clickedCategoryNumber);

            Log.d("CategoryAdapter ", "Clicked Category in subCategory, " + clickedCategoryName + clickedCategoryNumber);

        }

        private void startCategoryListActivity(String CategoryName, String CategoryNumber) {

            Bundle bundle = new Bundle();

            bundle.putString(ListingActivity.CLICKEDCATEGORYNAME_TAG, CategoryName);

            bundle.putString(ListingActivity.CLICKEDCATEGORYNUMBER_TAG, CategoryNumber);

            Intent intent = new Intent(mContext, ListingActivity.class);

            intent.putExtras(bundle);

            mContext.startActivity(intent);

        }
    }
}
