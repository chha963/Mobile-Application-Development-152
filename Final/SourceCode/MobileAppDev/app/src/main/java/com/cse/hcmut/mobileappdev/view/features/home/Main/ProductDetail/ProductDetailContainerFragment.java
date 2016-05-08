package com.cse.hcmut.mobileappdev.view.features.home.Main.ProductDetail;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseThreeScrollFragmentContainer;
import com.cse.hcmut.mobileappdev.models.Product.Product;
import com.cse.hcmut.mobileappdev.models.Product.ProductLab;
import com.cse.hcmut.mobileappdev.view.features.home.Main.ProductDetail.singlefragment.ContentBotProductDetailSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.home.Main.ProductDetail.singlefragment.ContentMiddleProductDetailSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.home.Main.ProductDetail.singlefragment.HeaderTopProductDetailSingleFragment;

/**
 * Created by dinhn on 4/11/2016.
 */
public class ProductDetailContainerFragment extends MyBaseThreeScrollFragmentContainer {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    private static final String ARG_PRODUCT_ID = "productId_ProductDetailFragment";

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static ProductDetailContainerFragment newInstance(Context context, String productId) {

        Bundle args = new Bundle();
        args.putString(ARG_PRODUCT_ID, productId);

        ProductDetailContainerFragment fragment = new ProductDetailContainerFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    private Product mProduct = null;

    private Context mContext = null;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected Fragment getHeaderTopFragment() {
        return HeaderTopProductDetailSingleFragment.newInstance(mProduct);
    }

    @Override
    protected Fragment getContentMiddleFragment() {
        return ContentMiddleProductDetailSingleFragment.newInstance(mProduct);
    }

    @Override
    protected Fragment getContentBotFragment() {
        return ContentBotProductDetailSingleFragment.newInstance(mProduct);
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            String productId = args.getString(ARG_PRODUCT_ID);
            mProduct = ProductLab.get(mContext).getProduct(productId);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

    private void setContext(Context context) {
        mContext = context;
    }
}
