package com.cse.hcmut.mobileappdev.base.views.fragment;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.cse.hcmut.mobileappdev.base.views.activity.MyActivity;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

/**
 * Created by dinhn on 3/7/2016.
 */
public abstract class MyBaseSingleFragment extends Fragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    /**
     * Returns the layout id associated to the layout used in the fragment
     *
     * @return
     */
    protected abstract int getLayoutId();

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    private static final int DEFAULT_CHILD_ANIMATION = 500;

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    private static long getNextAnimationDuration(Fragment fragment, long defValue) {
        try {
            // Attempt to get the resource ID of the next animation that will be applied to the
            // given fragment
            Field nextAnimField = Fragment.class.getDeclaredField("mNextAnim");
            nextAnimField.setAccessible(true);

            int nextAnimResource = nextAnimField.getInt(fragment);
            Animation nextAnim = AnimationUtils.loadAnimation(fragment.getActivity(), nextAnimResource);

            long duration = (nextAnim == null) ? defValue : nextAnim.getDuration();

            return duration;

        } catch (NoSuchFieldException | IllegalAccessException | Resources.NotFoundException e) {
            // if can not be loaded, use default duration
            return defValue;
        }
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    // Use this variable to check time between 2 click
    public long mLastClickTime = 0;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        View view = inflater.inflate(layoutId, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initActionBar(true, "");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {

        final Fragment parent = getParentFragment();

        Animation fragmentAnimation = null;

        // Apply only if this is a child fragment, and the parent is being removed
        if (!enter && parent != null) {
            Animation doNothingAnim = new AlphaAnimation(1, 1);
            doNothingAnim.setDuration(
                    getNextAnimationDuration(
                            parent,
                            DEFAULT_CHILD_ANIMATION
                    )
            );
            fragmentAnimation = doNothingAnim;
        } else {
            fragmentAnimation = super.onCreateAnimation(transit, enter, nextAnim);
        }
        return fragmentAnimation;
    }

    protected void initActionBar(boolean showHomeButton, String title) {
        Activity currentActivity = getActivity();
        if (currentActivity != null && currentActivity instanceof MyActivity) {
            ActionBar supportActionBar = ((MyActivity) currentActivity).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setHomeButtonEnabled(showHomeButton);
                supportActionBar.setTitle(title);
            }
        }
    }

    protected void showActionbar(boolean isShown) {
        Activity currentActivity = getActivity();
        if (currentActivity != null) {
            MyActivity myActivity = (MyActivity) currentActivity;
            if (myActivity != null) {
                ActionBar supportActionBar = myActivity.getSupportActionBar();
                if (supportActionBar != null) {
                    if (isShown) {
                        supportActionBar.show();
                    } else {
                        supportActionBar.hide();
                    }
                }
            }
        }
    }
}
