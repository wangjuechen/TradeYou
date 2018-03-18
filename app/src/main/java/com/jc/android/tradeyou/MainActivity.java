package com.jc.android.tradeyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import okhttp3.Credentials;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String CONSUMER_KEY = "A1AC63F0332A131A78FAC304D007E7D1";
    private final String CONSUMER_SECRET = "EC7F18B17A062962C6930A8AE88B16C7";

    private String credentials = Credentials.basic(CONSUMER_KEY, CONSUMER_SECRET);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    private void openMarketPlaceCategoryActivity() {

        Intent openMarketPlaceCategoryIntent = new Intent(this, MarketCategoryActivity.class);

        startActivity(openMarketPlaceCategoryIntent);
    }

    private void openJobsListingActivity() {

    }

    private void openMotorsListingActivity() {

    }

    private void openPropertyListingsActivity() {

    }

}
