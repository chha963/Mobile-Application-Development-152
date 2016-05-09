package com.cse.hcmut.mobileappdev.view.features.home.editpersonalinfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseTwoFragmentContainer;
import com.cse.hcmut.mobileappdev.view.features.home.editpersonalinfo.content.ContentBotEditPersonalInfoSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.home.editpersonalinfo.header.HeaderTopEditPersonalInfoSingleFragment;

/**
 * Created by dinhn on 5/2/2016.
 */
public class EditPersonalInfoContainerFragment extends MyBaseTwoFragmentContainer {

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

    public static EditPersonalInfoContainerFragment newInstance() {

        Bundle args = new Bundle();

        EditPersonalInfoContainerFragment fragment = new EditPersonalInfoContainerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public EditPersonalInfoContainerFragment() {

    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected Fragment getHeaderTopFragment() {
        return HeaderTopEditPersonalInfoSingleFragment.newInstance();
    }

    @Override
    protected Fragment getContentBotFragment() {
        return ContentBotEditPersonalInfoSingleFragment.newInstance();
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        if (rootView != null) {
        }
        return rootView;
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
