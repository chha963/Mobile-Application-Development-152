package com.cse.hcmut.mobileappdev.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.list.MyGalleryPostProductBitmapList;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dinhn on 5/7/2016.
 */
public class ProductGalleryListPostProductAdapter extends RecyclerView.Adapter {

    private static final int TYPE_ROUND_IMAGE_HORIZONTAL_PLACE_HOLDER = 1;
    private static final int TYPE_ROUND_IMAGE_HORIZONTAL = 2;

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    public interface OnItemClickListener {
        void onGalleryItemImageClick(View view, int position);

        void onGalleryItemEmptyPlaceHolderClick(View view, int position);
    }

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    public static final int NUMBER_OF_IMAGE_LIMIT = 5;

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    private Context mContext;

    private MyGalleryPostProductBitmapList mBitmapsList;

    OnItemClickListener mItemClickListener;

    int mNumberOfItemsNotNull = 0;
    int mItemCount = 0;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public ProductGalleryListPostProductAdapter(Context context, MyGalleryPostProductBitmapList bitmapsList) {
        mContext = context;
        mBitmapsList = bitmapsList;
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder holder = null;
        View view = null;
        switch (viewType) {
            case TYPE_ROUND_IMAGE_HORIZONTAL:
                view = layoutInflater.inflate(R.layout.layout_gallery_item_post_product, null);
                holder = new GalleryItemPostProductHolder(mContext, view);
                break;
            case TYPE_ROUND_IMAGE_HORIZONTAL_PLACE_HOLDER:
                view = layoutInflater.inflate(R.layout.layout_gallery_item_post_product_empty_place_holder, null);
                holder = new GalleryItemPostProductEmptyHolder(mContext, view);
                break;
            default:
                view = layoutInflater.inflate(R.layout.layout_gallery_item_post_product_empty_place_holder, null);
                holder = new GalleryItemPostProductEmptyHolder(mContext, view);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_ROUND_IMAGE_HORIZONTAL:
                if (holder instanceof GalleryItemPostProductHolder) {
                    GalleryItemPostProductHolder productHolder = (GalleryItemPostProductHolder) holder;
                    productHolder.bindHolderFrom(mBitmapsList.get(position));
                }
                break;
            case TYPE_ROUND_IMAGE_HORIZONTAL_PLACE_HOLDER:
                if (holder instanceof GalleryItemPostProductEmptyHolder) {
                    // bind empty place holder here
                }
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {

        int realSize = 0;

        if (mBitmapsList != null) {
            if (mBitmapsList.contains(null)) { // Gallery list was filled with null => find last null
                realSize = mBitmapsList.lastIndexOf(null) + 1;
            } else { // In case null was not filled (remove), we get size of bit map
                realSize = mBitmapsList.size();
            }
        }

        mNumberOfItemsNotNull = realSize;
        // We add 1 element place holder after image
        realSize = (realSize >= NUMBER_OF_IMAGE_LIMIT) ? NUMBER_OF_IMAGE_LIMIT : realSize + 1;
        // update item count global variable
        mItemCount = realSize;

        return realSize;
    }


    @Override
    public int getItemViewType(int position) {

        int type = TYPE_ROUND_IMAGE_HORIZONTAL;
        // Place holder not appear if it's last item limit (number of item = LIMIT SIZE)
        if (mNumberOfItemsNotNull != mItemCount) {
            if (position == (mItemCount - 1)) {
                type = TYPE_ROUND_IMAGE_HORIZONTAL_PLACE_HOLDER;
            }
        }
        return type;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public MyGalleryPostProductBitmapList getBitmapsList() {
        return mBitmapsList;
    }

    public void setBitmapsList(MyGalleryPostProductBitmapList bitmapsList) {
        mBitmapsList = bitmapsList;
    }

    // ---------------------------------------------------------------------------------------------
    // INNER CLASSES
    // ---------------------------------------------------------------------------------------------

    protected class GalleryItemPostProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Context mContext = null;

        @BindView(R.id.mainImageView_GalleryItemPostProductHolder)
        RoundedImageView mMainImageView;

        Bitmap mBitmap = null;

        public GalleryItemPostProductHolder(Context context, View itemView) {
            super(itemView);
            this.mContext = context;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        private void bindHolderFrom(Bitmap bitmap) {
            if (bitmap != null && mMainImageView != null) {
                mBitmap = bitmap;
                mMainImageView.setImageBitmap(bitmap);
                mMainImageView.invalidate();
            }
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onGalleryItemImageClick(v, this.getLayoutPosition());
            }
        }
    }

    protected class GalleryItemPostProductEmptyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Context mContext = null;

        public GalleryItemPostProductEmptyHolder(Context context, View itemView) {
            super(itemView);
            this.mContext = context;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onGalleryItemEmptyPlaceHolderClick(v, this.getLayoutPosition());
            }
        }

    }
}
