package com.cse.hcmut.mobileappdev.view.features.home.productdetail.header;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;
import com.cse.hcmut.mobileappdev.models.product.Product;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dinhn on 4/11/2016.
 */
public class HeaderTopProductDetailSingleFragment extends MyBaseSingleFragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    private static final String ARG_PRODUCT = "product";

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static HeaderTopProductDetailSingleFragment newInstance(Product product) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_PRODUCT, product);

        HeaderTopProductDetailSingleFragment fragment = new HeaderTopProductDetailSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @BindView(R.id.imageViewCatalogueTop_HeaderTopProductDetailSingleFragment)
    ImageView mCatalogueTopImageView;

    @BindView(R.id.imageViewBackTop_HeaderTopProductDetailSingleFragment)
    ImageView mNavigationBackImageView;

    @BindView(R.id.frameLayoutBackTop_HeaderTopProductDetailSingleFragment)
    FrameLayout mFrameLayoutBackTop;

    @OnClick(R.id.frameLayoutBackTop_HeaderTopProductDetailSingleFragment)
    void onClickBackNavigation() {
        Activity activity = getActivity();
        if (activity != null) {
            activity.onBackPressed();
        }
    }

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.single_fragment_header_top_product_detail;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            Product product = arguments.getParcelable(ARG_PRODUCT);
            setupWidget(product);
        }

    }

    private void setupWidget(Product product) {

        Glide.with(this)
                .load(product.getCatalogueId())
                .centerCrop()
                .placeholder(R.drawable.ic_place_holder)
                .into(mCatalogueTopImageView);

    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------
}
