package com.cse.hcmut.mobileappdev.view.features.home.Main.ProductList.horizontalscroll.content;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseTwoFragmentContainer;

import butterknife.BindDimen;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentProductListContainerMainFragment extends MyBaseTwoFragmentContainer {

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

    public static ContentProductListContainerMainFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ContentProductListContainerMainFragment fragment = new ContentProductListContainerMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @BindDimen(R.dimen.category_layout_height)
    int mCategoryLayoutHeight;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public ContentProductListContainerMainFragment() {
        // Required empty public constructor
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected Fragment getHeaderTopFragment() {
        return CategoryTabMainSingleFragment.newInstance();
    }

    @Override
    protected Fragment getContentBotFragment() {
        return ProductListMainSingleFragment.newInstance();
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupWidgets();
    }

    private void setupWidgets() {
        // Change height capsule tab layout
        ViewGroup.LayoutParams params = mHeaderTopLayout.getLayoutParams();
        params.height = mCategoryLayoutHeight;
        mHeaderTopLayout.setLayoutParams(params);
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

    public ProductListMainSingleFragment getProductListFragment() {
        return (ProductListMainSingleFragment)getContentBotFragment();
    }

    public CategoryTabMainSingleFragment getCategoryTabFragment() {
        return (CategoryTabMainSingleFragment)getHeaderTopFragment();
    }

}
