package com.jc.android.tradeyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.android.tradeyou.api.ServiceGenerator;
import com.jc.android.tradeyou.api.TradeMeApI;
import com.jc.android.tradeyou.models.Category;
import com.jc.android.tradeyou.models.SubcategoryA;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<SubcategoryA> mAllCategoryList = new ArrayList<>();

    public static final String CATEGORY_NAME_TAG = "Category_name_tag_mainactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getCategoryListFromSplashActivity();

        if(getSupportActionBar()!=null )
            getSupportActionBar().setElevation(0);
            getSupportActionBar().hide();
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

    private void getCategoryListFromSplashActivity() {

        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            mAllCategoryList = intent.getExtras().getParcelableArrayList(CATEGORY_NAME_TAG);
        }

    }

    private void openMarketPlaceCategoryActivity() {

        Intent openMarketPlaceCategoryIntent = new Intent(this, MarketCategoryActivity.class);

        Bundle extra = new Bundle();

        extra.putParcelableArrayList(MarketCategoryActivity.CATEGORY_LIST_TAG, mAllCategoryList);

        openMarketPlaceCategoryIntent.putExtras(extra);

        startActivity(openMarketPlaceCategoryIntent);

    }

    private void openJobsListingActivity() {

        Intent openJobListingActivityIntent = new Intent(this, ListingActivity.class);

        Bundle extra = new Bundle();

        int jobCategoryIndex = 2;

        SubcategoryA jobSubcategoryList = mAllCategoryList.get(jobCategoryIndex);

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

        SubcategoryA motorsSubcategoryList = mAllCategoryList.get(0);

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

        SubcategoryA propertySubcategoryList = mAllCategoryList.get(1);

        String name = propertySubcategoryList.getName();

        String number = propertySubcategoryList.getIdentifier_number();

        extra.putString(ListingActivity.CLICKEDCATEGORYNAME_TAG, name);

        extra.putString(ListingActivity.CLICKEDCATEGORYNUMBER_TAG, number);

        openPropertyListingActivityIntent.putExtras(extra);

        startActivity(openPropertyListingActivityIntent);
    }

}
