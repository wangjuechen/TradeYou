package com.jc.android.tradeyou.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {

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
    private List<SubcategoryA> subcategories = null;

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

    public List<SubcategoryA> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<SubcategoryA> subcategories) {
        this.subcategories = subcategories;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }

}
