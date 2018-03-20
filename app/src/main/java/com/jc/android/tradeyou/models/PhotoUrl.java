package com.jc.android.tradeyou.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getListUrl() {
        return listUrl;
    }

    public void setListUrl(String listUrl) {
        this.listUrl = listUrl;
    }

    public String getMediumUrl() {
        return mediumUrl;
    }

    public void setMediumUrl(String mediumUrl) {
        this.mediumUrl = mediumUrl;
    }

    public String getGalleryUrl() {
        return galleryUrl;
    }

    public void setGalleryUrl(String galleryUrl) {
        this.galleryUrl = galleryUrl;
    }

    public String getLargeUrl() {
        return largeUrl;
    }

    public void setLargeUrl(String largeUrl) {
        this.largeUrl = largeUrl;
    }

    public String getFullsizeUrl() {
        return fullsizeUrl;
    }

    public void setFullsizeUrl(String fullsizeUrl) {
        this.fullsizeUrl = fullsizeUrl;
    }

    public String getPlusSizeUrl() {
        return plusSizeUrl;
    }

    public void setPlusSizeUrl(String plusSizeUrl) {
        this.plusSizeUrl = plusSizeUrl;
    }
}
