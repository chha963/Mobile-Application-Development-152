package com.cse.hcmut.mobileappdev.view.features.home.Search.modelcontroller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cse.hcmut.mobileappdev.R;

import java.util.Collection;
import java.util.List;

/**
 * Created by dinhn on 3/26/2016.
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {

    private List<SearchResult> mItemList;

    public SearchResultAdapter(List<SearchResult> itemList) {
        this.mItemList = itemList;
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                               .inflate(R.layout.layout_item_search_result, parent, false);
        return new SearchResultViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        SearchResult result = mItemList.get(position);
        holder.mTitleTextView.setText(result.getTitle());
        holder.mDescriptionTextView.setText(result.getDescription());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    protected class SearchResultViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitleTextView;
        private TextView mDescriptionTextView;
        private ImageView mIconImageView;
        public SearchResultViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView)itemView.findViewById(R.id.textViewTitle_ItemSearchResult);
            mDescriptionTextView = (TextView)itemView.findViewById(R.id.textViewDescription_ItemSearchResult);
            mIconImageView = (ImageView)itemView.findViewById(R.id.imageViewIcon_ItemSearchResult);
        }
    }

    public void addAll(Collection<SearchResult> items) {
        if (mItemList != null) {
            int currentItemCount = mItemList.size();
            mItemList.addAll(items);
            notifyItemRangeInserted(currentItemCount, items.size());
        }
    }

    public void addAll(int position, Collection<SearchResult> items) {
        if (mItemList != null) {
            int currentItemCount = mItemList.size();
            if (position > currentItemCount) {
                throw new IndexOutOfBoundsException();
            } else {
                mItemList.addAll(position, items);
            }
        }
    }

    public void replaceWith(Collection<SearchResult> items) {
        replaceWith(items, false);
    }

    public void clear() {
        int itemCount = mItemList.size();
        mItemList.clear();
        notifyItemRangeRemoved(0, itemCount);
    }

    public void replaceWith(Collection<SearchResult> items, boolean isCleanAllAndReplace) {
        if (isCleanAllAndReplace) {
            clear();
            addAll(items);
        } else {
            int oldCount = mItemList.size();
            int newCount = items.size();
            int changeCount = oldCount - newCount;

            mItemList.clear();
            mItemList.addAll(items);

            if (changeCount > 0) {
                notifyItemRangeChanged(0, newCount);
                notifyItemRangeRemoved(newCount, changeCount);
            } else if (changeCount < 0) {
                notifyItemRangeChanged(0, oldCount);
                notifyItemRangeInserted(oldCount, Math.abs(changeCount));
            } else {
                notifyItemRangeChanged(0, newCount);
            }
        }
    }
}
