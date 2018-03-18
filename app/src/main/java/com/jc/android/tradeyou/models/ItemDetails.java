package com.jc.android.tradeyou.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemDetails {

    @SerializedName("ListingId")
    @Expose
    private int itemListingId;

    @SerializedName("Title")
    @Expose
    private int itemTitle;

    @SerializedName("PictureHref")
    @Expose
    private String itemPictureUrl;

}
