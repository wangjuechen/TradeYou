package com.jc.android.tradeyou;

import android.content.Intent;
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

    public static final String CATEGORY_LIST_TAG = "subcategoryAList";

    private RecyclerView mRecyclerView_CategoryList;

    private CategoryAdapter mCategoryAdapter;

    private ArrayList<SubcategoryA> mMarketPlaceCategoryList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_category);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getIntentFromMainActivity();

        setupRecyclerView();

    }

    private void getIntentFromMainActivity() {

        Intent intent = getIntent();

       if(intent.getExtras()!=null){
           mMarketPlaceCategoryList = (ArrayList<SubcategoryA>) intent.getExtras().getSerializable(CATEGORY_LIST_TAG);
       }
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


