package com.jc.android.tradeyou;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.jc.android.tradeyou.API.ApiUtils;
import com.jc.android.tradeyou.API.TrademeApI;
import com.jc.android.tradeyou.Models.Category;
import java.io.IOException;
import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTextTitle;
    private TextView mAPiName;

    private final String CONSUMER_KEY = "A1AC63F0332A131A78FAC304D007E7D1";
    private final String CONSUMER_SECRET = "EC7F18B17A062962C6930A8AE88B16C7";

    private String credentials = Credentials.basic(CONSUMER_KEY, CONSUMER_SECRET);


    TrademeApI trademeApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTextTitle = findViewById(R.id.title);
        mAPiName = findViewById(R.id.API_name);

        trademeApi = ApiUtils.getTradeMeApi();

        loadTradeMeAPI();


    }

    private void loadTradeMeAPI() {
        trademeApi.getCategory("1").enqueue(new Callback<Category>() {

            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if(response.isSuccessful()) {

                    mAPiName.setText(response.body().getName());
                    Log.d("MainActivity", "posts loaded from API");

                }else {
                    int statusCode  = response.code();

                    Log.d("MainActivity", "Error code: " + statusCode + response.message());
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(MainActivity.this, "This is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                }
                else {
                    Toast.makeText(MainActivity.this, "Conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                    Log.d("MainActivity", "Error: " + t.getMessage());

                }
            }
        });

    }
}
