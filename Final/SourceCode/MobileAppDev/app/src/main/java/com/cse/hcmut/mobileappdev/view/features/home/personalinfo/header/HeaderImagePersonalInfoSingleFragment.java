package com.cse.hcmut.mobileappdev.view.features.home.personalinfo.header;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;
import com.cse.hcmut.mobileappdev.manager.LogInManager;

import butterknife.BindView;

/**
 * Created by dinhn on 4/28/2016.
 */
public class HeaderImagePersonalInfoSingleFragment extends MyBaseSingleFragment {

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

    public static HeaderImagePersonalInfoSingleFragment newInstance() {

        Bundle args = new Bundle();

        HeaderImagePersonalInfoSingleFragment fragment = new HeaderImagePersonalInfoSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @BindView(R.id.imageViewHeader_HeaderImagePersonalInfoSingleFragment)
    ImageView mHeaderImageView;

    @BindView(R.id.txtViewUsername_HeaderImagePersonalInfoSingleFragment)
    TextView mUsernameTextView;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.single_fragment_header_image_personal_info;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            String urlImageHeader = LogInManager.get(getActivity()).getAccount().getBannerImageId();
            Glide.with(this)
                    .load(urlImageHeader)
                    .centerCrop()
                    .placeholder(R.drawable.ic_place_holder)
                    .into(mHeaderImageView);

            String username = LogInManager.get(getActivity()).getAccount().getUsername();
            mUsernameTextView.setText(username);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
