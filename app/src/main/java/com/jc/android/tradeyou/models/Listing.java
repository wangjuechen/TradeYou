package com.jc.android.tradeyou.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Listing {

    @SerializedName("TotalCount")
    @Expose
    private Integer totalCount;

    @SerializedName("List")
    @Expose
    private List<ItemDetailsFromListing> itemDetailsList = null;


    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ItemDetailsFromListing> getItemDetailsList() {
        return itemDetailsList;
    }

    public void setItemDetailsList(List<ItemDetailsFromListing> itemDetailsList) {
        this.itemDetailsList = itemDetailsList;
    }
}
