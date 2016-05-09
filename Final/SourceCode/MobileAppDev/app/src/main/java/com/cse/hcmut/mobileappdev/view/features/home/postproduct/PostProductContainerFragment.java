package com.cse.hcmut.mobileappdev.view.features.home.postproduct;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseTwoFragmentContainer;
import com.cse.hcmut.mobileappdev.models.product.Product;
import com.cse.hcmut.mobileappdev.utils.ResourceUtils;
import com.cse.hcmut.mobileappdev.view.features.home.postproduct.content.ContentPostProductSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.home.postproduct.header.HeaderTopPostProductSingleFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostProductContainerFragment extends MyBaseTwoFragmentContainer {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    private static final String ARG_PRODUCT = "arg_product";

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static PostProductContainerFragment newInstance(Product product) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_PRODUCT, product);

        PostProductContainerFragment fragment = new PostProductContainerFragment();
        fragment.setArguments(args);

        return fragment;

    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    private Product mProduct = null;

    int mColor = 0;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public PostProductContainerFragment() {
        // Required empty public constructor
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected Fragment getHeaderTopFragment() {
        return HeaderTopPostProductSingleFragment.newInstance(mColor);
    }

    @Override
    protected Fragment getContentBotFragment() {
        return ContentPostProductSingleFragment.newInstance(mProduct);
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mProduct = args.getParcelable(ARG_PRODUCT);

            Activity activity = getActivity();
            if (activity != null) {
                mColor = ResourceUtils.getColorForProductType(activity, mProduct.getType());
            }
        }
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
