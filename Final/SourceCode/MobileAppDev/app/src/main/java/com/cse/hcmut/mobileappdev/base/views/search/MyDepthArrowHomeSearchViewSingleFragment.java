package com.cse.hcmut.mobileappdev.base.views.search;

import com.cse.hcmut.mobileappdev.constants.MyConstantValues;

import org.cryse.widget.persistentsearch.HomeButton;

/**
 * Created by dinhn on 4/11/2016.
 */
public abstract class MyDepthArrowHomeSearchViewSingleFragment extends MyBaseSearchViewSingleFragment {

    @Override
    protected void setupWidgets() {
        super.setupWidgets();
        setDepthArrowIconModeHomeButton();
    }

    private void setDepthArrowIconModeHomeButton() {

        mSearchView.setHomeButtonMode(HomeButton.IconState.ARROW);
        mSearchView.setHomeButtonOpenIconState(HomeButton.IconState.ARROW);
        mSearchView.setHomeButtonCloseIconState(HomeButton.IconState.ARROW);

        mSearchView.setArrowDrawableHeadLength(MyConstantValues.ARROW_MENU_HEAD_LENGTH_MAP_FRAGMENT);
        mSearchView.setArrowDrawableShaftLength(MyConstantValues.ARROW_MENU_SHAFT_LENGTH_MAP_FRAGMENT);
        mSearchView.setArrowDrawableBarLength(MyConstantValues.ARROW_MENU_BAR_LENGTH_MAP_FRAGMENT);
        mSearchView.setArrowDrawableBarThickness(MyConstantValues.ARROW_MENU_BAR_THICKNESS_MAP_FRAGMENT);
        mSearchView.setArrowDrawableGapSize(MyConstantValues.ARROW_MENU_GAP_SIZE_MAP_FRAGMENT);

    }

}
