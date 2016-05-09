package com.cse.hcmut.mobileappdev.view.features.home.productdetail.content;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;
import com.cse.hcmut.mobileappdev.models.product.Product;
import com.cse.hcmut.mobileappdev.utils.MyUtils;
import com.cse.hcmut.mobileappdev.utils.ResourceUtils;
import com.cse.hcmut.mobileappdev.view.features.home.productdetail.activity.AvatarImageActivity;
import com.cse.hcmut.mobileappdev.view.features.home.productdetail.moredialog.MoreProductDetailDialogSingleFragment;

import butterknife.BindView;
import butterknife.OnClick;
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

    public static final String ARG_PRODUCT = "product";

    private static final String DIALOG_MORE_DESCRIPTION = "DialogMoreDescription";

    private static final int REQUEST_MORE_DESCRIPTION = 1;

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

    @BindView(R.id.imageViewAvatarLeft_ContentMiddleProductDetailSingleFragment)
    ImageView mAvatarLeftImageView;

    @BindView(R.id.txtViewProductName_ContentMiddleProductDetailSingleFragment)
    TextView mProductNameTextView;

    @BindView(R.id.txtViewAuthorName_ContentMiddleProductDetailSingleFragment)
    TextView mAuthorNameTextView;

    @BindView(R.id.txtViewDateLimit_ContentMiddleProductDetailSingleFragment)
    TextView mDateLimitTextView;

    @BindView(R.id.txtViewPrice_ContentMiddleProductDetailSingleFragment)
    TextView mPriceTextView;

    @BindView(R.id.txtViewBriefDescription_ContentMiddleProductDetailSingleFragment)
    TextView mBriefDescriptionTextView;

    @BindView(R.id.txtViewReadMore_ContentMiddleProductDetailSingleFragment)
    TextView mReadMoreTextView;

    @BindView(R.id.btnAddBookmark_ContentMiddleProductDetailSingleFragment)
    Button mAddBookmarkButton;

    @BindView(R.id.btnDial_ContentMiddleProductDetailSingleFragment)
    Button mDialButton;

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
            R.id.btnDial_ContentMiddleProductDetailSingleFragment,
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

    @OnClick(R.id.layoutBriefDescription_ContentMiddleProductDetailSingleFragment)
    void seeMoreDescrption() {
        Bundle args = getArguments();
        if (args != null) {
            Product product = args.getParcelable(ARG_PRODUCT);
            FragmentManager fragmentManager = getFragmentManager();
            MoreProductDetailDialogSingleFragment fragment = createMoreProductDetailDialog(product);
            fragment.setTargetFragment(ContentMiddleProductDetailSingleFragment.this, REQUEST_MORE_DESCRIPTION);
            fragment.show(fragmentManager, DIALOG_MORE_DESCRIPTION);
        }
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
                Intent intent = AvatarImageActivity.newIntent(getActivity(), product.getIconId());
                startActivity(intent);
            }
        });

        mDialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri callUri = Uri.parse("tel:" + product.getPhoneNumber());
                Intent intent = new Intent(Intent.ACTION_CALL, callUri);
                startActivity(intent);
            }
        });

    }

    private void setupColorWidgetsDependsOn(int productType) {
        Activity activity = getActivity();
        if (activity != null) {
            int color = ResourceUtils.getColorForProductType(getActivity(), productType);
            int buttonDrawable = ResourceUtils.getButtonDrawableIdWith(productType);
            mPriceTextView.setTextColor(color);
            mReadMoreTextView.setTextColor(color);
            mDialButton.setBackgroundResource(buttonDrawable);
        }
    }

    private MoreProductDetailDialogSingleFragment createMoreProductDetailDialog(Product product) {

        final MoreProductDetailDialogSingleFragment moreProductDetailDialogSingleFragment =
                MoreProductDetailDialogSingleFragment.newInstance(product);

        moreProductDetailDialogSingleFragment.setCallbacksListener(new MoreProductDetailDialogSingleFragment.CallbacksListener() {
            @Override
            public void onCloseButtonClicked() {

            }
        });

        return moreProductDetailDialogSingleFragment;
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------


}
