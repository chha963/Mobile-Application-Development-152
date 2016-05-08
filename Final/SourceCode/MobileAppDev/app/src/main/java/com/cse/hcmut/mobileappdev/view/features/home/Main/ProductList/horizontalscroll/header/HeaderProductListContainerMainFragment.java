package com.cse.hcmut.mobileappdev.view.features.home.Main.ProductList.horizontalscroll.header;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.models.adapters.HeaderProductListAdapter;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;
import com.cse.hcmut.mobileappdev.base.widgets.viewpager.MyViewPager;
import com.cse.hcmut.mobileappdev.task.AutoIncreasePageIndexViewPagerExecutor;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeaderProductListContainerMainFragment extends MyBaseSingleFragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    public static final int CHANGE_INDEX_VIEW_PAGER_INTERVAL = 5000;

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static Fragment newInstance() {
        Fragment fragment = new HeaderProductListContainerMainFragment();
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @Bind(R.id.viewPager_HeaderProductListMainSingleFragment)
    MyViewPager mViewPager;

    @Bind(R.id.circleIndicator_HeaderProductListMainSingleFragment)
    CircleIndicator mCircleIndicator;

    PagerAdapter mPagerAdapter = null;

    private Timer mTimerExecutor = null;

    private TimerTask mChangeIndexViewPagerRepeatedlyExecutor = null;

    boolean isEnableAutoScrollingViewPager = false;

    // Used to check if user scrolling
    int mPreviousStateViewPager = ViewPager.SCROLL_STATE_IDLE;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public HeaderProductListContainerMainFragment() {
        // Required empty public constructor
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.single_fragment_header_product_list_main;
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
            setupWidgets();
        }
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopIncreaseIndexViewPagerRepeatedlyExecutor();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopIncreaseIndexViewPagerRepeatedlyExecutor();
    }

    private void setupWidgets() {

        mPagerAdapter = new HeaderProductListAdapter(
                getActivity(),
                getChildFragmentManager()
        );
        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                handleOnPageScrollStateChanged(state);
            }
        });

        mCircleIndicator.setViewPager(mViewPager);

        int itemCount = mViewPager.getAdapter().getCount();
        int randomIndex = new Random().nextInt(999999) % itemCount;
        mViewPager.setCurrentItem(randomIndex);

        startIncreaseIndexViewPagerRepeatedlyExecutor();
    }

    private void handleOnPageScrollStateChanged(int state) {

        // User scrolling
        if (state == ViewPager.SCROLL_STATE_DRAGGING) {
            stopIncreaseIndexViewPagerRepeatedlyExecutor();
        } else if (mPreviousStateViewPager == ViewPager.SCROLL_STATE_DRAGGING) {
            startIncreaseIndexViewPagerRepeatedlyExecutor();
            Log.d("TestViewPager", "Not scrolling");
        }
        mPreviousStateViewPager = state;
    }


    private void startIncreaseIndexViewPagerRepeatedlyExecutor() {

        if (mChangeIndexViewPagerRepeatedlyExecutor == null) {

            final Handler handler = new Handler();
            mChangeIndexViewPagerRepeatedlyExecutor = new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                AutoIncreasePageIndexViewPagerExecutor increaseIndexViewPagerTask
                                        = new AutoIncreasePageIndexViewPagerExecutor(getActivity(), mViewPager);
                                increaseIndexViewPagerTask.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        int currentIndex = mViewPager.getCurrentItem();
                                        int itemCount = mViewPager.getAdapter().getCount();
                                        int nextIndex = (currentIndex + 1) % itemCount;
                                        mViewPager.setCurrentItem(nextIndex);
                                    }
                                });
                            } catch (Exception e) {
                                Log.d("RepeatTask", "Background increase index view pager not work");
                            }
                        }
                    });
                }
            };
        }

        if (mTimerExecutor == null) {
            mTimerExecutor = new Timer();
        }
        mTimerExecutor.schedule(mChangeIndexViewPagerRepeatedlyExecutor, CHANGE_INDEX_VIEW_PAGER_INTERVAL, CHANGE_INDEX_VIEW_PAGER_INTERVAL);

    }

    private void stopIncreaseIndexViewPagerRepeatedlyExecutor() {

        isEnableAutoScrollingViewPager = false;

        if (mChangeIndexViewPagerRepeatedlyExecutor != null) {
            mChangeIndexViewPagerRepeatedlyExecutor.cancel();
            mChangeIndexViewPagerRepeatedlyExecutor = null;
        }

        if (mTimerExecutor != null) {
            mTimerExecutor.cancel();
            mTimerExecutor = null;
        }

    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
