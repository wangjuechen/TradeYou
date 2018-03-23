package com.jc.android.tradeyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class ListingActivity extends AppCompatActivity implements ListingConditionFragment.OnFragmentInteractionListener {

    public static final String CLICKEDCATEGORYNAME_TAG = "ClickedCategoryInSubCategoryBName";

    public static final String CLICKEDCATEGORYNUMBER_TAG = "ClickedCategoryInSubCategoryNumber";

    private final String LISTINGCONDITIONFRAGMENT_TAG = "listingConditionfragment_tag";

    private final String LISTINGCONTENTFRAGMENT_TAG = "listingContentfragment_tag";

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
        } else {

            listingConditionFragment = (ListingConditionFragment) getSupportFragmentManager().
                    findFragmentByTag(LISTINGCONDITIONFRAGMENT_TAG);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.listing_condition_fragment_container, listingConditionFragment, LISTINGCONDITIONFRAGMENT_TAG)
                    .commit();

        }
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
                .add(R.id.listing_condition_fragment_container, listingConditionFragment, LISTINGCONDITIONFRAGMENT_TAG)
                .commit();

        fragmentManager.beginTransaction()
                .add(R.id.listing_content_fragment_container, listingContentFragment, LISTINGCONTENTFRAGMENT_TAG)
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
