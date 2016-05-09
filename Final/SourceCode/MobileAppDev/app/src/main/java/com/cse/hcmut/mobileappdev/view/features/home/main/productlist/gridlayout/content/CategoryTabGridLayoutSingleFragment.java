package com.cse.hcmut.mobileappdev.view.features.home.main.productlist.gridlayout.content;

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
import com.cse.hcmut.mobileappdev.adapters.CategoryCapsuleListAdapter;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;
import com.cse.hcmut.mobileappdev.utils.ResourceUtils;

import butterknife.BindDimen;
import butterknife.BindView;

/**
 * Created by dinhn on 4/9/2016.
 */
public class CategoryTabGridLayoutSingleFragment extends MyBaseSingleFragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    public static final String ARG_COLOR_KEY = "ARG_COLOR_CATEGORY_TAB_GRID_LAYOUT_FRAGMENT";

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static CategoryTabGridLayoutSingleFragment newInstance(int color) {

        Bundle args = new Bundle();
        args.putInt(ARG_COLOR_KEY, color);

        CategoryTabGridLayoutSingleFragment fragment = new CategoryTabGridLayoutSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @BindView(R.id.recyclerViewHorizontal_CategoryTabSingleFragment)
    RecyclerView mHorizontalCategoryRecyclerView;

    CategoryCapsuleListAdapter mCategoryAdapter = null;

    int mCurrentColor;

    @BindDimen(R.dimen.category_first_item_margin_left)
    int mMarginLeftFirstItemCard;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public CategoryTabGridLayoutSingleFragment() {
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

        mCurrentColor = getArguments().getInt(ARG_COLOR_KEY);

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

        String[] titleArray = ResourceUtils.getCategoryTitleArray(context);
        mCategoryAdapter = new CategoryCapsuleListAdapter(context, titleArray, mCurrentColor);

        setupRecyclerView(context, mHorizontalCategoryRecyclerView);

        mHorizontalCategoryRecyclerView.setAdapter(mCategoryAdapter);
    }

    private void setupRecyclerView(Context context, RecyclerView recyclerView) {

        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        setupLayoutManagerRecyclerView(context, recyclerView);
        setupItemDecorationRecyclerView(recyclerView);

    }

    private void setupLayoutManagerRecyclerView(Context context, RecyclerView recyclerView) {
        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                )
        );
    }

    private void setupItemDecorationRecyclerView(RecyclerView recyclerView) {
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
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------
}
