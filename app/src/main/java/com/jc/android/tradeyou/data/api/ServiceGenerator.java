package com.jc.android.tradeyou.data.api;

import android.text.TextUtils;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static final String API_BASE_URL = "https://api.tmsandbox.co.nz/";

    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    private static final Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static Retrofit retrofit = builder.build();

    public static <S> S createService(
            Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor).
                        addInterceptor(logging);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }
}
