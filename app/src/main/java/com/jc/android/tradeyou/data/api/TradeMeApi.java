package com.jc.android.tradeyou.data.api;


import com.jc.android.tradeyou.data.models.category.Category;
import com.jc.android.tradeyou.data.models.details.ItemDetails;
import com.jc.android.tradeyou.data.models.listing.Listing;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TradeMeApi {

    @GET("v1/Categories/{number}.json")
    Call<Category> getCategory(@Path("number") String number, @Query("depth") Integer depth);

    @GET("v1/Search/General.json")
    Call<Listing> getListing(@Query("category") String category, @Query("photo_size") String photoSize);

    @GET("v1/Listings/{Id}.json")
    Call<ItemDetails> getItemDetailsFromID(@Path("Id") String listingId);
}
