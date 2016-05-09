package com.cse.hcmut.mobileappdev.base.views.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.utils.ResourceUtils;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;

import butterknife.BindView;

/**
 * Created by dinhn on 4/10/2016.
 */
public abstract class MyBaseTwoTabLayoutChangeColorFragment extends MyBaseSingleFragment implements ObservableScrollViewCallbacks {

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

    protected final static int HEADER_TOP_LAYOUT_ID = R.id.headerTopLayout_TwoTabLayoutChangeColorFragment;

    protected final static int CONTENT_BOT_LAYOUT_ID = R.id.contentBotLayout_TwoTabLayoutChangeColorFragment;

    protected final static int SEARCH_VIEW_LAYOUT_ID = R.id.searchView_TwoTabLayoutChangeColorFragment;

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @BindView(R.id.tabLayoutWidget_TwoTabLayoutChangeColorFragment)
    protected TabLayout mTabLayout;

    @BindView(R.id.flexibleHeaderLayout_TwoTabLayoutChangeColorFragment)
    View mHeaderView;

    @BindView(R.id.scrollView_TwoTabLayoutChangeColorFragment)
    ObservableScrollView mScrollView;

    private int mBaseTranslationY = 0;


    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public MyBaseTwoTabLayoutChangeColorFragment() {
        // Required empty public constructor
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.two_fragment_change_color_tab_layout;
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        if (dragging) {
            int headerHeight = mHeaderView.getHeight();
            if (firstScroll) {
                float currentHeaderTranslationY = mHeaderView.getTranslationY();
                if (-headerHeight < currentHeaderTranslationY) {
                    mBaseTranslationY = scrollY;
                }
            }
            float headerTranslationY = ScrollUtils.getFloat(
                    -(scrollY - mBaseTranslationY),
                    -headerHeight,
                    0
            );
            mHeaderView.animate().cancel();
            mHeaderView.setTranslationY(headerTranslationY);
        }
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        mBaseTranslationY = 0;

        if (scrollState == ScrollState.DOWN) {
            showHeader();
        } else if (scrollState == ScrollState.UP) {
            int headerHeight = mHeaderView.getHeight();
            int scrollY = mScrollView.getCurrentScrollY();
            if (headerHeight <= scrollY) {
                hideHeader();
            } else {
                showHeader();
            }
        } else {
            // Even if onScrollChanged occurs without scrollY changing, header should be adjusted
            if (!headerIsShow() && !headerIsHidden()) {
                // Toolbar is moving but doesn't know which to move:
                // you can change this to hideToolbar()
                showHeader();
            }
        }
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
            mScrollView.setScrollViewCallbacks(this);
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
        FragmentManager fm = getFragmentManager();
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

    private boolean headerIsShow() {
        return mHeaderView.getTranslationY() == 0;
    }

    private boolean headerIsHidden() {
        return mHeaderView.getTranslationY() == -mHeaderView.getHeight();
    }

    private void showHeader() {
        float headerTranslationY = mHeaderView.getTranslationY();
        if (headerTranslationY != 0) {
            mHeaderView.animate().cancel();
            mHeaderView.animate().translationY(0).setDuration(200).start();
        }
    }

    private void hideHeader() {
        float headerTranslationY = mHeaderView.getTranslationY();
        int headerHeight = mHeaderView.getHeight();
        if (headerTranslationY != -headerHeight) {
            mHeaderView.animate().cancel();
            mHeaderView.animate().translationY(-headerHeight).setDuration(200).start();
        }
    }

    protected void setTabLayoutSelected(int position) {
        if (mTabLayout != null) {
            TabLayout.Tab tab = mTabLayout.getTabAt(position);
            if (tab != null) {
                tab.select();
            }
        }
    }

    protected void replaceTabLayoutColorWhenClickTab(int tabPosition) {
        Activity activity = getActivity();
        if (activity != null) {
            int color = ResourceUtils.getColorForProductType(activity, tabPosition);
            mTabLayout.setBackgroundColor(color);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------
}
