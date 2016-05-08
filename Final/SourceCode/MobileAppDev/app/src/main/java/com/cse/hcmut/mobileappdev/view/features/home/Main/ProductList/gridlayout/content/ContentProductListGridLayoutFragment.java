package com.cse.hcmut.mobileappdev.view.features.home.Main.ProductList.gridlayout.content;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseTwoFragmentContainer;

import butterknife.BindDimen;

/**
 * Created by dinhn on 4/9/2016.
 */
public class ContentProductListGridLayoutFragment extends MyBaseTwoFragmentContainer {
    
    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------
    
    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------
    
    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    public static final String ARG_COLOR_KEY = "ARG_COLOR_CONTENT_PRODUCT_LIST_GRID_LAYOUT";

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static ContentProductListGridLayoutFragment newInstance(int color) {

        Bundle args = new Bundle();
        args.putInt(ARG_COLOR_KEY, color);
        
        ContentProductListGridLayoutFragment fragment = new ContentProductListGridLayoutFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    int mCurrentColor;

    @BindDimen(R.dimen.category_layout_height)
    int mCategoryLayoutHeight;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------
    
    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected Fragment getHeaderTopFragment() {
        return CategoryTabGridLayoutSingleFragment.newInstance(mCurrentColor);
    }

    @Override
    protected Fragment getContentBotFragment() {
        return ProductListGridLayoutSingleFragment.newInstance(mCurrentColor);
    }
    
    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        mCurrentColor = getArguments().getInt(ARG_COLOR_KEY);

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
}
