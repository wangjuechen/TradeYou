package com.jc.android.tradeyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jc.android.tradeyou.api.ApiUtils;
import com.jc.android.tradeyou.api.TradeMeApI;
import com.jc.android.tradeyou.models.Category;
import com.jc.android.tradeyou.models.SubcategoryA;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String CONSUMER_KEY = "A1AC63F0332A131A78FAC304D007E7D1";
    private final String CONSUMER_SECRET = "EC7F18B17A062962C6930A8AE88B16C7";

    private String credentials = Credentials.basic(CONSUMER_KEY, CONSUMER_SECRET);

    private TradeMeApI tradeMeApi;

    private ArrayList<SubcategoryA> mMarketPlaceCategoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadTradeMeAPI();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_marketPlace:
                openMarketPlaceCategoryActivity();

                break;
            case R.id.tv_jobs:
                openJobsListingActivity();

                break;
            case R.id.tv_motors:
                openMotorsListingActivity();

                break;
            case R.id.tv_property:
                openPropertyListingsActivity();

                break;
        }
    }

    private void loadTradeMeAPI() {

        tradeMeApi = ApiUtils.getTradeMeApi();

        tradeMeApi.getCategory().enqueue(new Callback<Category>() {

            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {

                    mMarketPlaceCategoryList = response.body().getSubcategories();

                    Log.d("MainActivity", "Loaded from API is complete");

                } else {
                    int statusCode = response.code();

                    Log.d("MainActivity", "Error code: " + statusCode + response.message());
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(MainActivity.this, "Internet is disconnected :( Check internet connection", Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                } else {
                    Toast.makeText(MainActivity.this, "Data fetched failed :( Please try again later", Toast.LENGTH_SHORT).show();
                    Log.d("MarketCategoryActivity", "Error: " + t.getMessage());

                }
            }
        });
    }


    private void openMarketPlaceCategoryActivity() {

        Intent openMarketPlaceCategoryIntent = new Intent(this, MarketCategoryActivity.class);

        Bundle extra = new Bundle();

        extra.putParcelableArrayList(MarketCategoryActivity.CATEGORY_LIST_TAG, mMarketPlaceCategoryList);

        openMarketPlaceCategoryIntent.putExtras(extra);

        startActivity(openMarketPlaceCategoryIntent);
    }

    private void openJobsListingActivity() {

    }

    private void openMotorsListingActivity() {

    }

    private void openPropertyListingsActivity() {

    }

}
