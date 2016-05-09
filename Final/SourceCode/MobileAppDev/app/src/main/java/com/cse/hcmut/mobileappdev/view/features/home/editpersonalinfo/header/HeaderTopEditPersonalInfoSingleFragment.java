package com.cse.hcmut.mobileappdev.view.features.home.editpersonalinfo.header;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dinhn on 5/2/2016.
 */
public class HeaderTopEditPersonalInfoSingleFragment extends MyBaseSingleFragment {

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

    public static HeaderTopEditPersonalInfoSingleFragment newInstance() {

        Bundle args = new Bundle();

        HeaderTopEditPersonalInfoSingleFragment fragment = new HeaderTopEditPersonalInfoSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @BindView(R.id.previousImageView_SingleFragmentHeaderTopEditPersonalInfo)
    ImageView mPreviousImageView;

    @BindView(R.id.titleTextView_SingleFragmentHeaderTopEditPersonalInfo)
    TextView mTitleTextView;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.single_fragment_header_top_edit_personal_info;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @OnClick(R.id.previousFrameLayout_SingleFragmentHeaderTopEditPersonalInfo)
    void backToSignInFragment() {
        Activity activity = getActivity();
        if (activity != null) {
            activity.onBackPressed();
        }
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
