package com.cse.hcmut.mobileappdev.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.view.features.home.productdetail.avatar.ProductDetailAvatarImageSingleFragment;

import java.util.ArrayList;

/**
 * Created by dinhn on 4/12/2016.
 */
public class ImageGalleryPagerAdapter extends FragmentStatePagerAdapter {

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

    private SparseArray<Fragment> mRegisteredFragments;

    private ArrayList<String> mGalleryImageUrlArrayList;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public ImageGalleryPagerAdapter(Context context, FragmentManager fm, ArrayList<String> galleryImageUrlArrayList) {
        super(fm);
        mContext = context;
        mRegisteredFragments = new SparseArray<>();
        mGalleryImageUrlArrayList = galleryImageUrlArrayList;
    }


    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = getRegisteredFragment(position);
        if (fragment == null) {
            String url = mGalleryImageUrlArrayList.get(position);
            fragment = ProductDetailAvatarImageSingleFragment.newInstance(url);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return ((null != mGalleryImageUrlArrayList) ? mGalleryImageUrlArrayList.size() : 0);
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
