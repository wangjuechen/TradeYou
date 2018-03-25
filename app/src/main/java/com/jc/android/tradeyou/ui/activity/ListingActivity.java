package com.jc.android.tradeyou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.jc.android.tradeyou.R;
import com.jc.android.tradeyou.ui.fragment.ListingConditionFragment;
import com.jc.android.tradeyou.ui.fragment.ListingContentFragment;

public class ListingActivity extends AppCompatActivity implements ListingConditionFragment.OnFragmentInteractionListener {

    public static final String EXTRA_LISTING_NAME = "com.tradeyou.extras.EXTRA_LISTING_NAME";

    public static final String EXTRA_LISTING_NUMBER = "com.tradeyou.extras.EXTRA_LISTING_NUMBER";

    private final String BUNDLE_CONDITION_FRAGMENT = "CONDITION_FRAGMENT";

    private final String BUNDLE_CONTENT_FRAGMENT = "CONTENT_FRAGMENT";

    private Bundle mExtrasForClickedCategoryName;

    private ListingConditionFragment mListingConditionFragment;

    private ListingContentFragment mListingContentFragment;


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
        getSupportFragmentManager().putFragment(outState, BUNDLE_CONDITION_FRAGMENT, mListingConditionFragment);
        getSupportFragmentManager().putFragment(outState, BUNDLE_CONTENT_FRAGMENT, mListingContentFragment);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mListingConditionFragment = (ListingConditionFragment) getSupportFragmentManager().getFragment(savedInstanceState, BUNDLE_CONDITION_FRAGMENT);
        mListingContentFragment = (ListingContentFragment) getSupportFragmentManager().getFragment(savedInstanceState, BUNDLE_CONTENT_FRAGMENT);
    }


    private void getIntentFromCategoryActivity() {

        Intent intent = getIntent();

        mExtrasForClickedCategoryName = intent.getExtras();
    }


    private void setUpTwoFragments() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        mListingConditionFragment = new ListingConditionFragment();
        mListingConditionFragment.setArguments(mExtrasForClickedCategoryName);

        mListingContentFragment = new ListingContentFragment();
        mListingContentFragment.setArguments(mExtrasForClickedCategoryName);


        fragmentManager.beginTransaction()
                .add(R.id.listing_condition_fragment_container, mListingConditionFragment)
                .commit();

        fragmentManager.beginTransaction()
                .add(R.id.listing_content_fragment_container, mListingContentFragment)
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
        mListingContentFragment = ListingContentFragment.newInstance(categoryNumber);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.listing_content_fragment_container, mListingContentFragment)
                .commit();
    }
}
