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
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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

        Intent openJobListingActivityIntent = new Intent(this, ListingActivity.class);

        Bundle extra = new Bundle();

        SubcategoryA jobSubcategoryList = mMarketPlaceCategoryList.get(2);

        String name = jobSubcategoryList.getName();

        String number = jobSubcategoryList.getIdentifier_number();

        extra.putString(ListingActivity.CLICKEDCATEGORYNAME_TAG, name);

        extra.putString(ListingActivity.CLICKEDCATEGORYNUMBER_TAG, number);

        openJobListingActivityIntent.putExtras(extra);

        startActivity(openJobListingActivityIntent);

    }

    private void openMotorsListingActivity() {

        Intent openMotorsListingActivityIntent = new Intent(this, ListingActivity.class);

        Bundle extra = new Bundle();

        SubcategoryA motorsSubcategoryList = mMarketPlaceCategoryList.get(0);

        String name = motorsSubcategoryList.getName();

        String number = motorsSubcategoryList.getIdentifier_number();

        extra.putString(ListingActivity.CLICKEDCATEGORYNAME_TAG, name);

        extra.putString(ListingActivity.CLICKEDCATEGORYNUMBER_TAG, number);

        openMotorsListingActivityIntent.putExtras(extra);

        startActivity(openMotorsListingActivityIntent);
    }

    private void openPropertyListingsActivity() {

        Intent openPropertyListingActivityIntent = new Intent(this, ListingActivity.class);

        Bundle extra = new Bundle();

        SubcategoryA propertySubcategoryList = mMarketPlaceCategoryList.get(1);

        String name = propertySubcategoryList.getName();

        String number = propertySubcategoryList.getIdentifier_number();

        extra.putString(ListingActivity.CLICKEDCATEGORYNAME_TAG, name);

        extra.putString(ListingActivity.CLICKEDCATEGORYNUMBER_TAG, number);

        openPropertyListingActivityIntent.putExtras(extra);

        startActivity(openPropertyListingActivityIntent);
    }

}
