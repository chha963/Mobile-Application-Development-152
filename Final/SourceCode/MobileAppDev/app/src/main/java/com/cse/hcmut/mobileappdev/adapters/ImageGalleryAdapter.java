package com.cse.hcmut.mobileappdev.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.view.features.home.productdetail.activity.GalleryViewPagerActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by dinhn on 4/12/2016.
 */
public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.ImageGalleryHolder> {

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

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    private Context mContext;
    private ArrayList<String> mImageGalleryArrayList;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public ImageGalleryAdapter(Context context, ArrayList<String> imageGalleryArrayList) {
        mContext = context;
        mImageGalleryArrayList = imageGalleryArrayList;
    }


    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    public ImageGalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.layout_item_image_gallery_product_detail, null);
        ImageGalleryHolder imageGalleryHolder = new ImageGalleryHolder(mContext, view);
        return imageGalleryHolder;
    }

    @Override
    public void onBindViewHolder(ImageGalleryHolder holder, int position) {
        String iconId = mImageGalleryArrayList.get(position);
        holder.bindHolderFrom(iconId);
    }

    @Override
    public int getItemCount() {
        return ((null != mImageGalleryArrayList) ? mImageGalleryArrayList.size() : 0);
    }


    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // INNER CLASSES
    // ---------------------------------------------------------------------------------------------

    protected class ImageGalleryHolder extends RecyclerView.ViewHolder {

        Context mContext = null;

        @BindView(R.id.imageView_ItemImageGalleryDetailLayout)
        ImageView mImageView;

        @OnTouch(R.id.imageView_ItemImageGalleryDetailLayout)
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

        @OnClick(R.id.imageView_ItemImageGalleryDetailLayout)
        void displayDetails() {
            Intent intent = GalleryViewPagerActivity.newIntent(mContext, mImageGalleryArrayList, getAdapterPosition());
            mContext.startActivity(intent);
        }

        public ImageGalleryHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            ButterKnife.bind(this, itemView);
        }

        private void bindHolderFrom(String iconId) {

            Glide.with(mContext)
                    .load(iconId)
                    .placeholder(R.drawable.ic_place_holder)
                    .centerCrop()
                    .into(mImageView);

        }

    }

}
