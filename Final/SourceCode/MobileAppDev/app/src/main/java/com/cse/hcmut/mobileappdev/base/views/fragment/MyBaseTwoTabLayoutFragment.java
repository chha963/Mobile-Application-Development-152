package com.cse.hcmut.mobileappdev.base.views.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.R;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class MyBaseTwoTabLayoutFragment extends MyBaseSingleFragment implements ObservableScrollViewCallbacks {

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

    @BindView(R.id.tabLayoutWidget_TwoTabLayoutFragment)
    protected TabLayout mTabLayout;

    @BindView(R.id.flexibleHeaderLayout_TwoTabLayoutFragment)
    View mHeaderView;

    @BindView(R.id.scrollView_TwoTabLayoutFragment)
    ObservableScrollView mScrollView;

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

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        mHeaderView.setTranslationY(-scrollY);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
//        mBaseTranslationY = 0;
//
//        if (scrollState == ScrollState.DOWN) {
//            showHeader();
//        } else if (scrollState == ScrollState.UP) {
//            int headerHeight = mHeaderView.getHeight();
//            int scrollY = mScrollView.getCurrentScrollY();
//            if (headerHeight <= scrollY) {
//                hideHeader();
//            } else {
//                showHeader();
//            }
//        } else {
//            // Even if onScrollChanged occurs without scrollY changing, header should be adjusted
//            if (!headerIsShow() && !headerIsHidden()) {
//                // Toolbar is moving but doesn't know which to move:
//                // you can change this to hideToolbar()
//                showHeader();
//            }
//        }
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
            if (mScrollView != null) {
                mScrollView.setScrollViewCallbacks(this);
            }
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mScrollView != null) {
            // update scroll position to header (prevent header not right position when
            // opened this fragment against
            mScrollView.post(new Runnable() {
                @Override
                public void run() {
                    if (mScrollView != null) {
                        onScrollChanged(mScrollView.getScrollY(), false, false);
                    }
                }
            });
        }
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

//    private void fixScrollViewWithMapFragment() {
//        if (mScrollView != null && mContentBottomFrameLayout != null) {
//            // Fixed Touch Scroll View With GoogleMap
//            //mScrollView.addInterceptScrollView(mContentBottomFrameLayout);
//
//            // Set Fixed Layout Height (To Fix Scroll & Google Map Display)
//            ViewGroup.LayoutParams params = mContentBottomFrameLayout.getLayoutParams();
//            params.height = DeviceUtils.getScreenHeight(getContext());
//            params.width = DeviceUtils.getScreenWidth(getContext());
//            mContentBottomFrameLayout.setLayoutParams(params);
//        }
//    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------
}
