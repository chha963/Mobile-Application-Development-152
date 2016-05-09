package com.cse.hcmut.mobileappdev.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.list.MyProductAccountArrayList;
import com.cse.hcmut.mobileappdev.base.views.activity.MyActivity;
import com.cse.hcmut.mobileappdev.models.product.Product;
import com.cse.hcmut.mobileappdev.utils.MyUtils;
import com.cse.hcmut.mobileappdev.utils.ResourceUtils;
import com.cse.hcmut.mobileappdev.view.features.home.postproduct.PostProductContainerFragment;
import com.cse.hcmut.mobileappdev.view.features.home.productdetail.ProductDetailContainerFragment;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dinhn on 5/1/2016.
 */
public class ProductCardListPersonalInfoAdapter extends RecyclerView.Adapter {

    private static final int TYPE_PRODUCT_CARD_VERTICAL_PLACE_HOLDER = 1;
    private static final int TYPE_PRODUCT_CARD_VERTICAL = 2;

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    private final int NUMBER_OF_PRODUCT_LIMIT = 5;

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    private Context mContext;

    private MyProductAccountArrayList mProductList;

    int mColor = 0;

    int mNumberOfEmptyPlaceHolder;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public ProductCardListPersonalInfoAdapter(Context context, MyProductAccountArrayList productList) {
        mContext = context;
        mProductList = productList;
        mColor = ResourceUtils.getDefaultGreenColorTabLayout(context);
        updateEmptyPlaceHolder();
    }

    public ProductCardListPersonalInfoAdapter(Context context, MyProductAccountArrayList productList, int color) {
        mContext = context;
        mProductList = productList;
        mColor = color;
        updateEmptyPlaceHolder();
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
            case TYPE_PRODUCT_CARD_VERTICAL:
                view = layoutInflater.inflate(R.layout.layout_product_card_vertical, null);
                holder = new ProductCardVerticalHolder(mContext, view);
                break;
            case TYPE_PRODUCT_CARD_VERTICAL_PLACE_HOLDER:
                view = layoutInflater.inflate(R.layout.layout_product_card_vertical_empty_place_holder, null);
                holder = new ProductCardVerticalEmptyHolder(mContext, view);
                break;
            default:
                view = layoutInflater.inflate(R.layout.layout_product_card_vertical_empty_place_holder, null);
                holder = new ProductCardVerticalEmptyHolder(mContext, view);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_PRODUCT_CARD_VERTICAL:
                if (holder instanceof ProductCardVerticalHolder) {
                    ProductCardVerticalHolder productHolder = (ProductCardVerticalHolder) holder;
                    productHolder.bindHolderFrom(mProductList.get(position - mNumberOfEmptyPlaceHolder));
                }
                break;
            case TYPE_PRODUCT_CARD_VERTICAL_PLACE_HOLDER:
                if (holder instanceof ProductCardVerticalEmptyHolder) {
                    // bind empty place holder here
                }
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return NUMBER_OF_PRODUCT_LIMIT;
    }


