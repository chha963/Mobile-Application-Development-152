package com.cse.hcmut.mobileappdev.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.activity.MyActivity;
import com.cse.hcmut.mobileappdev.models.product.Product;
import com.cse.hcmut.mobileappdev.utils.MyUtils;
import com.cse.hcmut.mobileappdev.utils.ResourceUtils;
import com.cse.hcmut.mobileappdev.view.features.home.productdetail.ProductDetailContainerFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dinhn on 3/30/2016.
 */
public class ProductCardListAdapter extends RecyclerView.Adapter<ProductCardListAdapter.ProductCardHolder> {

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

    private ArrayList<Product> mProductList;

    int mColor = 0;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public ProductCardListAdapter(Context context, ArrayList<Product> productList) {
        mContext = context;
        mProductList = productList;
        mColor = ResourceUtils.getDefaultGreenColorTabLayout(context);
    }

    public ProductCardListAdapter(Context context, ArrayList<Product> productList, int color) {
        mContext = context;
        mProductList = productList;
        mColor = color;
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    public ProductCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.layout_product_card, null);
        ProductCardHolder productCardHolder = new ProductCardHolder(mContext, view);
        return productCardHolder;
    }

    @Override
    public void onBindViewHolder(ProductCardHolder holder, int position) {
        Product product = mProductList.get(position);
        holder.bindHolderFrom(product);
    }

    @Override
    public int getItemCount() {
        return ((null != mProductList) ? mProductList.size() : 0);
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

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

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // INNER CLASSES
    // ---------------------------------------------------------------------------------------------

    protected class ProductCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Product mProduct = null;
        Context mContext = null;

        @BindView(R.id.imageView_ProductCardLayout)
        ImageView mIconImageView;

        @BindView(R.id.txtViewProductName_ProductCardLayout)
        TextView mProductNameTextView;

        @BindView(R.id.txtViewProductDate_ProductCardLayout)
        TextView mProductDateTextView;

        @BindView(R.id.txtViewProductPrice_ProductCardLayout)
        TextView mProductPriceTextView;

        public ProductCardHolder(Context context, View itemView) {
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
                    .into(mIconImageView);

            mProductNameTextView.setText(product.getName());

            mProductDateTextView.setText(product.getDatePost());

            String priceStr = String.valueOf(product.getPrice());
            String priceFormatStr = MyUtils.addSeparatorEveryThreeCharacterFromLast(priceStr, ".");
            mProductPriceTextView.setText(priceFormatStr + " VND");
            mProductPriceTextView.setTextColor(mColor);

        }

        @Override
        public void onClick(View v) {
            replaceDetailFragmentWith(mProduct.getId());
        }
    }
}
