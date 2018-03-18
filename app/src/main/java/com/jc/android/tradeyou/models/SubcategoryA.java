package com.jc.android.tradeyou.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubcategoryA implements Parcelable{

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
    private List<SubcategoryB> Subcategories = null;

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

    protected SubcategoryA(Parcel in) {
        name = in.readString();
        identifier_number = in.readString();
        path = in.readString();
        if (in.readByte() == 0) {
            numberOfItem = null;
        } else {
            numberOfItem = in.readInt();
        }
        byte tmpIssRestricted = in.readByte();
        issRestricted = tmpIssRestricted == 0 ? null : tmpIssRestricted == 1;
        byte tmpHasLegalNotice = in.readByte();
        hasLegalNotice = tmpHasLegalNotice == 0 ? null : tmpHasLegalNotice == 1;
        byte tmpHasClassifieds = in.readByte();
        hasClassifieds = tmpHasClassifieds == 0 ? null : tmpHasClassifieds == 1;
        byte tmpCanHaveSecondCategory = in.readByte();
        canHaveSecondCategory = tmpCanHaveSecondCategory == 0 ? null : tmpCanHaveSecondCategory == 1;
        byte tmpCanBeSecondCategory = in.readByte();
        canBeSecondCategory = tmpCanBeSecondCategory == 0 ? null : tmpCanBeSecondCategory == 1;
        byte tmpIsLeaf = in.readByte();
        isLeaf = tmpIsLeaf == 0 ? null : tmpIsLeaf == 1;
    }

    public static final Creator<SubcategoryA> CREATOR = new Creator<SubcategoryA>() {
        @Override
        public SubcategoryA createFromParcel(Parcel in) {
            return new SubcategoryA(in);
        }

        @Override
        public SubcategoryA[] newArray(int size) {
            return new SubcategoryA[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(identifier_number);
        dest.writeString(path);
        if (numberOfItem == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(numberOfItem);
        }
        dest.writeByte((byte) (issRestricted == null ? 0 : issRestricted ? 1 : 2));
        dest.writeByte((byte) (hasLegalNotice == null ? 0 : hasLegalNotice ? 1 : 2));
        dest.writeByte((byte) (hasClassifieds == null ? 0 : hasClassifieds ? 1 : 2));
        dest.writeByte((byte) (canHaveSecondCategory == null ? 0 : canHaveSecondCategory ? 1 : 2));
        dest.writeByte((byte) (canBeSecondCategory == null ? 0 : canBeSecondCategory ? 1 : 2));
        dest.writeByte((byte) (isLeaf == null ? 0 : isLeaf ? 1 : 2));
    }

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

    public List<SubcategoryB> getSubcategories() {
        return Subcategories;
    }

    public void setSubcategories(List<SubcategoryB> subcategories) {
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
