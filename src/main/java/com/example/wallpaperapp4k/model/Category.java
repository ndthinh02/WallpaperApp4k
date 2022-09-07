package com.example.wallpaperapp4k.model;

import java.io.Serializable;

public class Category implements Serializable {
    //ámkdsdmskmdskdmska
    private String category;
    private String caregoryURL;

    public Category(String category, String caregoryURL) {
        this.category = category;
        this.caregoryURL = caregoryURL;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCaregoryURL() {
        return caregoryURL;
    }

    public void setCaregoryURL(String caregoryURL) {
        this.caregoryURL = caregoryURL;
    }
}
