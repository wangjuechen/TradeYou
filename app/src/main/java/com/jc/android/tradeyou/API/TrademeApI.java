package com.jc.android.tradeyou.API;


import com.jc.android.tradeyou.Models.Category;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TrademeApI {

//    @FormUrlEncoded
//    @POST("oauth2/token")
//    Call<OAuthToken> postCredentials(@Field("grant_type") String grantType);

    @GET("v1/Categories/0.json")
    Call<Category> getCategory(@Query("depth") String depth);
}
