package com.cse.hcmut.mobileappdev.base.views.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.utils.ResourceUtils;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dinhn on 4/28/2016.
 */
public abstract class MyBaseThreeTabLayoutChangeColorFragment extends MyBaseSingleFragment implements ObservableScrollViewCallbacks {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    protected abstract void onTabLayoutSelected(TabLayout.Tab tab);

    protected abstract void onTabLayoutUnselected(TabLayout.Tab tab);

    protected abstract void onTabLayoutReselected(TabLayout.Tab tab);

    protected abstract Fragment getFirstHeaderTopFragment();

    protected abstract Fragment getSecondHeaderTopFragment();

    protected abstract Fragment getContentBotFragment();

    protected abstract String getFirstLabelTab();

    protected abstract String getSecondLabelTab();

    protected abstract String getThirdLabelTab();

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    protected final static int HEADER_FIRST_TOP_LAYOUT_ID = R.id.headerFirstTopLayout_ThreeTabLayoutChangeColorFragment;

    protected final static int HEADER_SECOND_TOP_LAYOUT_ID = R.id.headerSecondTop_ThreeTabLayoutChangeColorFragment;

    protected final static int CONTENT_BOT_LAYOUT_ID = R.id.contentBotLayout_ThreeTabLayoutChangeColorFragment;

    private static final int TIME_BOTTOM_VIEW_HANDLER_INTERVAL = 2000;

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @BindView(R.id.tabLayoutWidget_ThreeTabLayoutChangeColorFragment)
    protected TabLayout mTabLayout;

    @BindView(R.id.flexibleHeaderLayout_ThreeTabLayoutChangeColorFragment)
    View mHeaderView;

    @BindView(R.id.relativeLayoutFeatureBottom_ThreeTabLayoutChangeColorFragment)
    protected RelativeLayout mFeatureRelativeLayout;

    @BindView(R.id.imageViewFeatureBottom_ThreeTabLayoutChangeColorTabFragment)
    protected ImageView mFeatureImageView;

    @BindView(R.id.scrollView_ThreeTabLayoutChangeColorFragment)
    protected ObservableScrollView mScrollView;

    @BindView(R.id.imageViewAvatar_ThreeTabLayoutChangeColorFragment)
    protected CircleImageView mAvatarImageView;

    private int mBaseTranslationY = 0;

    private boolean isBottomViewProgressingDisappear = false;

    private boolean isFinishedTouch = false;

    private Handler mBottomViewHandler;

    private Runnable mAutoDisappearBottomViewRunnable = new Runnable() {
        @Override
        public void run() {
            hideBottomFeatureView();
            isBottomViewProgressingDisappear = true;
        }
    };

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public MyBaseThreeTabLayoutChangeColorFragment() {
        // Required empty public constructor
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.three_fragment_change_color_tab_layout;
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        if (scrollY != 0) {
            mHeaderView.setTranslationY(-scrollY);
            if (isBottomViewProgressingDisappear && !isFinishedTouch) {
                showBottomFeatureView();
                if (mBottomViewHandler != null) {
                    mBottomViewHandler.removeCallbacks(mAutoDisappearBottomViewRunnable);
                }
                isBottomViewProgressingDisappear = false;
            }
        }
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }


    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

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

        if (mBottomViewHandler != null) {
            mBottomViewHandler.postDelayed(mAutoDisappearBottomViewRunnable, TIME_BOTTOM_VIEW_HANDLER_INTERVAL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        if (rootView != null) {
            mScrollView.setScrollViewCallbacks(this);
            mBottomViewHandler = new Handler();
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

        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // display bottom view when user ended touch
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    isFinishedTouch = true;
                    if (!isBottomViewProgressingDisappear) {
                        isBottomViewProgressingDisappear = true;
                        mBottomViewHandler.postDelayed(mAutoDisappearBottomViewRunnable, TIME_BOTTOM_VIEW_HANDLER_INTERVAL);
                    }
                } else {
                    isFinishedTouch = false;
                }
                return false;
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
        String lblTabThird = getThirdLabelTab();

        mTabLayout.addTab(mTabLayout.newTab().setText(lblTabFirst), true); // default is tab one
        mTabLayout.addTab(mTabLayout.newTab().setText(lblTabSecond), false);
        mTabLayout.addTab(mTabLayout.newTab().setText(lblTabThird));
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

        Fragment headerFirstFragment = getFirstHeaderTopFragment();
        Fragment headerSecondFragment = getSecondHeaderTopFragment();
        Fragment contentBotFragment = getContentBotFragment();

        if (headerFirstFragment != null) {
            ft.replace(HEADER_FIRST_TOP_LAYOUT_ID, headerFirstFragment);
        }

        if (headerSecondFragment != null) {
            ft.replace(HEADER_SECOND_TOP_LAYOUT_ID, headerSecondFragment);
        }

        if (contentBotFragment != null) {
            ft.replace(CONTENT_BOT_LAYOUT_ID, contentBotFragment);
        }

        ft.commit();
    }


    private boolean isBottomViewDisappearing() {
        float bottomViewTranslationY = mFeatureRelativeLayout.getTranslationY();
        float bottomViewHeight = mFeatureRelativeLayout.getHeight();
        return ((bottomViewTranslationY > 0) && (bottomViewTranslationY <= bottomViewHeight));
    }

    private void showBottomFeatureView() {
        Activity activity = getActivity();
        if (activity != null && mFeatureRelativeLayout != null) {
            float bottomViewTranslationY = mFeatureRelativeLayout.getTranslationY();
            if (bottomViewTranslationY != 0) {
                mFeatureRelativeLayout.animate().cancel();
                mFeatureRelativeLayout.animate().translationY(0).setDuration(200).start();
            }
        }
    }

    private void hideBottomFeatureView() {
        Activity activity = getActivity();
        if (activity != null && mFeatureRelativeLayout != null) {
            float bottomViewTranslationY = mFeatureRelativeLayout.getTranslationY();
            int bottomViewHeight = mFeatureRelativeLayout.getHeight();
            if (bottomViewTranslationY != bottomViewHeight) {
                mFeatureRelativeLayout.animate().cancel();
                mFeatureRelativeLayout.animate().translationY(bottomViewHeight).setDuration(200).start();
            }
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

    protected void replaceWidgetsColorWhenClickTab(int tabPosition) {
        Activity activity = getActivity();
        if (activity != null) {
            int color = ResourceUtils.getColorForPersonalInfoTabPosition(activity, tabPosition);
            mTabLayout.setBackgroundColor(color);
            mFeatureRelativeLayout.setBackgroundColor(color);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
