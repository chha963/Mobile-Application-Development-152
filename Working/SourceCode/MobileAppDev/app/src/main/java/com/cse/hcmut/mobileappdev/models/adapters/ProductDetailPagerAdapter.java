package com.cse.hcmut.mobileappdev.models.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cse.hcmut.mobileappdev.models.Product.Product;
import com.cse.hcmut.mobileappdev.view.features.home.Main.ProductDetail.ProductDetailContainerFragment;

import java.util.ArrayList;

/**
 * Created by dinhn on 4/11/2016.
 */
public class ProductDetailPagerAdapter extends FragmentStatePagerAdapter {
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

    Context mContext = null;

    ArrayList<Product> mProductArrayList;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public ProductDetailPagerAdapter(FragmentManager fm, Context context, ArrayList<Product> productArrayList) {
        super(fm);
        mContext = context;
        mProductArrayList = productArrayList;
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    public Fragment getItem(int position) {
        Product product = mProductArrayList.get(position);
        return ProductDetailContainerFragment.newInstance(mContext, product.getId());
    }

    @Override
    public int getCount() {
        return mProductArrayList.size();
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------
}
