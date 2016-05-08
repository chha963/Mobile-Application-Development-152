package com.cse.hcmut.mobileappdev.models.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.view.features.home.Main.ProductList.horizontalscroll.header.TopProductListMainSingleFragment;
import com.cse.hcmut.mobileappdev.models.Product.Product;
import com.cse.hcmut.mobileappdev.models.Product.TopProductLab;

import java.util.ArrayList;

/**
 * Created by dinhn on 3/29/2016.
 */
public class HeaderProductListAdapter extends FragmentStatePagerAdapter {

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

    private Context mContext = null;

    private SparseArray<Fragment> mRegisteredFragments = null;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public HeaderProductListAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
        this.mRegisteredFragments = new SparseArray<>();
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    public int getCount() {
        return TopProductLab.get(mContext).getProductList().size();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = getRegisteredFragment(position);
        if (fragment == null) {
            ArrayList<Product> topProductList = TopProductLab.get(mContext).getProductList();
            Product product = topProductList.get(position);
            fragment = TopProductListMainSingleFragment.newInstance(product.getId());
        }
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mRegisteredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mRegisteredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

    public Fragment getRegisteredFragment(int position) {
        return mRegisteredFragments.get(position);
    }
}
