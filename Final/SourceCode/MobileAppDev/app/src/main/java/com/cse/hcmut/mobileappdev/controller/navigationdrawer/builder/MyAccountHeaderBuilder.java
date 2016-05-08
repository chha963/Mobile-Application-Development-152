package com.cse.hcmut.mobileappdev.controller.navigationdrawer.builder;

import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;

/**
 * Created by dinhn on 4/2/2016.
 */
public class MyAccountHeaderBuilder extends AccountHeaderBuilder {

    @Override
    public AccountHeader build() {
        AccountHeader customizeHeader = super.build();
        int curHeight = 275;
        setHeaderBackgroundHeight(curHeight);
        return customizeHeader;
    }

    public int getHeaderBackgroundHeight() {
        View accountHeader = mAccountHeaderContainer.findViewById(com.mikepenz.materialdrawer.R.id.material_drawer_account_header);
        int height = 0;
        if (accountHeader != null) {
            height = accountHeader.getLayoutParams().height;
        }
        return height;
    }

    public void setHeaderBackgroundHeight(int height) {
        if (mAccountHeaderContainer != null) {

            View accountHeader = mAccountHeaderContainer.findViewById(com.mikepenz.materialdrawer.R.id.material_drawer_account_header);
            if (accountHeader != null) {
                ViewGroup.LayoutParams params = accountHeader.getLayoutParams();
                params.height = height;
                accountHeader.setLayoutParams(params);
            }
        }
    }
}
