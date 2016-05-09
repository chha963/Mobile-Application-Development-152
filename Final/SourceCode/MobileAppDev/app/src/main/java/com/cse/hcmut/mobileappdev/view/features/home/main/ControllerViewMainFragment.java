package com.cse.hcmut.mobileappdev.view.features.home.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseTwoTabControlFragmentContainer;
import com.cse.hcmut.mobileappdev.controller.MainActivity.MainActivitySender;
import com.cse.hcmut.mobileappdev.controller.navigationdrawer.drawer.DrawerManager;
import com.cse.hcmut.mobileappdev.view.features.home.main.productlist.gridlayout.ProductListContainerSectionClickedFragment;
import com.cse.hcmut.mobileappdev.view.features.home.main.productlist.horizontalscroll.ProductListContainerMainFragment;
import com.cse.hcmut.mobileappdev.view.features.home.map.MapMainSingleFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ControllerViewMainFragment extends MyBaseTwoTabControlFragmentContainer
        implements MainActivitySender.OnSendToMainFragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    public static final int INDEX_PRODUCT_LIST_FRAGMENT = 0;

    public static final int INDEX_MAP_FRAGMENT = 1;

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static Fragment newInstance() {

        Bundle args = new Bundle();

        ControllerViewMainFragment fragment = new ControllerViewMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public ControllerViewMainFragment() {
        // Required empty public constructor
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.content_main;
    }

    @Override
    protected int getDefaultPosition() {
        return INDEX_PRODUCT_LIST_FRAGMENT;
    }

    @Override
    protected Fragment selectFragmentWith(int tabPosition) {
        Fragment fragment = null;

        switch (tabPosition) {

            case INDEX_PRODUCT_LIST_FRAGMENT:
                fragment = ProductListContainerMainFragment.newInstance();
                break;

            case INDEX_MAP_FRAGMENT:
                fragment = new MapMainSingleFragment();
                break;

            default:
                break;
        }

        return fragment;
    }

    @Override
    protected FragmentTransaction createFragmentTransactionStyleDefault() {
        FragmentTransaction ft = mFragmentManager.beginTransaction();

        if (ft != null) {
            ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        }

        return ft;
    }

    @Override
    protected FragmentTransaction createAnimFragmentTransactionWith(int tabPosition) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();

        if (ft != null) {
            switch (tabPosition) {
                case 0:
                    ft.setCustomAnimations(
                            R.anim.slide_in_right,
                            R.anim.slide_out_left,
                            R.anim.slide_in_left,
                            R.anim.slide_out_right
                    );
                    break;
                case 1:
                    ft.setCustomAnimations(
                            R.anim.slide_in_left,
                            R.anim.slide_out_right,
                            R.anim.slide_in_right,
                            R.anim.slide_out_left
                    );
                    break;
            }
        }

        return ft;
    }

    @Override
    public void onSelectedTabProductListMainFragment(int position) {
        replaceFragmentWhenClickTabLayoutMainWith(position);
    }

    @Override
    public void onSelectedSectionProductListMainFragment(int position) {
        replaceFragmentWhenClickSectionWith(position);
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

//            int sectionLength = ResourceUtils.getNumberOfMainSection(getActivity());
//            mHorizontalScrollStateProductList =
//                    new ArrayList<>(Collections.nCopies(sectionLength, 0));

    @Override
    protected void replaceFragmentWhenClickTabLayoutMainWith(int tabPosition) {
        super.replaceFragmentWhenClickTabLayoutMainWith(tabPosition);

        if (mCurrentFragment instanceof MapMainSingleFragment) {
            DrawerManager.get().enableDrawer(false);
        } else {
            DrawerManager.get().enableDrawer(true);
        }
    }

    private void replaceFragmentWhenClickSectionWith(int sectionIndex) {

        ProductListContainerSectionClickedFragment fragment
                = ProductListContainerSectionClickedFragment.newInstance(sectionIndex);

        FragmentTransaction ft = getFragmentManager().beginTransaction();

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


    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
