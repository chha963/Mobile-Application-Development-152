package com.cse.hcmut.mobileappdev.view.features.home.Search.modelcontroller;

/**
 * Created by dinhn on 3/26/2016.
 */
public class SearchResult {
    private String mTitle;
    private String mDescription;
    private String mIconUrl;

    public SearchResult(String title, String description, String iconUrl) {
        this.mTitle = title;
        this.mDescription = description;
        this.mIconUrl = iconUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getIconUrl() {
        return mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        mIconUrl = iconUrl;
    }
}
