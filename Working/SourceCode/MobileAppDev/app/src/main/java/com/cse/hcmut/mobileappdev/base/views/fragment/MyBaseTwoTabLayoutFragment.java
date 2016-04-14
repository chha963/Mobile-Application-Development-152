package com.cse.hcmut.mobileappdev.base.views.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.utils.DeviceUtils;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class MyBaseTwoTabLayoutFragment extends MyBaseSingleFragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    protected abstract void onTabLayoutSelected(TabLayout.Tab tab);

    protected abstract void onTabLayoutUnselected(TabLayout.Tab tab);

    protected abstract void onTabLayoutReselected(TabLayout.Tab tab);

    protected abstract Fragment getHeaderTopFragment();

    protected abstract Fragment getContentBotFragment();

    protected abstract Fragment getSearchViewFragment();

    protected abstract String getFirstLabelTab();

    protected abstract String getSecondLabelTab();

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    protected final static int HEADER_TOP_LAYOUT_ID = R.id.headerTopLayout_TwoTabLayoutFragment;

    protected final static int CONTENT_BOT_LAYOUT_ID = R.id.contentBotLayout_TwoTabLayoutFragment;

    protected final static int SEARCH_VIEW_LAYOUT_ID = R.id.searchView_TwoTabLayoutFragment;

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @Bind(R.id.tabLayoutWidget_TwoTabLayoutFragment)
    protected TabLayout mTabLayout;

    protected FrameLayout mContentBottomFrameLayout;

    protected ScrollView mScrollView;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public MyBaseTwoTabLayoutFragment() {
        // Required empty public constructor
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.two_fragment_tab_layout;
    }


    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        if (rootView != null) {
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // First time init
        if (savedInstanceState == null) {
            setupWidgets();
            bindWidgetsWithListener();
        }
    }

    private void bindWidgetsWithListener() {
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onTabLayoutSelected(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                onTabLayoutUnselected(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                onTabLayoutReselected(tab);
            }
        });
    }

    private void setupWidgets() {
        setupTabLayout();
        replaceHeaderAndContentFragment();
    }

    private void setupTabLayout() {

        String lblTabFirst = getFirstLabelTab();
        String lblTabSecond = getSecondLabelTab();

        mTabLayout.addTab(mTabLayout.newTab().setText(lblTabFirst), true); // default is tab one
        mTabLayout.addTab(mTabLayout.newTab().setText(lblTabSecond), false);
    }

    public final void setSelectedTab(int position) {
        if (mTabLayout != null) {
            TabLayout.Tab tab = mTabLayout.getTabAt(position);
            if (tab != null) {
                tab.select();
            }
        }
    }

    protected void replaceHeaderAndContentFragment() {
        // difference
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Fragment headerTopFragment = getHeaderTopFragment();
        Fragment contentBotFragment = getContentBotFragment();
        Fragment searchViewFragment = getSearchViewFragment();

        if (headerTopFragment != null) {
            ft.replace(HEADER_TOP_LAYOUT_ID, headerTopFragment);
        }

        if (contentBotFragment != null) {
            ft.replace(CONTENT_BOT_LAYOUT_ID, contentBotFragment);
        }

        if (searchViewFragment != null) {
            ft.replace(SEARCH_VIEW_LAYOUT_ID, searchViewFragment);
        }

        ft.commit();
    }

    private void fixScrollViewWithMapFragment() {
        if (mScrollView != null && mContentBottomFrameLayout != null) {
            // Fixed Touch Scroll View With GoogleMap
            //mScrollView.addInterceptScrollView(mContentBottomFrameLayout);

            // Set Fixed Layout Height (To Fix Scroll & Google Map Display)
            ViewGroup.LayoutParams params = mContentBottomFrameLayout.getLayoutParams();
            params.height = DeviceUtils.getScreenHeight(getContext());
            params.width = DeviceUtils.getScreenWidth(getContext());
            mContentBottomFrameLayout.setLayoutParams(params);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------
}
