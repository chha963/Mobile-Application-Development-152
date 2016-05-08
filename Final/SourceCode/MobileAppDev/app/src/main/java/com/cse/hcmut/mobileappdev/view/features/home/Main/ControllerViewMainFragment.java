package com.cse.hcmut.mobileappdev.view.features.home.Main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseTwoTabControlFragmentContainer;
import com.cse.hcmut.mobileappdev.controller.MainActivity.MainActivitySender;
import com.cse.hcmut.mobileappdev.controller.navigationdrawer.drawer.DrawerManager;
import com.cse.hcmut.mobileappdev.utils.ResourceUtils;
import com.cse.hcmut.mobileappdev.view.features.home.Main.Map.MapMainSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.home.Main.ProductList.gridlayout.ProductListContainerSectionClickedFragment;
import com.cse.hcmut.mobileappdev.view.features.home.Main.ProductList.horizontalscroll.ProductListContainerMainFragment;
import com.cse.hcmut.mobileappdev.view.features.home.Main.ProductList.horizontalscroll.content.ProductListMainSingleFragment;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class ControllerViewMainFragment extends MyBaseTwoTabControlFragmentContainer
                                        implements MainActivitySender.OnSenderFromMainFragment
{

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

    private ArrayList<Integer> mHorizontalScrollStateProductList = null;

    private int mHorizontalScrollOffsetCategoryTab = 0;

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

    @Override
    protected void initDefaultFragment() {
        super.initDefaultFragment();
        // Default is fragment 0
        if (mCurrentFragment instanceof ProductListContainerMainFragment) {
            ProductListContainerMainFragment productListMainFragment =(ProductListContainerMainFragment) mCurrentFragment;
            ProductListMainSingleFragment productListFragment = productListMainFragment.getProductListFragment();

            int sectionLength = ResourceUtils.getNumberOfMainSection(getActivity());
            mHorizontalScrollStateProductList =
                    new ArrayList<>(Collections.nCopies(sectionLength, 0));

            productListFragment.setHorizontalScrollStates(mHorizontalScrollStateProductList);
        }
    }

    @Override
    protected void replaceFragmentWhenClickTabLayoutMainWith(int tabPosition) {
        super.replaceFragmentWhenClickTabLayoutMainWith(tabPosition);

        if (mCurrentFragment instanceof MapMainSingleFragment) {
            DrawerManager.get().enableDrawer(false);
        } else {
            DrawerManager.get().enableDrawer(true);
        }

        backupCurrentAndRestoreLastFragment();
    }

    /**
     * Use 2 variables to keep track current & last fragment + static variables to store (if need)
     */
    private void backupCurrentAndRestoreLastFragment() {

        if (mCurrentFragment instanceof ProductListContainerMainFragment) {
            ProductListContainerMainFragment currentCastedFragment = (ProductListContainerMainFragment)mCurrentFragment;

            MapMainSingleFragment lastCastedFragment = (MapMainSingleFragment)mLastFragment;

            currentCastedFragment
                    .getProductListFragment()
                    .setHorizontalScrollStates(mHorizontalScrollStateProductList);

            currentCastedFragment
                    .getCategoryTabFragment()
                    .setCurrentHorizontalScrollOffset(mHorizontalScrollOffsetCategoryTab);

            lastCastedFragment.saveStateToLocalDatabase(getActivity());

        } else if (mCurrentFragment instanceof MapMainSingleFragment) {
            MapMainSingleFragment currentCastedFragment = (MapMainSingleFragment)mCurrentFragment;
            ProductListContainerMainFragment lastCastedFragment = (ProductListContainerMainFragment)mLastFragment;

            currentCastedFragment.restoreStateFromLocalDatabase(getActivity());

            mHorizontalScrollStateProductList = lastCastedFragment
                            .getProductListFragment()
                            .getHorizontalScrollState();

            mHorizontalScrollOffsetCategoryTab = lastCastedFragment
                            .getCategoryTabFragment()
                            .getCurrentHorizontalScrollOffset();
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
