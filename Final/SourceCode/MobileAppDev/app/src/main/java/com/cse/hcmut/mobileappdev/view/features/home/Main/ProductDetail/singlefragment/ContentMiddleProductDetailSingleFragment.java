package com.cse.hcmut.mobileappdev.view.features.home.Main.ProductDetail.singlefragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;
import com.cse.hcmut.mobileappdev.models.Product.Product;
import com.cse.hcmut.mobileappdev.utils.DeviceUtils;
import com.cse.hcmut.mobileappdev.utils.MyUtils;
import com.cse.hcmut.mobileappdev.utils.ResourceUtils;
import com.cse.hcmut.mobileappdev.view.features.home.Main.ProductDetail.acitivity.AvatarImageActivity;

import java.io.File;
import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.OnTouch;

/**
 * Created by dinhn on 4/12/2016.
 */
public class ContentMiddleProductDetailSingleFragment extends MyBaseSingleFragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    public static final String ARG_PRODUCT = "product_ContentMiddleProductDetailSingleFragment";

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static ContentMiddleProductDetailSingleFragment newInstance(Product product) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_PRODUCT, product);

        ContentMiddleProductDetailSingleFragment fragment = new ContentMiddleProductDetailSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @Bind(R.id.imageViewAvatarLeft_ContentMiddleProductDetailSingleFragment)
    ImageView mAvatarLeftImageView;

    @Bind(R.id.txtViewProductName_ContentMiddleProductDetailSingleFragment)
    TextView mProductNameTextView;

    @Bind(R.id.txtViewAuthorName_ContentMiddleProductDetailSingleFragment)
    TextView mAuthorNameTextView;

    @Bind(R.id.txtViewDateLimit_ContentMiddleProductDetailSingleFragment)
    TextView mDateLimitTextView;

    @Bind(R.id.txtViewPrice_ContentMiddleProductDetailSingleFragment)
    TextView mPriceTextView;

    @Bind(R.id.txtViewBriefDescription_ContentMiddleProductDetailSingleFragment)
    TextView mBriefDescriptionTextView;

    @Bind(R.id.txtViewReadMore_ContentMiddleProductDetailSingleFragment)
    TextView mReadMoreTextView;

    @Bind(R.id.btnAddBookmark_ContentMiddleProductDetailSingleFragment)
    Button mAddBookmarkButton;

    @Bind(R.id.btnCall_ContentMiddleProductDetailSingleFragment)
    Button mCallButton;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.single_fragment_content_middle_product_detail;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @OnTouch({
            R.id.btnCall_ContentMiddleProductDetailSingleFragment,
            R.id.btnAddBookmark_ContentMiddleProductDetailSingleFragment,
            R.id.imageViewAvatarLeft_ContentMiddleProductDetailSingleFragment
    })
    boolean changeAlpha(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                view.setAlpha(0.6f);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                view.setAlpha(1f);
                break;
        }
        return false;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            Product product = arguments.getParcelable(ARG_PRODUCT);
            setupWidget(product);
        }

    }

    private void setupWidget(final Product product) {

        Glide.with(this)
                .load(product.getIconId())
                .centerCrop()
                .placeholder(R.drawable.ic_place_holder)
                .into(mAvatarLeftImageView);

        setupColorWidgetsDependsOn(product.getType());

        mProductNameTextView.setText(product.getName());

        mAuthorNameTextView.setText(product.getAuthor());

        mDateLimitTextView.setText(product.getDatePost());

        mPriceTextView.setText(MyUtils.addSeparatorEveryThreeCharacterFromLast(product.getPrice() + "", ".") + " VND");

        mBriefDescriptionTextView.setText(product.getBriefDescription());

        //loadImageCacheInBackground(product);

        mAvatarLeftImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AvatarImageActivity.newIntent(getActivity(),product.getIconId());
                startActivity(intent);
            }
        });

    }

    private void setupColorWidgetsDependsOn(int productType) {
        Activity activity = getActivity();
        if (activity != null) {
            int color = ResourceUtils.getColorForProductType(getActivity(), productType);
            int buttonDrawable = ResourceUtils.getButtonDrawableForProductType(getActivity(), productType);
            mPriceTextView.setTextColor(color);
            mReadMoreTextView.setTextColor(color);
            mCallButton.setBackgroundResource(buttonDrawable);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------


}
