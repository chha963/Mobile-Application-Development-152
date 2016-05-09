package com.cse.hcmut.mobileappdev.view.features.home.main.productlist.gridlayout.content;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.adapters.NothingSelectedSpinnerAdapter;
import com.cse.hcmut.mobileappdev.adapters.ProductCardListAdapter;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;
import com.cse.hcmut.mobileappdev.base.widgets.itemdecoration.GridSpacingItemDecoration;
import com.cse.hcmut.mobileappdev.constants.MyConstantEnums;
import com.cse.hcmut.mobileappdev.models.product.Product;
import com.cse.hcmut.mobileappdev.models.product.ProductLab;
import com.cse.hcmut.mobileappdev.utils.ResourceUtils;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by dinhn on 4/9/2016.
 */
public class ProductListGridLayoutSingleFragment extends MyBaseSingleFragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    public static final String ARG_COLOR_KEY = "arg_color_key";

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static ProductListGridLayoutSingleFragment newInstance(int color) {

        Bundle args = new Bundle();
        args.putInt(ARG_COLOR_KEY, color);

        ProductListGridLayoutSingleFragment fragment = new ProductListGridLayoutSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    ArrayList<Product> mProductList;

    @BindView(R.id.recyclerViewVertical_ProductListGridLayoutSingleFragment)
    RecyclerView mVerticalRecyclerView;

    @BindView(R.id.sortSpinner_GridLayoutProductListSingleFragment)
    Spinner mSortSpinner;

    @BindString(R.string.spinner_sort_text_hint_section_main_fragment)
    String mSortSpinnerTextHint;

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

        if (rootView != null && context != null) {
            setupWidgets(context);
            setupSpinnerDropDownList(context);
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

    private void setupSpinnerDropDownList(Context context) {

        String[] itemsSort = MyConstantEnums.Sort.getArrayValues();

        if (context != null) {

            int layoutDropdownSpinner = R.layout.layout_spinner_dropdown_item_align_right;
            int layoutSpinnerItem = R.layout.layout_spinner_item;

            ArrayAdapter<String> sortArrayAdapter
                    = new ArrayAdapter<>(context, layoutDropdownSpinner, itemsSort);

            NothingSelectedSpinnerAdapter sortAdapter = new NothingSelectedSpinnerAdapter(
                    sortArrayAdapter,
                    layoutSpinnerItem,
                    context
            );
            sortAdapter.setTextHint(mSortSpinnerTextHint);
            sortAdapter.setTextHintGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            sortAdapter.setTextHintAlignment(View.TEXT_ALIGNMENT_VIEW_END);

            mSortSpinner.setAdapter(sortAdapter);
        }

    }

    private int getTypeByColor(int color) {
        int position = Product.INDEX_BUY_PRODUCT;

        Activity activity = getActivity();

        if (activity != null) {

            if (color == ResourceUtils.getDefaultGreenColorTabLayout(activity)) {
                position = Product.INDEX_BUY_PRODUCT;
            } else if (color == ResourceUtils.getAnotherBlueColorTabLayout(activity)) {
                position = Product.INDEX_SELL_PRODUCT;
            }
        }

        return position;
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------
}
