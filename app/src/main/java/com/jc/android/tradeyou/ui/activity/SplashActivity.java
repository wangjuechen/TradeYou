package com.jc.android.tradeyou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.jc.android.tradeyou.BuildConfig;
import com.jc.android.tradeyou.R;
import com.jc.android.tradeyou.data.api.ServiceGenerator;
import com.jc.android.tradeyou.data.api.TradeMeApi;
import com.jc.android.tradeyou.data.models.category.Category;
import com.jc.android.tradeyou.data.models.category.Subcategory;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    private static final int SPLASH_TIME_OUT = 1000;

    private TradeMeApi tradeMeApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadCategoryAPI();
    }

    private void loadCategoryAPI() {

        tradeMeApi = ServiceGenerator.createService(TradeMeApi.class, null);

        String allCategoryQueryNumber = "0";
        tradeMeApi.getCategory(allCategoryQueryNumber,1).enqueue(new Callback<Category>() {

            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {

                    final ArrayList<Subcategory> mAllCategoryList = response.body().getSubcategories();

                    if(BuildConfig.DEBUG) Log.d(TAG, "Loaded from API is complete");

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);

                            Bundle extra = new Bundle();

                            extra.putParcelableArrayList(MainActivity.EXTRA_CATEGORY_NAME_LIST, mAllCategoryList);

                            intent.putExtras(extra);

                            startActivity(intent);

                            finish();

                        }
                    }, SPLASH_TIME_OUT);

                } else {
                    int statusCode = response.code();

                    if (statusCode == 500)
                        Toast.makeText(SplashActivity.this, getResources().getString(R.string.error_server_issue_toast), Toast.LENGTH_SHORT).show();

                    if(BuildConfig.DEBUG) Log.d(TAG, "Error code: " + statusCode + response.message());

                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                if (t instanceof IOException) {
                    // IOException is because Internet issue
                    Toast.makeText(SplashActivity.this, getResources().getString(R.string.error_internet_issue_toast), Toast.LENGTH_SHORT).show();

                } else {
                    //Other cause which mean Object format wrong or API problem
                    if(BuildConfig.DEBUG) Log.d(TAG, "Error: " + t.getMessage());

                }
            }
        });
    }

}
