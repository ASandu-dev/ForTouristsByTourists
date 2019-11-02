package com.school.fortouristsbytourists;

public class ModelClass {

    private String mTitle;
    private String mImage;
    private String mFull_desc;
    private String mLink;

    public ModelClass() {
    }

    public ModelClass(String title, String image, String full_desc, String link) {
        mTitle = title;
        mImage = image;
        mFull_desc = full_desc;
        mLink = link;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getFull_desc() {
        return mFull_desc;
    }

    public void setFull_desc(String full_desc) {
        mFull_desc = full_desc;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }
}

