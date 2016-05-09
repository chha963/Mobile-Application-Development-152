package com.cse.hcmut.mobileappdev.view.features.start.forgotpassword.content;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;
import com.cse.hcmut.mobileappdev.utils.MyUtils;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dinhn on 4/30/2016.
 */
public class ContentBotForgotPasswordSingleFragment extends MyBaseSingleFragment {

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

    public static ContentBotForgotPasswordSingleFragment newInstance() {

        Bundle args = new Bundle();

        ContentBotForgotPasswordSingleFragment fragment = new ContentBotForgotPasswordSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @BindView(R.id.imageViewUsername_ContentBotForgotPasswordSingleFragment)
    ImageView mUsernameImageView;

    @BindView(R.id.imageViewEmail_ContentBotForgotPasswordSingleFragment)
    ImageView mEmailImageView;

    @BindView(R.id.btnSendPasswordToEmail_ContentBotForgotPasswordSingleFragment)
    Button mForgotPasswordButton;

    @BindColor(R.color.icon_sign_up)
    int iconColor;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.single_fragment_content_bot_forgot_password;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @OnClick(R.id.btnSendPasswordToEmail_ContentBotForgotPasswordSingleFragment)
    void sendPasswordToEmail() {
        MyUtils.showFeatureIsDevelopingDialog(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changeColorIcons();
    }

    private void changeColorIcons() {

        if (mUsernameImageView != null) {
            mUsernameImageView.setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP);
        }

        if (mEmailImageView != null) {
            mEmailImageView.setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP);
        }

    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
