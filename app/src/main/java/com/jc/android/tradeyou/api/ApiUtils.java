package com.jc.android.tradeyou.api;


public class ApiUtils {

    public static final String BASE_URL = "https://api.tmsandbox.co.nz/";

    public static TradeMeApI getTradeMeApi() {
        return RetrofitClient.getClient(BASE_URL).create(TradeMeApI.class);
    }
}
