package com.jc.android.tradeyou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jc.android.tradeyou.BuildConfig;
import com.jc.android.tradeyou.R;
import com.jc.android.tradeyou.data.api.ServiceGenerator;
import com.jc.android.tradeyou.data.api.TradeMeApi;
import com.jc.android.tradeyou.data.api.util.APIError;
import com.jc.android.tradeyou.data.api.util.ErrorUtils;
import com.jc.android.tradeyou.data.models.details.ItemDetails;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();

    public static final String EXTRA_LISTING_ID = "com.tradeyou.extras.EXTRA_LISTING_ID";

    private int mListingId;

    @BindView(R.id.text_detail_page_id)
    TextView mTextViewListingId;

    @BindView(R.id.text_detail_page_title)
    TextView mTextViewListingTitle;

    @BindView(R.id.image_detail_page_picture)
    ImageView mImageViewListingPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getListingIdFromListingActivity();

        initView();

        loadListingDetailsApi();

    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(0);
        }

        final CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);


        AppBarLayout appBarLayout = findViewById(R.id.appBar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset < 30) {
                    collapsingToolbar.setTitle(getResources().getString(R.string.details_title));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(getResources().getString(R.string.details_empty_title));
                    isShow = false;
                }
            }
        });

        ButterKnife.bind(this);

        mTextViewListingId.setText(String.valueOf(mListingId));
    }

    private void loadListingDetailsApi() {

        String key = BuildConfig.TRADE_ME_API_CONSUMER_KEY;
        String secret = BuildConfig.TRADE_ME_API_CONSUMER_SECRET;

        TradeMeApi mTradeMeApi = ServiceGenerator.createService(TradeMeApi.class,
                " OAuth oauth_consumer_key=\""
                        + key + "\","
                        + " oauth_signature_method=\"PLAINTEXT\", oauth_signature=\""
                        + secret + "&\"");

        mTradeMeApi.getItemDetailsFromID(String.valueOf(mListingId)).enqueue(new Callback<ItemDetails>() {
            @Override
            public void onResponse(Call<ItemDetails> call, Response<ItemDetails> response) {
                if (response.isSuccessful()) {

                    String listingTitle = response.body().getItemTitle();

                    mTextViewListingTitle.setText(listingTitle);

                    if (response.body().getItemPictureUrlCollections() != null
                            && response.body().getItemPictureUrlCollections().size() > 0) {

                        String listingPicUrl = response.body()
                                .getItemPictureUrlCollections()
                                .get(0).getUrlList()
                                .getLargeUrl();

                        Glide.with(getApplicationContext())
                                .load(listingPicUrl)
                                .into(mImageViewListingPicture);
                    } else {
                        mImageViewListingPicture.setImageResource(R.drawable.art_img_placeholder);
                    }

                } else {

                    int statusCode = response.code();

                    if (statusCode == 500)
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.error_server_issue_toast),
                                Toast.LENGTH_SHORT).show();

                    APIError error = ErrorUtils.parseError(response);

                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "Error code: " + statusCode + response.message() + error.message());
                }
            }

            @Override
            public void onFailure(Call<ItemDetails> call, Throwable t) {

                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.error_internet_issue_toast),
                            Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.error_other_issue_toast),
                            Toast.LENGTH_SHORT).show();

                    if (BuildConfig.DEBUG) Log.d(TAG, "Error: " + t.getMessage());

                }
            }
        });
    }

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
            mListingId = intent.getExtras().getInt(EXTRA_LISTING_ID);
    }

}
