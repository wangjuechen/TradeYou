package com.jc.android.tradeyou.data.api;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

class AuthenticationInterceptor implements Interceptor {

    private final String authToken;

    public AuthenticationInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header("Authorization", authToken);

        Request request = builder.build();
        return chain.proceed(request);
    }
}
