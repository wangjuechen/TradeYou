package com.jc.android.tradeyou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.jc.android.tradeyou.api.ServiceGenerator;
import com.jc.android.tradeyou.api.TradeMeApI;
import com.jc.android.tradeyou.models.Category;
import com.jc.android.tradeyou.models.SubcategoryA;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 1000;

    private TradeMeApI tradeMeApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadTradeMeAPI();
    }

    private void loadTradeMeAPI() {

        tradeMeApi = ServiceGenerator.createService(TradeMeApI.class, null);

        tradeMeApi.getCategory().enqueue(new Callback<Category>() {

            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {

                    final ArrayList<SubcategoryA> mAllCategoryList = response.body().getSubcategories();


                    Log.d("SplashActivity", "Loaded from API is complete");

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);

                            Bundle extra = new Bundle();

                            extra.putSerializable(MainActivity.CATEGORY_NAME_TAG, mAllCategoryList);

                            intent.putExtras(extra);

                            startActivity(intent);

                            finish();
                        }
                    }, SPLASH_TIME_OUT);

                } else {
                    int statusCode = response.code();

                    if (statusCode == 500)
                        Toast.makeText(SplashActivity.this, "Our serves have some issues :( They will be back shortly", Toast.LENGTH_SHORT).show();

                    Log.d("SplashActivity", "Error code: " + statusCode + response.message());

                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                if (t instanceof IOException) {
                    // IOException is because Internet issue
                    Toast.makeText(SplashActivity.this, "Internet is disconnected :( Check internet connection", Toast.LENGTH_SHORT).show();

                } else {

                    //Other cause which mean Object format wrong or API problem
                    Log.d("MarketCategoryActivity", "Error: " + t.getMessage());

                }
            }
        });

    }
}