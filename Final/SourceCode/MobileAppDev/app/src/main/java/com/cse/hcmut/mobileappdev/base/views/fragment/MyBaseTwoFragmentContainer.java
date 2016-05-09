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
 * A simple {@link Fragment} subclass.
 */
public abstract class MyBaseTwoFragmentContainer extends MyBaseSingleFragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    protected abstract Fragment getHeaderTopFragment();

    protected abstract Fragment getContentBotFragment();

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    protected final static int mHeaderTopId = R.id.headerTopLayout_TwoFragmentContainer;

    protected final static int mContentBotId = R.id.contentBotLayout_TwoFragmentContainer;

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @BindView(R.id.headerTopLayout_TwoFragmentContainer)
    protected FrameLayout mHeaderTopLayout;

    @BindView(R.id.contentBotLayout_TwoFragmentContainer)
    protected FrameLayout mContentBotLayout;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public MyBaseTwoFragmentContainer() {
        // Required empty public constructor
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.two_fragment_container;
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
        Fragment contentBotFragment = getContentBotFragment();

        if (headerTopFragment != null) {
            ft.replace(mHeaderTopId, headerTopFragment);
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
