package com.cse.hcmut.mobileappdev.controller.navigationdrawer.builder;

import android.util.TypedValue;
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
        int curHeight =
                (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        137.5f,
                        mAccountHeader.getContext().getResources().getDisplayMetrics()
                );
        setHeaderBackgroundHeight(curHeight);
        return customizeHeader;
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

    //    public int getHeaderBackgroundHeight() {
//        View accountHeader = mAccountHeaderContainer.findViewById(com.mikepenz.materialdrawer.R.id.material_drawer_account_header);
//        int height = 0;
//        if (accountHeader != null) {
//            height = accountHeader.getLayoutParams().height;
//        }
//        return height;
//    }
}
