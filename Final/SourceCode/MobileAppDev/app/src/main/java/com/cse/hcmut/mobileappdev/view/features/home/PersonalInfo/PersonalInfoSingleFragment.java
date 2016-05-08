package com.cse.hcmut.mobileappdev.view.features.home.PersonalInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalInfoSingleFragment extends MyBaseSingleFragment {

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

    public static PersonalInfoSingleFragment newInstance() {

        Bundle args = new Bundle();

        PersonalInfoSingleFragment fragment = new PersonalInfoSingleFragment();
        fragment.setArguments(args);

        return fragment;

    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public PersonalInfoSingleFragment() {
        // Required empty public constructor
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal_info;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
