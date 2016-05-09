package com.cse.hcmut.mobileappdev.controller.MainActivity;

/**
 * Created by dinhn on 5/6/2016.
 */
public class MainActivitySender {

    public interface OnSendToMainFragment {
        void onSelectedTabProductListMainFragment(int tabPosition);

        void onSelectedSectionProductListMainFragment(int position);
    }
}
