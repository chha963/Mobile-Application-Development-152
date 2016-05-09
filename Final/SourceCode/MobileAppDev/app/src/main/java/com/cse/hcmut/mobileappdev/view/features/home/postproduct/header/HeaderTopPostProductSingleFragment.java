package com.cse.hcmut.mobileappdev.view.features.home.postproduct.header;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;
import com.cse.hcmut.mobileappdev.utils.MyUtils;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dinhn on 5/3/2016.
 */
public class HeaderTopPostProductSingleFragment extends MyBaseSingleFragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    private static final String ARG_COLOR = "arg_color";

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static HeaderTopPostProductSingleFragment newInstance(int color) {

        Bundle args = new Bundle();
        args.putInt(ARG_COLOR, color);

        HeaderTopPostProductSingleFragment fragment = new HeaderTopPostProductSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @BindView(R.id.previousImageView_HeaderTopPostProductSingleFragment)
    ImageView mPreviousImageView;

    @BindView(R.id.titleTextView_HeaderTopPostProductSingleFragment)
    TextView mTitleTextView;

    @BindView(R.id.btnPostProduct_HeaderTopPostProductSingleFragment)
    Button mPostProductButton;

    @BindColor(R.color.post_product_text_and_icon_header_top)
    int mColorPreviousIcon;

    int mColor;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.single_fragment_header_top_post_product;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @OnClick(R.id.previousFrameLayout_HeaderTopPostProductSingleFragment)
    void cancelPostProduct() {
        Activity activity = getActivity();
        if (activity != null) {
            activity.onBackPressed();
        }
    }

    @OnClick(R.id.btnPostProduct_HeaderTopPostProductSingleFragment)
    void postProduct() {
        MyUtils.showFeatureIsDevelopingDialog(getActivity());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mColor = args.getInt(ARG_COLOR);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changeColorWidgets();
    }

    private void changeColorWidgets() {
        if (mPreviousImageView != null) {
            mPreviousImageView.setColorFilter(mColorPreviousIcon, PorterDuff.Mode.SRC_ATOP);
        }

        if (mPostProductButton != null) {
            mPostProductButton.setTextColor(mColor);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------
}
