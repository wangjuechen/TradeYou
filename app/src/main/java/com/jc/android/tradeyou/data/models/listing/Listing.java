package com.jc.android.tradeyou.data.models.listing;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Listing {

    @SerializedName("TotalCount")
    @Expose
    private Integer totalCount;

    @SerializedName("List")
    @Expose
    private List<ListingDetails> itemDetailsList = null;


    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ListingDetails> getItemDetailsList() {
        return itemDetailsList;
    }

    public void setItemDetailsList(List<ListingDetails> itemDetailsList) {
        this.itemDetailsList = itemDetailsList;
    }
}
