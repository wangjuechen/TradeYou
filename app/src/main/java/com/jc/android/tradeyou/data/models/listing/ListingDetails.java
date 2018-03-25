package com.jc.android.tradeyou.data.models.listing;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListingDetails {

    @SerializedName("ListingId")
    @Expose
    private Integer itemListingId;

    @SerializedName("Title")
    @Expose
    private String itemTitle;

    @SerializedName("PictureHref")
    @Expose
    private String itemPictureUrl;

    public Integer getItemListingId() {
        return itemListingId;
    }

    public void setItemListingId(Integer itemListingId) {
        this.itemListingId = itemListingId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemPictureUrl() {
        return itemPictureUrl;
    }

    public void setItemPictureUrl(String itemPictureUrl) {
        this.itemPictureUrl = itemPictureUrl;
    }
}
