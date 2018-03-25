package com.jc.android.tradeyou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.jc.android.tradeyou.R;

public class ListingActivity extends AppCompatActivity implements ListingConditionFragment.OnFragmentInteractionListener {

    public static final String CLICKEDCATEGORYNAME_TAG = "ClickedCategoryInSubCategoryBName";

    public static final String CLICKEDCATEGORYNUMBER_TAG = "ClickedCategoryInSubCategoryNumber";

    private final String CONDITIONFRAGMENT_KEY = "condition_fragment_key";

    private final String CONTENTFRAGMENT_KEY = "content_fragment_key";

    private Bundle extrasForClickedCategoryName;

    private ListingConditionFragment listingConditionFragment;

    private ListingContentFragment listingContentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(0);
        }

        if (savedInstanceState == null) {

            getIntentFromCategoryActivity();

            setUpTwoFragments();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, CONDITIONFRAGMENT_KEY, listingConditionFragment);
        getSupportFragmentManager().putFragment(outState, CONTENTFRAGMENT_KEY, listingContentFragment);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        listingConditionFragment = (ListingConditionFragment) getSupportFragmentManager().getFragment(savedInstanceState, CONDITIONFRAGMENT_KEY);
        listingContentFragment = (ListingContentFragment) getSupportFragmentManager().getFragment(savedInstanceState, CONTENTFRAGMENT_KEY);
    }

    private void getIntentFromCategoryActivity() {

        Intent intent = getIntent();

        extrasForClickedCategoryName = intent.getExtras();
    }


    private void setUpTwoFragments() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        listingConditionFragment = new ListingConditionFragment();
        listingConditionFragment.setArguments(extrasForClickedCategoryName);

        listingContentFragment = new ListingContentFragment();
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
        listingContentFragment = ListingContentFragment.newInstance(categoryNumber);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.listing_content_fragment_container, listingContentFragment)
                .commit();
    }
}
