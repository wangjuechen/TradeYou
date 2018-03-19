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
    private Map<Integer, ArrayList<String>> itemPictureUrlCollections = null;

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public Map<Integer, ArrayList<String>> getItemPictureUrlCollections() {
        return itemPictureUrlCollections;
    }

    public void setItemPictureUrlCollections(Map<Integer, ArrayList<String>> itemPictureUrlCollections) {
        this.itemPictureUrlCollections = itemPictureUrlCollections;
    }
}
