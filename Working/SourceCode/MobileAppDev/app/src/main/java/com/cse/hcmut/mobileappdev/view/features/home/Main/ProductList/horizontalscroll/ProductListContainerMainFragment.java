package com.cse.hcmut.mobileappdev.view.features.home.Main.ProductList.horizontalscroll;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.controller.MainActivity.MainActivityReceiver;
import com.cse.hcmut.mobileappdev.view.features.home.Main.ProductList.horizontalscroll.content.CategoryTabMainSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.home.Main.ProductList.horizontalscroll.content.ContentProductListContainerMainFragment;
import com.cse.hcmut.mobileappdev.view.features.home.Main.ProductList.horizontalscroll.content.ProductListMainSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.home.Main.ProductList.horizontalscroll.header.HeaderProductListContainerMainFragment;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseTwoTabLayoutFragment;
import com.cse.hcmut.mobileappdev.view.features.home.Search.ProductSearchViewSingleFragment;

import butterknife.BindString;

/**
 * Created by dinhn on 3/25/2016.
 */
public class ProductListContainerMainFragment extends MyBaseTwoTabLayoutFragment {

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

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    protected MainActivityReceiver.OnReceiverProductListMainSingleFragment mClickListener;

    private int mLastTabPosition = 0;

    @BindString(R.string.first_tab_main_product_list_fragment)
    String mTitleFirstTab;

    @BindString(R.string.second_tab_main_product_list_fragment)
    String mTitleSecondTab;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public ProductListContainerMainFragment() {
        // default required constructor
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected void onTabLayoutSelected(TabLayout.Tab tab) {
        int newPosition = tab.getPosition();
        if (mLastTabPosition != newPosition) {
            mClickListener.onReceiveProductListMainTabLayoutIndex(newPosition);
            mLastTabPosition = newPosition;
        }
    }

    @Override
    protected void onTabLayoutUnselected(TabLayout.Tab tab) {

    }

    @Override
    protected void onTabLayoutReselected(TabLayout.Tab tab) {

    }

    @Override
    protected Fragment getHeaderTopFragment() {
        return HeaderProductListContainerMainFragment.newInstance();
    }

    @Override
    protected Fragment getContentBotFragment() {
        return ContentProductListContainerMainFragment.newInstance();
    }

    @Override
    protected Fragment getSearchViewFragment() {
        return ProductSearchViewSingleFragment.newInstance();
    }

    @Override
    protected String getFirstLabelTab() {
        return mTitleFirstTab;
    }

    @Override
    protected String getSecondLabelTab() {
        return mTitleSecondTab;
    }

    // Override
    public static Fragment newInstance() {

        ProductListContainerMainFragment fullFragment = new ProductListContainerMainFragment();
        return fullFragment;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        if (rootView != null) {
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mClickListener =
                    (MainActivityReceiver.OnReceiverProductListMainSingleFragment) context;
        } catch (ClassCastException e) {
            Log.d(
                    "ProductListFragment",
                    context.toString() + " must implement on ProductListContainerMainFragment");
        }
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

    public ProductListMainSingleFragment getProductListFragment() {

        ProductListMainSingleFragment productListFragment =
                ((ContentProductListContainerMainFragment) getContentBotFragment()).getProductListFragment();

        return productListFragment;
    }

    public CategoryTabMainSingleFragment getCategoryTabFragment() {

        CategoryTabMainSingleFragment categoryTabFragment =
                ((ContentProductListContainerMainFragment) getContentBotFragment()).getCategoryTabFragment();

        return categoryTabFragment;
    }

}
