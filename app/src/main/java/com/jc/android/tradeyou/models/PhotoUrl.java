package com.jc.android.tradeyou.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoUrl {

    @SerializedName("Thumbnail")
    @Expose
    private String thumbnailUrl;

    @SerializedName("List")
    @Expose
    private String listUrl;

    @SerializedName("Medium")
    @Expose
    private String mediumUrl;

    @SerializedName("Gallery")
    @Expose
    private String galleryUrl;

    @SerializedName("Large")
    @Expose
    private String largeUrl;

    @SerializedName("FullSize")
    @Expose
    private String fullsizeUrl;

    @SerializedName("PlusSize")
    @Expose
    private String plusSizeUrl;

}
