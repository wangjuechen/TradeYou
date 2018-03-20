package com.jc.android.tradeyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class ListingActivity extends AppCompatActivity implements ListingConditionFragment.OnFragmentInteractionListener {

    public static final String CLICKEDCATEGORYNAME_TAG = "ClickedCategoryInSubCategoryBName";

    public static final String CLICKEDCATEGORYNUMBER_TAG = "ClickedCategoryInSubCategoryNumber";

    private Bundle extrasForClickedCategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {

            getIntentFromCategoryActivity();

            setUpTwoFragments();
        }
    }

    private void getIntentFromCategoryActivity() {

        Intent intent = getIntent();

        extrasForClickedCategoryName = intent.getExtras();
    }

    private void setUpTwoFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        ListingConditionFragment listingConditionFragment = new ListingConditionFragment();
        listingConditionFragment.setArguments(extrasForClickedCategoryName);

        ListingContentFragment listingContentFragment = new ListingContentFragment();
        listingContentFragment.setArguments(extrasForClickedCategoryName);

        fragmentManager.beginTransaction()
                .add(R.id.listing_condition_fragment_container, listingConditionFragment)
                .commit();

        fragmentManager.beginTransaction()
                .add(R.id.listing_content_fragment_container, listingContentFragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFragmentInteraction(String categoryNumber) {
        ListingContentFragment newContentFragment = new ListingContentFragment();
        newContentFragment.setCategoryNumber(categoryNumber);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.listing_content_fragment_container, newContentFragment)
                .commit();
    }
}
