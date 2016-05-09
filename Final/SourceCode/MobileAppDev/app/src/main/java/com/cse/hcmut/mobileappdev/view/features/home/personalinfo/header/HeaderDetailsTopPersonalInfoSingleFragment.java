package com.cse.hcmut.mobileappdev.view.features.home.personalinfo.header;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.home.editpersonalinfo.EditPersonalInfoContainerFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dinhn on 4/28/2016.
 */
public class HeaderDetailsTopPersonalInfoSingleFragment extends MyBaseSingleFragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    public static final String ARG_COLOR_KEY = "arg_color";

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static HeaderDetailsTopPersonalInfoSingleFragment newInstance() {

        Bundle args = new Bundle();
//        args.putInt(ARG_COLOR_KEY, color);

        HeaderDetailsTopPersonalInfoSingleFragment fragment = new HeaderDetailsTopPersonalInfoSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @BindView(R.id.frameLayoutEdit_HeaderDetailsPersonalInfo)
    FrameLayout mEditFrameLayout;

    @BindView(R.id.txtViewPhoneNumber_HeaderDetailsPersonalInfo)
    TextView mPhoneNumberTextView;

    @BindView(R.id.txtViewSeeInMap_HeaderDetailsPersonalInfo)
    TextView mSeeInMapTextView;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.single_fragment_header_details_personal_info;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @OnClick(R.id.frameLayoutEdit_HeaderDetailsPersonalInfo)
    void goToEditPersonalInfoScreen() {
        EditPersonalInfoContainerFragment fragment =
                EditPersonalInfoContainerFragment.newInstance();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        if (ft != null) {

            ft.setCustomAnimations(
                    R.anim.slide_in_left,
                    R.anim.slide_out_right,
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
            );

            if (fragment != null) {
                ft.replace(R.id.frame_container_MainActivity, fragment);
                ft.addToBackStack(fragment.toString());
            }

            ft.commit();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Bundle args = getArguments();
//        if (args != null) {
//            int color = args.getInt(ARG_COLOR_KEY);
//            setWidgetsColor(color);
//        }
    }

    private void setWidgetsColor(int color) {

        if (mEditFrameLayout != null) {
            mEditFrameLayout.setBackgroundColor(color);
        }

        if (mPhoneNumberTextView != null) {
            mPhoneNumberTextView.setTextColor(color);
        }

        if (mSeeInMapTextView != null) {
            mSeeInMapTextView.setTextColor(color);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
