package com.jc.android.tradeyou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.jc.android.tradeyou.R;
import com.jc.android.tradeyou.ui.adapter.MarketCategoryAdapter;
import com.jc.android.tradeyou.data.models.category.Subcategory;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarketCategoryActivity extends AppCompatActivity {

    public static final String EXTRA_CATEGORY_LIST = "com.tradeyou.extras.EXTRA_CATEGORY_LIST";

    @BindView(R.id.rv_categoryList)
    RecyclerView mRecyclerView_CategoryList;

    private ArrayList<Subcategory> mMarketPlaceCategoryList = new ArrayList<>();

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
            mMarketPlaceCategoryList = intent.getExtras().getParcelableArrayList(EXTRA_CATEGORY_LIST);
        }
    }

    private void setupRecyclerView() {

        MarketCategoryAdapter mMarketCategoryAdapter = new MarketCategoryAdapter(this, mMarketPlaceCategoryList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mRecyclerView_CategoryList.setLayoutManager(layoutManager);

        mRecyclerView_CategoryList.setHasFixedSize(true);

        mRecyclerView_CategoryList.setAdapter(mMarketCategoryAdapter);
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


