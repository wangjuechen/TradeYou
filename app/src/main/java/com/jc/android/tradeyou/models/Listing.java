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
    private List<ItemDetails> itemDetailsList = null;


    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ItemDetails> getItemDetailsList() {
        return itemDetailsList;
    }

    public void setItemDetailsList(List<ItemDetails> itemDetailsList) {
        this.itemDetailsList = itemDetailsList;
    }
}
