package com.cse.hcmut.mobileappdev.features.home.Main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.constants.MyConstants;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class WidgetMainFragment extends Fragment {


    public WidgetMainFragment() {
        // Required empty public constructor
    }

    MultiStateToggleButton mMultiBtnFeature;

    MapMainFragment mMapMainFragment;
    ProductListMainFragment mProductListMainFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_widget_main, container, false);
        if (rootView != null) {
            // Default feature is Google Map
            mMultiBtnFeature = (MultiStateToggleButton) rootView.findViewById(R.id.multiBtnFeature_MainFragment);
            mMultiBtnFeature.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
                @Override
                public void onValueChanged(int value) {
                    displayView(value);
                }
            });

            // First run
            if (savedInstanceState == null) {
                if (mMapMainFragment == null) mMapMainFragment = new MapMainFragment();
                if (mProductListMainFragment == null)
                    mProductListMainFragment = new ProductListMainFragment();
                mMultiBtnFeature.setValue(MyConstants.INDEX_FEATURE_MAP_IN_MAIN_FRAGMENT);
                addView(mMapMainFragment);
                addView(mProductListMainFragment);
                displayView(MyConstants.INDEX_FEATURE_MAP_IN_MAIN_FRAGMENT);
            }
        }
        return rootView;
    }

    /*
     Add new fragment (Use when first run)
     */
    void addView(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (fragmentTransaction != null) {
                fragmentTransaction.add(R.id.featureLayout_MainFragment, fragment);
                fragmentTransaction.commit();
            }
        }
    }

    /*
     Show/Hide fragment depend on user selection
     */
    void displayView(int index) {
        Fragment fragmentShow = null;
        Fragment fragmentHide = null;
        switch (index) {
            case MyConstants.INDEX_FEATURE_MAP_IN_MAIN_FRAGMENT:
                fragmentShow = mMapMainFragment;
                fragmentHide = mProductListMainFragment;
                break;
            case MyConstants.INDEX_FEATURE_PRODUCT_LIST_IN_MAIN_FRAGMENT:
                fragmentShow = mProductListMainFragment;
                fragmentHide = mMapMainFragment;
                break;
            default:
                break;
        }
        if (fragmentShow != null && fragmentHide != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (fragmentTransaction != null) {
                fragmentTransaction.show(fragmentShow);
                fragmentTransaction.hide(fragmentHide);
                fragmentTransaction.commit();
            }
        }
    }

}
