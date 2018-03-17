package com.jc.android.tradeyou.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubcategoryB {

    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("Number")
    @Expose
    private String identifier_number;

    @SerializedName("Path")
    @Expose
    private String path;

    @SerializedName("Subcategories")
    @Expose
    private List<SubcategoryC> Subcategories = null;

    @SerializedName("Count")
    @Expose
    private Integer numberOfItem;

    @SerializedName("IsRestricted")
    @Expose
    private Boolean issRestricted;

    @SerializedName("HasLegalNotice")
    @Expose
    private Boolean hasLegalNotice;

    @SerializedName("HasClassifieds")
    @Expose
    private Boolean hasClassifieds;

    @SerializedName("AreaOfBusiness")
    @Expose
    private AreaOfBusiness areaOfBusiness;

    public enum AreaOfBusiness {
        @SerializedName("0")
        ALL,
        @SerializedName("1")
        MARKETPLACE,
        @SerializedName("2")
        PROPERTY,
        @SerializedName("3")
        MOTORS,
        @SerializedName("4")
        JOBS,
        @SerializedName("5")
        SERVICES
    }

    @SerializedName("CanHaveSecondCategory")
    @Expose
    private Boolean canHaveSecondCategory;

    @SerializedName("CanBeSecondCategory")
    @Expose
    private Boolean canBeSecondCategory;

    @SerializedName("IsLeaf")
    @Expose
    private Boolean isLeaf;

}
