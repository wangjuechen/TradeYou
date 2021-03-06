package com.jc.android.tradeyou.data.models.details;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photos {
    @SerializedName("Key")
    @Expose
    private Integer key;

    @SerializedName("Value")
    @Expose
    private PhotoUrl urlList = null;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public PhotoUrl getUrlList() {
        return urlList;
    }

    public void setUrlList(PhotoUrl urlList) {
        this.urlList = urlList;
    }
}
