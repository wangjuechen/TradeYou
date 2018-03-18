package com.jc.android.tradeyou;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.jc.android.tradeyou.adapter.CategoryAdapter;
import com.jc.android.tradeyou.api.ApiUtils;
import com.jc.android.tradeyou.api.TradeMeApI;
import com.jc.android.tradeyou.models.Category;
import com.jc.android.tradeyou.models.SubcategoryA;
import com.jc.android.tradeyou.models.SubcategoryB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarketCategoryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView_CategoryList;

    private CategoryAdapter mCategoryAdapter;

    private TradeMeApI tradeMeApi;

    private List<SubcategoryA> mMarketPlaceCategoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_category);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadTradeMeAPI();

    }

    private void loadTradeMeAPI() {

        tradeMeApi = ApiUtils.getTradeMeApi();

        tradeMeApi.getCategory().enqueue(new Callback<Category>() {

            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {

                    mMarketPlaceCategoryList = response.body().getSubcategories();

                    setupRecyclerView();

                    Log.d("MarketCategoryActivity", "posts loaded from API");

                } else {
                    int statusCode = response.code();

                    Log.d("MarketCategoryActivity", "Error code: " + statusCode + response.message());
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(MarketCategoryActivity.this, "Internet is disconnected :( Check internet connection", Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                } else {
                    Toast.makeText(MarketCategoryActivity.this, "Data fetched failed :( Please try again later", Toast.LENGTH_SHORT).show();
                    Log.d("MarketCategoryActivity", "Error: " + t.getMessage());

                }
            }
        });
    }

    private void setupRecyclerView() {

        mCategoryAdapter = new CategoryAdapter(this, mMarketPlaceCategoryList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mRecyclerView_CategoryList = findViewById(R.id.rv_categoryList);

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


