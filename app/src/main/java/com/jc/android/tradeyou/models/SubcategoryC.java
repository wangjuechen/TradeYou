package com.jc.android.tradeyou.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SubcategoryC implements Serializable{
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
    private List<SubcategoryD> Subcategories = null;

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

    private enum AreaOfBusiness {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier_number() {
        return identifier_number;
    }

    public void setIdentifier_number(String identifier_number) {
        this.identifier_number = identifier_number;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<SubcategoryD> getSubcategories() {
        return Subcategories;
    }

    public void setSubcategories(List<SubcategoryD> subcategories) {
        Subcategories = subcategories;
    }

    public Integer getNumberOfItem() {
        return numberOfItem;
    }

    public void setNumberOfItem(Integer numberOfItem) {
        this.numberOfItem = numberOfItem;
    }

    public Boolean getIssRestricted() {
        return issRestricted;
    }

    public void setIssRestricted(Boolean issRestricted) {
        this.issRestricted = issRestricted;
    }

    public Boolean getHasLegalNotice() {
        return hasLegalNotice;
    }

    public void setHasLegalNotice(Boolean hasLegalNotice) {
        this.hasLegalNotice = hasLegalNotice;
    }

    public Boolean getHasClassifieds() {
        return hasClassifieds;
    }

    public void setHasClassifieds(Boolean hasClassifieds) {
        this.hasClassifieds = hasClassifieds;
    }

    public AreaOfBusiness getAreaOfBusiness() {
        return areaOfBusiness;
    }

    public void setAreaOfBusiness(AreaOfBusiness areaOfBusiness) {
        this.areaOfBusiness = areaOfBusiness;
    }

    public Boolean getCanHaveSecondCategory() {
        return canHaveSecondCategory;
    }

    public void setCanHaveSecondCategory(Boolean canHaveSecondCategory) {
        this.canHaveSecondCategory = canHaveSecondCategory;
    }

    public Boolean getCanBeSecondCategory() {
        return canBeSecondCategory;
    }

    public void setCanBeSecondCategory(Boolean canBeSecondCategory) {
        this.canBeSecondCategory = canBeSecondCategory;
    }

    public Boolean getLeaf() {
        return isLeaf;
    }

    public void setLeaf(Boolean leaf) {
        isLeaf = leaf;
    }
}
