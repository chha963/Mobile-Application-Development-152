package com.cse.hcmut.mobileappdev.base.views.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.cse.hcmut.mobileappdev.R;

import butterknife.BindView;

/**
 * Created by dinhn on 4/12/2016.
 */
public abstract class MyBaseThreeFragmentContainerWithScroll extends MyBaseSingleFragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    protected abstract Fragment getHeaderTopFragment();

    protected abstract Fragment getContentMiddleFragment();

    protected abstract Fragment getContentBotFragment();

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    protected final static int mHeaderTopId = R.id.headerTopLayout_ThreeFragmentContainerWithScroll;

    protected final static int mContentMiddleId = R.id.contentMiddleLayout_ThreeFragmentContainerWithScroll;

    protected final static int mContentBotId = R.id.contentBotLayout_ThreeFragmentContainerWithScroll;

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @BindView(R.id.headerTopLayout_ThreeFragmentContainerWithScroll)
    protected FrameLayout mHeaderTopLayout;

    @BindView(R.id.contentMiddleLayout_ThreeFragmentContainerWithScroll)
    protected FrameLayout mContentMiddleLayout;

    @BindView(R.id.contentBotLayout_ThreeFragmentContainerWithScroll)
    protected FrameLayout mContentBotLayout;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public MyBaseThreeFragmentContainerWithScroll() {
        // Required empty public constructor
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.three_fragment_container_with_scroll;
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
        if (savedInstanceState == null) {
            setupWidgets();
        }
    }

    private void setupWidgets() {
        replaceHeaderAndContentFragment();
    }

    protected void replaceHeaderAndContentFragment() {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Fragment headerTopFragment = getHeaderTopFragment();
        Fragment contentMiddleFragment = getContentMiddleFragment();
        Fragment contentBotFragment = getContentBotFragment();

        if (headerTopFragment != null) {
            ft.replace(mHeaderTopId, headerTopFragment);
        }

        if (contentMiddleFragment != null) {
            ft.replace(mContentMiddleId, contentMiddleFragment);
        }

        if (contentBotFragment != null) {
            ft.replace(mContentBotId, contentBotFragment);
        }

        ft.commit();
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------


}
