package com.jc.android.tradeyou.api;


import com.jc.android.tradeyou.models.Category;
import com.jc.android.tradeyou.models.Listing;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface TradeMeApI {

    @GET("v1/Categories/0.json")
    Call<Category> getCategory();

    @GET("v1/Search/General.json")
    Call<Listing> getListing(@Query("category") String category);
}
