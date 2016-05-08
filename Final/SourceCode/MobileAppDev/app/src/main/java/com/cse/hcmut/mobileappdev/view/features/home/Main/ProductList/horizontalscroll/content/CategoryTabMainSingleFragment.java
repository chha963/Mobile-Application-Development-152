package com.cse.hcmut.mobileappdev.view.features.home.Main.ProductList.horizontalscroll.content;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.models.adapters.CategoryCapsuleListAdapter;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;
import com.cse.hcmut.mobileappdev.utils.ResourceUtils;

import butterknife.Bind;
import butterknife.BindDimen;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryTabMainSingleFragment extends MyBaseSingleFragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static CategoryTabMainSingleFragment newInstance() {

        Bundle args = new Bundle();

        CategoryTabMainSingleFragment fragment = new CategoryTabMainSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    private int mOffsetX = 0;

    @Bind(R.id.recyclerViewHorizontal_CategoryTabSingleFragment)
    RecyclerView mHorizontalCategoryRecyclerView;

    @BindDimen(R.dimen.card_view_product_list_first_item_margin_left)
    int mMarginLeftFirstItemCard;

    CategoryCapsuleListAdapter mCategoryAdapter = null;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public CategoryTabMainSingleFragment() {
        // Required empty public constructor
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.single_fragment_tab_category;
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
        setupWidgets(context);
    }

    private void setupWidgets(Context context) {
        setupRecyclerView(context, mHorizontalCategoryRecyclerView);
        setupAdapterAddToRecyclerView(context, mCategoryAdapter, mHorizontalCategoryRecyclerView);
    }

    private void setupAdapterAddToRecyclerView(Context context, RecyclerView.Adapter adapter, RecyclerView recyclerView) {
        String[] titleArray = ResourceUtils.getCategoryTitleArray(context);
        adapter = new CategoryCapsuleListAdapter(context, titleArray);
        recyclerView.setAdapter(adapter);
    }

    private void setupRecyclerView(Context context, RecyclerView recyclerView) {

        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        setupLayoutManagerRecyclerView(context, recyclerView);
        setupListenersRecyclerView(context, recyclerView);

        restoreRecyclerViewStateFromLastOpened(context,recyclerView);
    }

    private void setupLayoutManagerRecyclerView(Context context, RecyclerView recyclerView) {
        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                ));
    }

    private void setupListenersRecyclerView(Context context, RecyclerView recyclerView) {
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                final int itemPosition = parent.getChildAdapterPosition(view);
                if (itemPosition == RecyclerView.NO_POSITION) {
                    return;
                }

                final int itemCount = state.getItemCount();

                // first position
                if (itemPosition == 0) {
                    outRect.set(mMarginLeftFirstItemCard, 0, 0, 0);
                }
                // last position
                else if (itemPosition > 0 && itemPosition == (itemCount - 1)) {

                }
                // position between first and last
                else {

                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mOffsetX += dx;
            }
        });
    }

    private void restoreRecyclerViewStateFromLastOpened(Context context,RecyclerView recyclerView) {
        if (mOffsetX != 0) {
            recyclerView.smoothScrollBy(mOffsetX, 0);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

    public int getCurrentHorizontalScrollOffset() {
        return mOffsetX;
    }

    public void setCurrentHorizontalScrollOffset(int offset) {
        mOffsetX = offset;
    }
}
