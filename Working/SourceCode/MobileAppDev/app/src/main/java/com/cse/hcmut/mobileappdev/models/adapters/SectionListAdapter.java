package com.cse.hcmut.mobileappdev.models.adapters;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.models.Product.Product;
import com.cse.hcmut.mobileappdev.models.Section.SectionDataMainFragment;
import com.cse.hcmut.mobileappdev.base.widgets.recyclerview.MySnappyRecyclerView;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dinhn on 3/30/2016.
 */
public class SectionListAdapter extends RecyclerView.Adapter<SectionListAdapter.SectionHolder> {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    public interface OnClickSection {
        void onClickSection(int clickPosition);
    }

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    private Context mContext;
    private ArrayList<SectionDataMainFragment> mSectionDataList;

    private ArrayList<Integer> mHorizontalScrollStateList = null;
    private ArrayList<SectionHolder> mSectionHolderList = new ArrayList<>();

    private OnClickSection mOnClickSectionCallback;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public SectionListAdapter(Context context, ArrayList<SectionDataMainFragment> sectionDataList) {
        mContext = context;
        mSectionDataList = sectionDataList;
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    public SectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_horizontal_section_product_list, null);
        SectionHolder sectionHolder = new SectionHolder(mContext, v);
        mSectionHolderList.add(sectionHolder);
        return sectionHolder;
    }

    @Override
    public void onBindViewHolder(SectionHolder holder, int position) {

        String sectionName = mSectionDataList.get(position).getHeaderTitle();
        holder.setTitle(sectionName);

        ArrayList<Product> singleSectionItems = mSectionDataList.get(position).getSectionItemList();

        ProductCardListAdapter productCardListAdapter =
                new ProductCardListAdapter(mContext, singleSectionItems);

        MySnappyRecyclerView recyclerView = holder.mRecyclerView;

        recyclerView.setAdapter(productCardListAdapter);

        setupRecyclerView(recyclerView);

        restoreRecyclerViewFromLastOpened(recyclerView ,mHorizontalScrollStateList, position);

    }


    @Override
    public int getItemCount() {
        return ((null != mSectionDataList) ? mSectionDataList.size() : 0);
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    private void setupRecyclerView(MySnappyRecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        mContext,
                        LinearLayoutManager.HORIZONTAL,
                        false
                ));

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setSnapEnabled(true);

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
                    outRect.set(12, 0, 8, 0);
                }
                // last position
                else {
                    outRect.set(0, 0, 8, 0);
                }
            }
        });
    }

    private void restoreRecyclerViewFromLastOpened
            (MySnappyRecyclerView recyclerView, ArrayList<Integer> positionList, int position) {
        if (positionList != null) {
            recyclerView.scrollLayoutToPosition(positionList.get(position));
        }
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

    public void setOnClickSectionCallback(OnClickSection onClickSectionCallback) {
        mOnClickSectionCallback = onClickSectionCallback;
    }

    public void setHorizontalScrollStateList(ArrayList<Integer> horizontalScrollStateList) {
        mHorizontalScrollStateList = horizontalScrollStateList;
    }

    public ArrayList<Integer> getCurrentHorizontalScrollStateList() {
        int length = mSectionHolderList.size();
        ArrayList<Integer> scrollStateList = new ArrayList<>(Collections.nCopies(length, 0));
        for (int i = 0; i < length; i++) {
            RecyclerView.LayoutManager layoutManager =
                    mSectionHolderList.get(i).getRecyclerView().getLayoutManager();

            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager)layoutManager;
                int position = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                scrollStateList.set(i, position);
            }
        }
        return scrollStateList;
    }

    // ---------------------------------------------------------------------------------------------
    // INNER CLASSES
    // ---------------------------------------------------------------------------------------------

    protected class SectionHolder extends MySnappyRecyclerView.ViewHolder implements View.OnClickListener {

        private Context mContext = null;

        @Bind(R.id.txtViewTypeProduct_TypeProductList)
        TextView mTitleTextView = null;

        @Bind(R.id.txtViewMore_TypeProductList)
        TextView mMoreTextView = null;

        @Bind(R.id.recyclerViewHorizontal_TypeProductList)
        MySnappyRecyclerView mRecyclerView = null;

        public SectionHolder(Context context, View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            ButterKnife.bind(this, itemView);
            this.mContext = context;
        }

        @Override
        public void onClick(View v) {
            if (mOnClickSectionCallback != null) {
                mOnClickSectionCallback.onClickSection(getAdapterPosition());
            }
        }

        private void setTitle(String title) {
            if (mTitleTextView != null) {
                mTitleTextView.setText(title);
            }
        }

        private MySnappyRecyclerView getRecyclerView() {
            return mRecyclerView;
        }
    }
}
