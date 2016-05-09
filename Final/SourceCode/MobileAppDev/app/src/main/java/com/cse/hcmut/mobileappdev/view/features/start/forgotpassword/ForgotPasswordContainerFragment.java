package com.cse.hcmut.mobileappdev.view.features.start.forgotpassword;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseTwoFragmentContainer;
import com.cse.hcmut.mobileappdev.view.features.start.forgotpassword.content.ContentBotForgotPasswordSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.start.forgotpassword.header.HeaderTopForgotPasswordSingleFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPasswordContainerFragment extends MyBaseTwoFragmentContainer {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static ForgotPasswordContainerFragment newInstance() {

        Bundle args = new Bundle();

        ForgotPasswordContainerFragment fragment = new ForgotPasswordContainerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public ForgotPasswordContainerFragment() {
        // Required empty public constructor
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected Fragment getHeaderTopFragment() {
        return HeaderTopForgotPasswordSingleFragment.newInstance();
    }

    @Override
    protected Fragment getContentBotFragment() {
        return ContentBotForgotPasswordSingleFragment.newInstance();
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

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
