package com.cse.hcmut.mobileappdev.view.features.home.main.productlist.horizontalscroll;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseTwoTabLayoutFragment;
import com.cse.hcmut.mobileappdev.controller.MainActivity.MainActivityReceiver;
import com.cse.hcmut.mobileappdev.view.features.home.main.productlist.horizontalscroll.content.ContentProductListContainerMainFragment;
import com.cse.hcmut.mobileappdev.view.features.home.main.productlist.horizontalscroll.header.HeaderProductListContainerMainFragment;
import com.cse.hcmut.mobileappdev.view.features.home.search.ProductSearchViewSingleFragment;

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

    protected MainActivityReceiver.OnReceiveFromProductListMainSingleFragment mClickListener;

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
    public static ProductListContainerMainFragment newInstance() {

        Bundle args = new Bundle();

        ProductListContainerMainFragment fragment = new ProductListContainerMainFragment();
        fragment.setArguments(args);
        return fragment;
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
                    (MainActivityReceiver.OnReceiveFromProductListMainSingleFragment) context;
        } catch (ClassCastException e) {
//            Log.d(
//                    "ProductListFragment",
//                    context.toString() + " must implement on ProductListContainerMainFragment");
        }
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
