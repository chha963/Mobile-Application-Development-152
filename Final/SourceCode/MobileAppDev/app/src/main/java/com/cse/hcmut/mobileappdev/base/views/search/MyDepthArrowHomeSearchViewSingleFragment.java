package com.cse.hcmut.mobileappdev.base.views.search;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.constants.MyConstantValues;

import org.cryse.widget.persistentsearch.HomeButton;

/**
 * Created by dinhn on 4/11/2016.
 */
public abstract class MyDepthArrowHomeSearchViewSingleFragment extends MyBaseSearchViewSingleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    protected void setupWidgets() {
        super.setupWidgets();
        setDepthArrowIconModeHomeButton();
    }

    private void setDepthArrowIconModeHomeButton() {

        mSearchView.setHomeButtonMode(HomeButton.IconState.ARROW);
        mSearchView.setHomeButtonOpenIconState(HomeButton.IconState.ARROW);
        mSearchView.setHomeButtonCloseIconState(HomeButton.IconState.ARROW);

        Activity activity = getActivity();

        float headArrow = MyConstantValues.ARROW_MENU_HEAD_LENGTH_MAP_FRAGMENT;
        float shaftArrow = MyConstantValues.ARROW_MENU_SHAFT_LENGTH_MAP_FRAGMENT;
        float barArrow = MyConstantValues.ARROW_MENU_BAR_LENGTH_MAP_FRAGMENT;
        float barThicknessArrow = MyConstantValues.ARROW_MENU_BAR_THICKNESS_MAP_FRAGMENT;
        float gapArrow = MyConstantValues.ARROW_MENU_GAP_SIZE_MAP_FRAGMENT;

        if (activity != null) {
            headArrow = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    headArrow,
                    activity.getResources().getDisplayMetrics()
            );

            shaftArrow = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    shaftArrow,
                    activity.getResources().getDisplayMetrics()
            );

            barArrow = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    barArrow,
                    activity.getResources().getDisplayMetrics()
            );

            barThicknessArrow = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    barThicknessArrow,
                    activity.getResources().getDisplayMetrics()
            );

            gapArrow = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    gapArrow,
                    activity.getResources().getDisplayMetrics()
            );
        }


        mSearchView.setArrowDrawableHeadLength(headArrow);
        mSearchView.setArrowDrawableShaftLength(shaftArrow);
        mSearchView.setArrowDrawableBarLength(barArrow);
        mSearchView.setArrowDrawableBarThickness(barThicknessArrow);
        mSearchView.setArrowDrawableGapSize(gapArrow);

    }

}
