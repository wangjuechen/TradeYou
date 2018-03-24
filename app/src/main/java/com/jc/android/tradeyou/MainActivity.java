package com.jc.android.tradeyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.jc.android.tradeyou.models.category.Subcategory;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Subcategory> mAllCategoryList = new ArrayList<>();

    public static final String CATEGORY_NAME_TAG = "Category_name_tag_mainactivity";

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getCategoryListFromSplashActivity();

        ButterKnife.bind(this);

        mNavigationView.setNavigationItemSelectedListener(this);

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

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

        Subcategory jobSubcategoryList = mAllCategoryList.get(jobCategoryIndex);

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

        Subcategory motorsSubcategoryList = mAllCategoryList.get(0);

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

        Subcategory propertySubcategoryList = mAllCategoryList.get(1);

        String name = propertySubcategoryList.getName();

        String number = propertySubcategoryList.getIdentifier_number();

        extra.putString(ListingActivity.CLICKEDCATEGORYNAME_TAG, name);

        extra.putString(ListingActivity.CLICKEDCATEGORYNUMBER_TAG, number);

        openPropertyListingActivityIntent.putExtras(extra);

        startActivity(openPropertyListingActivityIntent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_notification) {

        } else if (id == R.id.nav_setting) {

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openDrawer(View view) {
        if (!mDrawerLayout.isDrawerOpen(mNavigationView)) {
            mDrawerLayout.openDrawer(mNavigationView);
        }
    }
}
