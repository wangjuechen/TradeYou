package com.jc.android.tradeyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.jc.android.tradeyou.adapter.CategoryAdapter;
import com.jc.android.tradeyou.models.SubcategoryA;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarketCategoryActivity extends AppCompatActivity {

    public static final String CATEGORY_LIST_TAG = "subcategoryAList";

    @BindView(R.id.rv_categoryList)
    RecyclerView mRecyclerView_CategoryList;

    private CategoryAdapter mCategoryAdapter;

    private ArrayList<SubcategoryA> mMarketPlaceCategoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_category);

        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(0);
        }

        getIntentFromMainActivity();

        setupRecyclerView();

    }

    private void getIntentFromMainActivity() {

        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            mMarketPlaceCategoryList = intent.getExtras().getParcelableArrayList(CATEGORY_LIST_TAG);
        }
    }

    private void setupRecyclerView() {

        mCategoryAdapter = new CategoryAdapter(this, mMarketPlaceCategoryList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mRecyclerView_CategoryList.setLayoutManager(layoutManager);

        mRecyclerView_CategoryList.setHasFixedSize(true);

        mRecyclerView_CategoryList.setAdapter(mCategoryAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}


