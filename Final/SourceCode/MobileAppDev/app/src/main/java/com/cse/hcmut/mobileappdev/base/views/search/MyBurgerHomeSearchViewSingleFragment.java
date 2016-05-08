package com.cse.hcmut.mobileappdev.base.views.search;

import org.cryse.widget.persistentsearch.HomeButton;

/**
 * Created by dinhn on 4/10/2016.
 */
public abstract class MyBurgerHomeSearchViewSingleFragment extends MyBaseSearchViewSingleFragment {

    @Override
    protected void setupWidgets() {
        super.setupWidgets();
        //setBurgerIconModeHomeButton();
    }

    private void setBurgerIconModeHomeButton() {

        mSearchView.setHomeButtonMode(HomeButton.IconState.BURGER);
        mSearchView.setHomeButtonOpenIconState(HomeButton.IconState.ARROW);
        mSearchView.setHomeButtonCloseIconState(HomeButton.IconState.BURGER);

    }
}
