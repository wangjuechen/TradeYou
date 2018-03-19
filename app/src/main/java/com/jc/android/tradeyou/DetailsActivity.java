package com.jc.android.tradeyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jc.android.tradeyou.api.APIError;
import com.jc.android.tradeyou.api.ErrorUtils;
import com.jc.android.tradeyou.api.ServiceGenerator;
import com.jc.android.tradeyou.api.TradeMeApI;
import com.jc.android.tradeyou.models.ItemDetailsFromIDPath;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    public static final String CLICKEDLISTINGID_TAG = "clickedListingId_tag";

    private int mListingId;

    private TradeMeApI mTradeMeApI;

    private TextView tv_listing_id;

    private TextView tv_listing_title;

    private ImageView iv_listing_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getListingIdFromListingActivity();

        tv_listing_id = findViewById(R.id.tv_item_listingId);

        tv_listing_title = findViewById(R.id.tv_item_listingTitle_detail_page);

        iv_listing_picture = findViewById(R.id.iv_itemImage_detail_page);

        tv_listing_id.setText(getResources().getString(R.string.listing_id_detail_page, mListingId));

        loadTradeMeApi();

    }

    private void loadTradeMeApi() {
        mTradeMeApI = ServiceGenerator.createService(TradeMeApI.class,
                " OAuth oauth_consumer_key=\"A1AC63F0332A131A78FAC304D007E7D1\", oauth_signature_method=\"PLAINTEXT\", oauth_signature=\"EC7F18B17A062962C6930A8AE88B16C7&\"");

        mTradeMeApI.getItemDetailsFromID(mListingId).enqueue(new Callback<ItemDetailsFromIDPath>() {
            @Override
            public void onResponse(Call<ItemDetailsFromIDPath> call, Response<ItemDetailsFromIDPath> response) {
                if (response.isSuccessful()) {
                    String listingTitle = response.body().getItemTitle();
                    String listingPicUrl = response.body().getItemPictureUrlCollections().get(0).get(0);

                    tv_listing_title.setText(getResources().getString(R.string.listing_title_detail_page, listingTitle));

                    Glide.with(getApplicationContext()).
                            load(listingPicUrl).
                            into(iv_listing_picture);
                } else {

                    int statusCode = response.code();

                    Toast.makeText(getApplicationContext(), "Listing fetched failed :( Please try again later", Toast.LENGTH_SHORT).show();

                    APIError error = ErrorUtils.parseError(response);

                    Log.d("ListingContentFragment", "Error code: " + statusCode + response.message() + error.message());
                }
            }

            @Override
            public void onFailure(Call<ItemDetailsFromIDPath> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "Internet is disconnected :( Check internet connection", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getApplicationContext(), "Listing fetched failed :( Please try again later", Toast.LENGTH_SHORT).show();
                    Log.d("MarketCategoryActivity", "Error: " + t.getMessage());

                }
            }
        });
    }

    private void getListingIdFromListingActivity() {
        Intent intent = getIntent();
        if (intent.getExtras() != null)
            mListingId = intent.getExtras().getInt(CLICKEDLISTINGID_TAG);
    }

}