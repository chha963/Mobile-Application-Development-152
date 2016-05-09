package com.cse.hcmut.mobileappdev.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.utils.MyUtils;
import com.cse.hcmut.mobileappdev.utils.ResourceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dinhn on 4/5/2016.
 */
public class CategoryCapsuleListAdapter extends RecyclerView.Adapter<CategoryCapsuleListAdapter.CategoryCapsuleHolder> {

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

    private String[] mCategoryTitleList;

    int mCurrentColor;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public CategoryCapsuleListAdapter(Context context, String[] categoryTitleList, int color) {
        mContext = context;
        mCategoryTitleList = categoryTitleList;
        mCurrentColor = color;
    }

    public CategoryCapsuleListAdapter(Context context, String[] categoryTitleList) {
        mContext = context;
        mCategoryTitleList = categoryTitleList;
        mCurrentColor = ResourceUtils.getDefaultGreenColorTabLayout(context);
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    public CategoryCapsuleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.layout_category_button, null);
        CategoryCapsuleHolder categoryCapsuleHolder = new CategoryCapsuleHolder(mContext, view);
        return categoryCapsuleHolder;
    }

    @Override
    public void onBindViewHolder(CategoryCapsuleHolder holder, int position) {
        String title = mCategoryTitleList[position];
        holder.setTitleText(title);
        holder.setBackgroundColor(mCurrentColor);

        setupButton(holder);
    }

    @Override
    public int getItemCount() {
        return ((null != mCategoryTitleList) ? mCategoryTitleList.length : 0);
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    private void setupButton(CategoryCapsuleHolder holder) {

        Button capsuleButton = holder.getCapsuleButton();
        Paint textPaint = capsuleButton.getPaint();

        float width = textPaint.measureText(capsuleButton.getText().toString());

        ViewGroup.LayoutParams layoutParams = capsuleButton.getLayoutParams();
        int bonusWidth = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                45,
                mContext.getResources().getDisplayMetrics()
        );
        layoutParams.width = (int) width + bonusWidth;
        capsuleButton.setLayoutParams(layoutParams);

    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // INNER CLASS
    // ---------------------------------------------------------------------------------------------

    protected class CategoryCapsuleHolder extends RecyclerView.ViewHolder {

        Context mContext;
        String mTitle;

        @BindView(R.id.btnCapsule_CategoryButtonLayout)
        Button mCapsuleButton;

        @OnClick(R.id.btnCapsule_CategoryButtonLayout)
        public void selectCategory() {
            MyUtils.showFeatureIsDevelopingDialog(mContext);
        }

        public CategoryCapsuleHolder(Context context, View itemView) {
            super(itemView);
            this.mContext = context;
            ButterKnife.bind(this, itemView);
        }

        private void setTitleText(String title) {
            mCapsuleButton.setText(title);
            mTitle = title;
        }

        public String getTitle() {
            return mTitle;
        }

        public Button getCapsuleButton() {
            return mCapsuleButton;
        }

        public void setBackgroundColor(int color) {

            Drawable buttonDrawable = mCapsuleButton.getBackground();

            if (buttonDrawable instanceof ShapeDrawable) {
                ShapeDrawable shapeDrawable = (ShapeDrawable) buttonDrawable;
                shapeDrawable.getPaint().setColor(color);
            } else if (buttonDrawable instanceof GradientDrawable) {
                GradientDrawable gradientDrawable = (GradientDrawable) buttonDrawable;
                gradientDrawable.setColor(color);
            } else if (buttonDrawable instanceof ColorDrawable) {
                ColorDrawable colorDrawable = (ColorDrawable) buttonDrawable;
                colorDrawable.setColor(color);
            }

        }
    }
}
