package com.cse.hcmut.mobileappdev.task;

import android.content.Context;
import android.support.v4.view.ViewPager;

import java.util.concurrent.Executor;

/**
 * Created by dinhn on 4/8/2016.
 */
public class AutoIncreasePageIndexViewPagerExecutor implements Executor {

    private Context mContext;
    private ViewPager mViewPager;

    public AutoIncreasePageIndexViewPagerExecutor(Context context, ViewPager viewPager) {
        this.mContext = context;
        this.mViewPager = viewPager;
    }

    @Override
    public void execute(Runnable command) {
        if (mViewPager != null && hasAdapter()) {
            command.run();
        }
    }

    private ViewPager getViewPager() {
        return mViewPager;
    }

    private boolean hasAdapter() {
        return (null != mViewPager.getAdapter());
    }

}
