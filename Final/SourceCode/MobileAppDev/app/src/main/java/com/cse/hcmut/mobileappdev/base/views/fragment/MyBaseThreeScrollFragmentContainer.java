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

import butterknife.Bind;

/**
 * Created by dinhn on 4/12/2016.
 */
public abstract class MyBaseThreeScrollFragmentContainer extends MyBaseSingleFragment {

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

    protected final static int mHeaderTopId = R.id.headerTopLayout_ThreeFragmentContainer;

    protected final static int mContentMiddleId = R.id.contentMiddleLayout_ThreeFragmentContainer;

    protected final static int mContentBotId = R.id.contentBotLayout_ThreeFragmentContainer;

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @Bind(R.id.headerTopLayout_ThreeFragmentContainer)
    protected FrameLayout mHeaderTopLayout;

    @Bind(R.id.contentMiddleLayout_ThreeFragmentContainer)
    protected FrameLayout mContentMiddleLayout;

    @Bind(R.id.contentBotLayout_ThreeFragmentContainer)
    protected FrameLayout mContentBotLayout;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public MyBaseThreeScrollFragmentContainer() {
        // Required empty public constructor
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.three_fragment_scroll_container;
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
