package com.jc.android.tradeyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.jc.android.tradeyou.api.APIError;
import com.jc.android.tradeyou.api.ErrorUtils;
import com.jc.android.tradeyou.api.ServiceGenerator;
import com.jc.android.tradeyou.api.TradeMeApI;
import com.jc.android.tradeyou.models.ItemDetailsFromIDPath;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    public static final String CLICKEDLISTINGID_TAG = "clickedListingId_tag";

    private int mListingId;

    private TradeMeApI mTradeMeApI;

//    @BindView(R.id.progressBar_detailPage)
//    ProgressBar progressBar_imageLoading;

    @BindView(R.id.tv_item_listingId)
    TextView tv_listing_id;

    @BindView(R.id.tv_item_listingTitle_detail_page)
    TextView tv_listing_title;

    @BindView(R.id.iv_itemImage_detail_page)
    ImageView iv_listing_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getListingIdFromListingActivity();

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(0);
        }

        final CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);


        AppBarLayout appBarLayout = findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset < 30) {
                    collapsingToolbar.setTitle("Listing Details");
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbar.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

        ButterKnife.bind(this);

        tv_listing_id.setText(String.valueOf(mListingId));

        loadTradeMeApi();

    }

    private void loadTradeMeApi() {

        String consumerKey = "A1AC63F0332A131A78FAC304D007E7D1";
        String consumerSecret = "EC7F18B17A062962C6930A8AE88B16C7";

        mTradeMeApI = ServiceGenerator.createService(TradeMeApI.class,
                " OAuth oauth_consumer_key=\"" + consumerKey + "\"," + " oauth_signature_method=\"PLAINTEXT\", oauth_signature=\"" + consumerSecret + "&\"");

        mTradeMeApI.getItemDetailsFromID(String.valueOf(mListingId)).enqueue(new Callback<ItemDetailsFromIDPath>() {
            @Override
            public void onResponse(Call<ItemDetailsFromIDPath> call, Response<ItemDetailsFromIDPath> response) {
                if (response.isSuccessful()) {

                    String listingTitle = response.body().getItemTitle();

                    tv_listing_title.setText(listingTitle);

                    if (response.body().getItemPictureUrlCollections() != null && response.body().getItemPictureUrlCollections().size() > 0) {

                        String listingPicUrl = response.body().getItemPictureUrlCollections().get(0).getUrlList().getLargeUrl();

                        Glide.with(getApplicationContext()).
                                load(listingPicUrl).
                                into(iv_listing_picture);
                    } else {
                        iv_listing_picture.setImageResource(R.drawable.placeholder);
                    }

                    //setUpProgressBarInvisible();

                } else {

                    int statusCode = response.code();

                    if (statusCode == 500)
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_server_issue_toast), Toast.LENGTH_SHORT).show();

                    APIError error = ErrorUtils.parseError(response);

                    Log.d("DetailActivity", "Error code: " + statusCode + response.message() + error.message());
                }
            }

            @Override
            public void onFailure(Call<ItemDetailsFromIDPath> call, Throwable t) {

                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "Internet is disconnected :( Check internet connection", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getApplicationContext(), "Details fetched failed :( Please try again later", Toast.LENGTH_SHORT).show();
                    Log.d("DetailActivity", "Error: " + t.getMessage());

                }
            }
        });
    }

//    private void setUpProgressBarInvisible() {
//        progressBar_imageLoading.setProgress(100);
//        progressBar_imageLoading.setVisibility(View.INVISIBLE);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getListingIdFromListingActivity() {
        Intent intent = getIntent();
        if (intent.getExtras() != null)
            mListingId = intent.getExtras().getInt(CLICKEDLISTINGID_TAG);
    }

}
