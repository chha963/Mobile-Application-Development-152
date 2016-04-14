package com.cse.hcmut.mobileappdev.base.views.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.home.Main.ControllerViewMainFragment;

/**
 * Created by dinhn on 4/3/2016.
 */
public abstract class MyBaseTwoTabControlFragmentContainer
                        extends MyBaseSingleFragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    protected abstract int getDefaultPosition();

    protected abstract Fragment selectFragmentWith(int tabPosition);

    /**
     *
     * @return null if display default fragment with no transaction animation
     */
    protected abstract FragmentTransaction createFragmentTransactionStyleDefault();

    /**
     *
     * @return null if switch between fragments with no transaction animation
     */
    protected abstract FragmentTransaction createAnimFragmentTransactionWith(int tabPosition);

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    protected FragmentManager mFragmentManager = null;

    protected Fragment mCurrentFragment = null;

    protected Fragment mLastFragment = null;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            mFragmentManager = getFragmentManager();
            initDefaultFragment();
        }
    }

    protected void replaceFragmentWhenClickTabLayoutMainWith(int tabPosition) {

        Fragment fragment = selectFragmentWith(tabPosition);

        FragmentTransaction ft = createAnimFragmentTransactionWith(tabPosition);

        if (ft != null) {
            mLastFragment = mCurrentFragment;
            mCurrentFragment = fragment;

            if (fragment != null) {
                ft.replace(R.id.frame_container_MainActivity, fragment);
            }
            ft.commit();
        }

    }

    protected void initDefaultFragment() {

        int defaultPosition = getDefaultPosition();

        Fragment fragment = selectFragmentWith(defaultPosition);

        mCurrentFragment = fragment;

        FragmentTransaction ft = createFragmentTransactionStyleDefault();

        if (fragment != null) {
            ft.replace(R.id.frame_container_MainActivity, fragment);
        }

        ft.commit();
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
