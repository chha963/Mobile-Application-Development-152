package com.cse.hcmut.mobileappdev.view.features.home.Main.ProductList.gridlayout.content;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;
import com.cse.hcmut.mobileappdev.base.widgets.itemdecoration.GridSpacingItemDecoration;
import com.cse.hcmut.mobileappdev.models.Product.Product;
import com.cse.hcmut.mobileappdev.models.Product.ProductLab;
import com.cse.hcmut.mobileappdev.models.adapters.ProductCardListAdapter;
import com.cse.hcmut.mobileappdev.utils.ResourceUtils;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by dinhn on 4/9/2016.
 */
public class ProductListGridLayoutSingleFragment extends MyBaseSingleFragment
{

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    public static final String ARG_COLOR_KEY = "ARG_COLOR_PRODUCT_LIST_GRID_LAYOUT";

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static ProductListGridLayoutSingleFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt(ARG_COLOR_KEY, type);

        ProductListGridLayoutSingleFragment fragment = new ProductListGridLayoutSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    ArrayList<Product> mProductList;

    @Bind(R.id.recyclerViewVertical_ProductListGridLayoutSingleFragment)
    RecyclerView mVerticalRecyclerView;

    ProductCardListAdapter mProductCardListAdapter;

    int mCurrentColor;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.single_fragment_grid_layout_product_list;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        Context context = getActivity();
        mCurrentColor = getArguments().getInt(ARG_COLOR_KEY);

        if (rootView != null) {
            setupWidgets(context);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupWidgets(Context context) {
        setupAdapter(context);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                context,
                3,
                GridLayoutManager.VERTICAL,
                false);

        int spanCount = 3;
        int spacing = 16;
        boolean isEdge = true;

        mVerticalRecyclerView.setLayoutManager(gridLayoutManager);
        mVerticalRecyclerView.setAdapter(mProductCardListAdapter);
        mVerticalRecyclerView.setFocusable(false);
        mVerticalRecyclerView.addItemDecoration(
                new GridSpacingItemDecoration(spanCount, spacing, isEdge)
        );

    }

    private void setupAdapter(Context context) {

        int typeProduct = getTypeByColor(mCurrentColor);
        mProductList = ProductLab.get(context).getProductListFilteredByType(typeProduct);

        mProductCardListAdapter = new ProductCardListAdapter(context, mProductList, mCurrentColor);
    }

    private int getTypeByColor(int color) {
        int position = Product.INDEX_BUY_PRODUCT;

        Activity activity = getActivity();

        if (activity != null) {

            if (color == ResourceUtils.getDefaultColorTabLayout(activity)) {
                position = Product.INDEX_BUY_PRODUCT;
            } else if (color == ResourceUtils.getAnotherColorTabLayout(activity)) {
                position = Product.INDEX_SELL_PRODUCT;
            }
        }

        return position;
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------
}
