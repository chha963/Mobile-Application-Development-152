package com.cse.hcmut.mobileappdev.view.features.home.Main.ProductList.gridlayout;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseTwoTabLayoutChangeColorFragment;
import com.cse.hcmut.mobileappdev.models.Product.Product;
import com.cse.hcmut.mobileappdev.utils.ResourceUtils;
import com.cse.hcmut.mobileappdev.view.features.home.Main.ProductList.gridlayout.content.ContentProductListGridLayoutFragment;
import com.cse.hcmut.mobileappdev.view.features.home.Main.ProductList.gridlayout.header.HeaderProductListGridLayoutFragment;
import com.cse.hcmut.mobileappdev.view.features.home.Search.ProductSearchViewStickyTopSingleFragment;

import butterknife.BindString;

/**
 * Created by dinhn on 4/9/2016.
 */
public class ProductListContainerSectionClickedFragment extends MyBaseTwoTabLayoutChangeColorFragment {
    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    public static final String EXTRA_TYPE_KEY =
            "com.cse.hcmut.mobileappdev.view.features.home.Main.ProductList.gridlayout.color_key";

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static ProductListContainerSectionClickedFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt(EXTRA_TYPE_KEY, position);

        ProductListContainerSectionClickedFragment fragment
                = new ProductListContainerSectionClickedFragment();

        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @BindString(R.string.first_tab_grid_layout_product_list_fragment)
    String mFirstTabLabel;

    @BindString(R.string.second_tab_grid_layout_product_list_fragment)
    String mSecondTabLabel;

    int mCurrentColor = 0;

    private int mLastTabPosition = 0;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected void onTabLayoutSelected(TabLayout.Tab tab) {

        int newPosition = tab.getPosition();

        if (mLastTabPosition != newPosition) {
            mLastTabPosition = newPosition;
            replaceFragmentAndColorWhenClickTab(newPosition);
            // Save current tab position
            saveTabPosition();
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
        return HeaderProductListGridLayoutFragment.newInstance(mCurrentColor);
    }

    @Override
    protected Fragment getContentBotFragment() {
        return ContentProductListGridLayoutFragment.newInstance(mCurrentColor);
    }

    @Override
    protected Fragment getSearchViewFragment() {
        return ProductSearchViewStickyTopSingleFragment.newInstance();
    }

    @Override
    protected String getFirstLabelTab() {
        return mFirstTabLabel;
    }

    @Override
    protected String getSecondLabelTab() {
        return mSecondTabLabel;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mLastTabPosition = bundle.getInt(EXTRA_TYPE_KEY);
            mCurrentColor = ResourceUtils.getColorForProductType(getActivity(), mLastTabPosition);
        }

        if (rootView != null) {

        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            setTabLayoutSelected(mLastTabPosition);
            replaceTabLayoutColorWhenClickTab(mLastTabPosition);
        }
    }

    private FragmentTransaction createFragmentTransaction(FragmentManager fragmentManager, int position) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (ft != null) {
            switch (position) {

                case Product.INDEX_BUY_PRODUCT:
                    ft.setCustomAnimations(
                            R.anim.slide_in_right_short,
                            R.anim.slide_out_left_short,
                            R.anim.slide_in_left_short,
                            R.anim.slide_out_right_short
                    );
                    break;

                case Product.INDEX_SELL_PRODUCT:
                    ft.setCustomAnimations(
                            R.anim.slide_in_left_short,
                            R.anim.slide_out_right_short,
                            R.anim.slide_in_right_short,
                            R.anim.slide_out_left_short
                    );
                    break;
            }
        }
        return ft;
    }

    private void replaceFragmentAndColorWhenClickTab(int position) {

        Activity activity = getActivity();
        if (activity != null) {
            int color = ResourceUtils.getColorForProductType(activity, position);

            Fragment headerFragment = HeaderProductListGridLayoutFragment.newInstance(color);
            Fragment contentFragment = ContentProductListGridLayoutFragment.newInstance(color);

            FragmentManager fragmentManager = getFragmentManager();

            FragmentTransaction normalFragmentTransaction = fragmentManager.beginTransaction();

            FragmentTransaction animateFragmentTransaction
                    = createFragmentTransaction(fragmentManager, position);

            if (animateFragmentTransaction != null) {

                if (contentFragment != null) {
                    animateFragmentTransaction.replace(CONTENT_BOT_LAYOUT_ID, contentFragment);
                }

                animateFragmentTransaction.commit();
            }

            if (normalFragmentTransaction != null) {

                if (headerFragment != null) {
                    normalFragmentTransaction.replace(HEADER_TOP_LAYOUT_ID, headerFragment);
                }

                normalFragmentTransaction.commit();
            }

            // Replace fragment immediate
            fragmentManager.executePendingTransactions();
            // Replace tab layout color
            replaceTabLayoutColorWhenClickTab(position);
        }
    }

    private void saveTabPosition() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putInt(EXTRA_TYPE_KEY, mLastTabPosition);
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------
}
