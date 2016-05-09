package com.cse.hcmut.mobileappdev.view.features.home.main.productlist.horizontalscroll.header;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;
import com.cse.hcmut.mobileappdev.models.product.Product;
import com.cse.hcmut.mobileappdev.models.product.TopProductLab;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopProductListMainSingleFragment extends MyBaseSingleFragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    private static final String ARG_TOP_PRODUCT_ID = "top_product_id";

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static Fragment newInstance(String productId) {
        Bundle args = new Bundle();
        args.putString(ARG_TOP_PRODUCT_ID, productId);

        Fragment fragment = new TopProductListMainSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @BindView(R.id.imageView_TopProductListSingleFragment)
    ImageView mTopProductImageView = null;

    @BindView(R.id.typeAndNameProduct_TopProductListSingleFragment)
    TextView mTypeAndNameTextView = null;

    @BindView(R.id.shortDescriptionProduct_TopProductListSingleFragment)
    TextView mShortDescriptionTextView = null;

    @BindView(R.id.priceProduct_TopProductListSingleFragment)
    TextView mPriceTextView = null;

    private Product mTopProduct = null;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public TopProductListMainSingleFragment() {
        // Required empty public constructor
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.single_fragment_top_product_list;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        bindTopProductThrough(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        if (rootView != null) {
            setupWidgets();
        }
        return rootView;
    }

    private void setupWidgets() {

        if (mTopProduct != null) {

            Glide.with(this)
                    .load(mTopProduct.getIconId())
                    .centerCrop()
                    .placeholder(R.drawable.ic_place_holder)
                    .into(mTopProductImageView);

            mTypeAndNameTextView
                    .setText(toTypeAndNameProductStringThrough(
                            mTopProduct.getType(),
                            mTopProduct.getName()
                    ));

            mShortDescriptionTextView.setText(mTopProduct.getBriefDescription());

            String priceString = toPriceStringThrough(mTopProduct.getPrice());
            mPriceTextView.setText(priceString);
        }

    }

    private void bindTopProductThrough(Bundle args) {
        if (args != null) {
            String topProductId = args.getString(ARG_TOP_PRODUCT_ID);
            mTopProduct = TopProductLab.get(getActivity()).getProduct(topProductId);
        }
    }

    private String toTypeAndNameProductStringThrough(int type, String name) {
        String typeString = "[" + ((type == Product.INDEX_BUY_PRODUCT) ? "MUA" : "BÁN") + "]";
        return (typeString + name);
    }

    private String toPriceStringThrough(int price) {
        return String.valueOf(price) + " VND";
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
