package com.cse.hcmut.mobileappdev.view.features.start.forgotpassword.header;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dinhn on 4/30/2016.
 */
public class HeaderTopForgotPasswordSingleFragment extends MyBaseSingleFragment {

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

    public static HeaderTopForgotPasswordSingleFragment newInstance() {

        Bundle args = new Bundle();

        HeaderTopForgotPasswordSingleFragment fragment = new HeaderTopForgotPasswordSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @BindView(R.id.previousImageView_SingleFragmentHeaderTopForgotPassword)
    ImageView mPreviousImageView;

    @BindView(R.id.titleTextView_SingleFragmentHeaderTopForgotPassword)
    TextView mTitleTextView;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.single_fragment_header_top_forgot_password;
    }


    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @OnClick(R.id.previousFrameLayout_SingleFragmentHeaderTopForgotPassword)
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
