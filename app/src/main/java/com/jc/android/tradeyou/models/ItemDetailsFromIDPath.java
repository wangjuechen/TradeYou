package com.jc.android.tradeyou.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class ItemDetailsFromIDPath {

    @SerializedName("Title")
    @Expose
    private String itemTitle;

    @SerializedName("Photos")
    @Expose
    private ArrayList<Photos> itemPictureUrl = null;

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public ArrayList<Photos> getItemPictureUrlCollections() {
        return itemPictureUrl;
    }

    public void setItemPictureUrlCollections(ArrayList<Photos> itemPictureUrl) {
        this.itemPictureUrl = itemPictureUrl;
    }
}
