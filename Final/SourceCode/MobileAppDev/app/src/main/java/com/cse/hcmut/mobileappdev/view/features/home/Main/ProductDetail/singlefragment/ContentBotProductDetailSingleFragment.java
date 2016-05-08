package com.cse.hcmut.mobileappdev.view.features.home.Main.ProductDetail.singlefragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;
import com.cse.hcmut.mobileappdev.base.widgets.recyclerview.MySnappyRecyclerView;
import com.cse.hcmut.mobileappdev.models.Product.Product;
import com.cse.hcmut.mobileappdev.models.adapters.ImageGalleryAdapter;
import com.cse.hcmut.mobileappdev.utils.MyUtils;
import com.cse.hcmut.mobileappdev.utils.ResourceUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.BindDimen;

/**
 * Created by dinhn on 4/11/2016.
 */
public class ContentBotProductDetailSingleFragment extends MyBaseSingleFragment {

   // ---------------------------------------------------------------------------------------------
   // TYPES
   // ---------------------------------------------------------------------------------------------

   // ---------------------------------------------------------------------------------------------
   // ABSTRACT METHODS
   // ---------------------------------------------------------------------------------------------

   // ---------------------------------------------------------------------------------------------
   // STATIC FIELDS
   // ---------------------------------------------------------------------------------------------

    public static final String ARG_PRODUCT = "product_ContentBotProductDetailSingleFragment";

   // ---------------------------------------------------------------------------------------------
   // STATIC METHODS
   // ---------------------------------------------------------------------------------------------

    public static ContentBotProductDetailSingleFragment newInstance(Product product) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_PRODUCT, product);

        ContentBotProductDetailSingleFragment fragment = new ContentBotProductDetailSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

   // ---------------------------------------------------------------------------------------------
   // FIELDS
   // ---------------------------------------------------------------------------------------------

    @Bind(R.id.recyclerViewHorizontal_ContentBottomProductListDetailSingleFragment)
    MySnappyRecyclerView mHorizontalImageRecyclerView;

    @Bind(R.id.txtViewPhoneNumber_ContentBottomProductDetailSingleFragment)
    TextView mPhoneNumberTextView;

    @Bind(R.id.txtViewAddress_ContentBottomProductDetailSingleFragment)
    TextView mAddressTextView;

    @Bind(R.id.txtViewSeeInMap_ContentBottomProductDetailSingleFragment)
    TextView mSeeInMapTextView;

    @BindDimen(R.dimen.card_view_product_list_margin_right)
    int mMarginRightGalleryItem;

    ImageGalleryAdapter mImageGalleryAdapter = null;


   // ---------------------------------------------------------------------------------------------
   // CONSTRUCTORS
   // ---------------------------------------------------------------------------------------------

   // ---------------------------------------------------------------------------------------------
   // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
   // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.single_fragment_content_bottom_product_detail;
    }

    // ---------------------------------------------------------------------------------------------
   // METHODS
   // ---------------------------------------------------------------------------------------------

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        if (rootView != null) {

        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getActivity();
        Bundle arguments = getArguments();
        if (arguments != null) {
            Product product = arguments.getParcelable(ARG_PRODUCT);
            setupWidgets(context,product);
        }
    }

    private void setupWidgets(Context context, Product product) {

        ArrayList<String> iconIdLists = product.getGalleryIdList();
        mImageGalleryAdapter = new ImageGalleryAdapter(context, iconIdLists);

        setupRecyclerView(context, mHorizontalImageRecyclerView);
        mHorizontalImageRecyclerView.setAdapter(mImageGalleryAdapter);

        setupColorWidgetsDependsOn(context, product.getType());

        String formatPhoneNumber = MyUtils.addSeparatorEveryThreeCharacterFromBegin(product.getPhoneNumber()," ");
        mPhoneNumberTextView.setText(formatPhoneNumber);

        mAddressTextView.setText(product.getAddress());

    }

    private void setupColorWidgetsDependsOn(Context context,int productType) {

        int color = ResourceUtils.getColorForProductType(context, productType);
        mSeeInMapTextView.setTextColor(color);
    }

    private void setupRecyclerView(Context context, MySnappyRecyclerView recyclerView) {

        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setSnapEnabled(true);

        setupLayoutManagerRecyclerView(context, recyclerView);
        setupItemDecorationRecyclerView(recyclerView);

    }

    private void setupLayoutManagerRecyclerView(Context context, MySnappyRecyclerView recyclerView) {
        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                )
        );
    }

    private void setupItemDecorationRecyclerView(MySnappyRecyclerView recyclerView) {
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                final int itemPosition = parent.getChildAdapterPosition(view);

                if (itemPosition == RecyclerView.NO_POSITION) {
                    return;
                }

                outRect.set(0,0,mMarginRightGalleryItem,0);
            }
        });
    }

    // ---------------------------------------------------------------------------------------------
   // GETTERS / SETTERS
   // ---------------------------------------------------------------------------------------------

}
