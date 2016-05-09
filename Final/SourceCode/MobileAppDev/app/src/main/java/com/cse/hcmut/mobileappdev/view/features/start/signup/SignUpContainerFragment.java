package com.cse.hcmut.mobileappdev.view.features.start.signup;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseTwoFragmentContainer;
import com.cse.hcmut.mobileappdev.view.features.start.signup.content.ContentSignUpSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.start.signup.header.HeaderTopSignUpSingleFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpContainerFragment extends MyBaseTwoFragmentContainer {

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

    public static SignUpContainerFragment newInstance() {

        Bundle args = new Bundle();

        SignUpContainerFragment fragment = new SignUpContainerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public SignUpContainerFragment() {
        // Required empty public constructor
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected Fragment getHeaderTopFragment() {
        return HeaderTopSignUpSingleFragment.newInstance();
    }

    @Override
    protected Fragment getContentBotFragment() {
        return ContentSignUpSingleFragment.newInstance();
    }


    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        if (rootView != null) {
        }
        return rootView;
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
