package com.cse.hcmut.mobileappdev.navigationdrawer;

/**
 * Created by dinhn on 3/8/2016.
 */
public class NavigationDrawerItem {
    private String mTitle;
    private int mIcon;

    public NavigationDrawerItem(String title, int icon) {
        mTitle = title;
        mIcon = icon;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int icon) {
        mIcon = icon;
    }
}
