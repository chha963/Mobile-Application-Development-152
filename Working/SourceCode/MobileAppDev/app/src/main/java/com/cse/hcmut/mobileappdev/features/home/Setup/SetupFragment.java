package com.cse.hcmut.mobileappdev.features.home.Setup;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetupFragment extends Fragment {


    public SetupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_setup, container, false);
        if (rootView != null) {

        }
        return rootView;
    }

}
