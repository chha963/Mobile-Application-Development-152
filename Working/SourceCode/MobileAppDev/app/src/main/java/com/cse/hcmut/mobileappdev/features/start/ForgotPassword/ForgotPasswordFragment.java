package com.cse.hcmut.mobileappdev.features.start.ForgotPassword;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.MyFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPasswordFragment extends MyFragment {


    public ForgotPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        if (rootView != null) {

        }
        return rootView;
    }

}
