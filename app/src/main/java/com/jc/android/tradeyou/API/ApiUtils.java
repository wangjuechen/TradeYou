package com.jc.android.tradeyou.API;


public class ApiUtils {

    public static final String BASE_URL = "https://api.tmsandbox.co.nz/";

    public static TrademeApI getTradeMeApi() {
        return RetrofitClient.getClient(BASE_URL).create(TrademeApI.class);
    }
}