    @Override
    public int getItemViewType(int position) {
        //Default is place holder
        int type = TYPE_PRODUCT_CARD_VERTICAL_PLACE_HOLDER;
        if (position >= mNumberOfEmptyPlaceHolder) {
            type = TYPE_PRODUCT_CARD_VERTICAL;
        }
        return type;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    private void updateEmptyPlaceHolder() {
        if (mProductList != null) {
            mNumberOfEmptyPlaceHolder = NUMBER_OF_PRODUCT_LIMIT - mProductList.size();
        }
    }

    private void replaceEditFragmentWith(Product product) {

        MyActivity activity = (MyActivity) mContext;
        if (activity != null) {

            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            Fragment fragment = PostProductContainerFragment.newInstance(product);

            fragmentTransaction.setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.fade_out
            );

            fragmentTransaction.replace(R.id.frame_container_MainActivity, fragment);
            fragmentTransaction.addToBackStack(fragment.toString());

            fragmentTransaction.commit();
        }
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // INNER CLASSES
    // ---------------------------------------------------------------------------------------------

    protected class ProductCardVerticalHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Context mContext = null;
        Product mProduct = null;

        @BindView(R.id.imageView_ProductCardVerticalLayout)
        ImageView mIconImageView;

        @BindView(R.id.txtViewProductName_ProductCardVerticalLayout)
        TextView mProductNameTextView;

        @BindView(R.id.txtViewProductDate_ProductCardVerticalLayout)
        TextView mProductDateTextView;

        @BindView(R.id.txtViewProductPrice_ProductCardVerticalLayout)
        TextView mProductPriceTextView;

        @BindView(R.id.txtViewProductDescription_ProductCardVerticalLayout)
        TextView mProductDescriptionTextView;

        @BindView(R.id.txtViewEditProduct_ProductCardVerticalLayout)
        TextView mEditProductTextView;

        @BindView(R.id.txtViewRemoveProduct_ProductCardVerticalLayout)
        TextView mRemoveProductTextView;

        @OnClick(R.id.txtViewEditProduct_ProductCardVerticalLayout)
        void editProduct() {
            replaceEditFragmentWith(mProduct);
        }

        @OnClick(R.id.txtViewRemoveProduct_ProductCardVerticalLayout)
        void removeProduct() {
            MyUtils.showFeatureIsDevelopingDialog(mContext);
        }

        public ProductCardVerticalHolder(Context context, View itemView) {
            super(itemView);
            this.mContext = context;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        private void bindHolderFrom(Product product) {

            mProduct = product;

            Glide.with(mContext)
                    .load(mProduct.getIconId())
                    .placeholder(R.drawable.ic_place_holder)
                    .centerCrop()
                    .dontAnimate()
                    .into(mIconImageView);

            mProductNameTextView.setText(product.getName());

            mProductDateTextView.setText(product.getDatePost());

            mProductDescriptionTextView.setText(product.getOtherFullDescription());

            String priceStr = String.valueOf(product.getPrice());
            String priceFormatStr = MyUtils.addSeparatorEveryThreeCharacterFromLast(priceStr, ".")
                    + ("<sup><small>" + "VND" + "</small>" + "</sup>");
            mProductPriceTextView.setText(Html.fromHtml(priceFormatStr));

            mProductPriceTextView.setTextColor(mColor);
            mEditProductTextView.setTextColor(mColor);
            mRemoveProductTextView.setTextColor(mColor);

        }

        @Override
        public void onClick(View v) {
            replaceDetailFragmentWith(mProduct.getId());
        }

        private void replaceDetailFragmentWith(String productId) {

            MyActivity activity = (MyActivity) mContext;
            if (activity != null) {

                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Fragment fragment = ProductDetailContainerFragment.newInstance(activity, productId);

                fragmentTransaction.setCustomAnimations(
                        R.anim.fade_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.fade_out
                );

                fragmentTransaction.replace(R.id.frame_container_MainActivity, fragment);
                fragmentTransaction.addToBackStack(fragment.toString());

                fragmentTransaction.commit();
            }
        }

    }

    protected class ProductCardVerticalEmptyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Context mContext = null;

        @BindView(R.id.imageViewPlus_ProductCardVerticalEmptyPlaceHolder)
        ImageView mImageView;

        @BindView(R.id.txtViewPlus_ProductCardVerticalPlaceHolder)
        TextView mTextView;

        @BindColor(R.color.personal_info_empty_place_holder)
        int mDefaultColor;

        public ProductCardVerticalEmptyHolder(Context context, View itemView) {
            super(itemView);
            this.mContext = context;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int typeProduct = ResourceUtils.getProductTypeFromColor(mContext, mColor);
            Product product = new Product(typeProduct);
            replaceEditFragmentWith(product);
        }

    }
}
