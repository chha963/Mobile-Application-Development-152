package com.cse.hcmut.mobileappdev.view.features.home.productdetail.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.adapters.ImageGalleryPagerAdapter;
import com.cse.hcmut.mobileappdev.base.views.activity.MyActivity;

import java.util.ArrayList;

import butterknife.BindView;

public class GalleryViewPagerActivity extends MyActivity {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------
    private static final String EXTRA_GALLERY_IMAGE_URLS =
            "com.cse.hcmut.mobileappdev.view.features.home.ProductDetail.activity.gallery_url_list";

    private static final String EXTRA_SELECTED_INDEX =
            "com.cse.hcmut.mobileappdev.view.features.home.ProductDetail.activity.selected_index";

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static Intent newIntent(Context packageContext, ArrayList<String> urlList, int selectedIndex) {
        Intent intent = new Intent(packageContext, GalleryViewPagerActivity.class);
        intent.putExtra(EXTRA_GALLERY_IMAGE_URLS, urlList);
        intent.putExtra(EXTRA_SELECTED_INDEX, selectedIndex);
        return intent;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @BindView(R.id.viewPager_GalleryViewPagerActivity)
    ViewPager mViewPager;

    FragmentStatePagerAdapter mFragmentStatePagerAdapter = null;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gallery_view_pager;
    }

    @Override
    protected boolean isFullScreen() {
        return true;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setToolbarVisibility(View.GONE);
        setupWidgets();
    }

    private void setupWidgets() {
        setupViewPager();
    }

    private void setupViewPager() {
        Intent currentIntent = getIntent();
        if (currentIntent != null) {
            ArrayList<String> urlList = currentIntent.getStringArrayListExtra(EXTRA_GALLERY_IMAGE_URLS);
            int selectedIndex = currentIntent.getIntExtra(EXTRA_SELECTED_INDEX, 0);

            mFragmentStatePagerAdapter = new ImageGalleryPagerAdapter(
                    this,
                    getSupportFragmentManager(),
                    urlList
            );

            mViewPager.setAdapter(mFragmentStatePagerAdapter);
            mViewPager.setCurrentItem(selectedIndex);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------
}
