package com.cse.hcmut.mobileappdev.view.features.home.personalinfo.content;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.adapters.ProductCardListPersonalInfoAdapter;
import com.cse.hcmut.mobileappdev.base.list.MyProductAccountArrayList;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;
import com.cse.hcmut.mobileappdev.models.product.ProductAccountLab;
import com.cse.hcmut.mobileappdev.utils.ResourceUtils;

import butterknife.BindDimen;
import butterknife.BindView;

/**
 * Created by dinhn on 4/29/2016.
 */
public class ContentPersonalInfoSingleFragment extends MyBaseSingleFragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    private static final String ARG_TAB_POSITION_KEY = "arg_tab_position";

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static ContentPersonalInfoSingleFragment newInstance(int tabPosition) {

        Bundle args = new Bundle();
        args.putInt(ARG_TAB_POSITION_KEY, tabPosition);

        ContentPersonalInfoSingleFragment fragment = new ContentPersonalInfoSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @BindView(R.id.recyclerViewVertical_ContentPersonalInfoSingleFragment)
    RecyclerView mVerticalRecyclerView;

    ProductCardListPersonalInfoAdapter mProductCardListAdapter;

    int mCurrentTabPosition;

    @BindDimen(R.dimen.personal_info_product_card_padding)
    int mProductCardPadding;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.single_fragment_content_personal_info;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        Context context = getActivity();
        if (context != null) {

            Bundle args = getArguments();

            if (args != null) {

                mCurrentTabPosition = args.getInt(ARG_TAB_POSITION_KEY);

                if (rootView != null) {
                    setupWidgets(context);
                }

            }
        }
        return rootView;
    }

    private void setupWidgets(Context context) {
        setupAdapters(context);

        LinearLayoutManager layoutRecyclerView
                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        mVerticalRecyclerView.setLayoutManager(layoutRecyclerView);
        mVerticalRecyclerView.setAdapter(mProductCardListAdapter);
        mVerticalRecyclerView.setFocusable(false);
        mVerticalRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                final int itemPosition = parent.getChildAdapterPosition(view);

                if (itemPosition == RecyclerView.NO_POSITION) {
                    return;
                }

                // first item
                if (itemPosition == 0) {
                    outRect.set(mProductCardPadding, 2 * mProductCardPadding, mProductCardPadding, mProductCardPadding);
                } else {
                    outRect.set(mProductCardPadding, mProductCardPadding, mProductCardPadding, mProductCardPadding);
                }
            }
        });
    }

    private void setupAdapters(Context context) {

        int color = ResourceUtils.getColorForPersonalInfoTabPosition(context, mCurrentTabPosition);

        ProductAccountLab productAccountLab = ProductAccountLab.get(context);
        switch (mCurrentTabPosition) {
            case 0:
                MyProductAccountArrayList buyProductList = productAccountLab.getBuyProductList();
                mProductCardListAdapter = new ProductCardListPersonalInfoAdapter(context, buyProductList, color);
                break;
            case 1:
                MyProductAccountArrayList sellProductList = productAccountLab.getSellProductList();
                mProductCardListAdapter = new ProductCardListPersonalInfoAdapter(context, sellProductList, color);
                break;
            case 2:
                break;
        }
    }


    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
