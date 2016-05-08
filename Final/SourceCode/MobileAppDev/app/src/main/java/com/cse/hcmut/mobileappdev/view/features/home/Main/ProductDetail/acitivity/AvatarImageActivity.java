package com.cse.hcmut.mobileappdev.view.features.home.Main.ProductDetail.acitivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.activity.MyActivity;

import butterknife.Bind;

public class AvatarImageActivity extends MyActivity {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    private static final String EXTRA_AVATAR_IMAGE_URL =
            "com.cse.hcmut.mobileappdev.view.features.home.Main.ProductDetail.activity.avatar_image_url";

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static Intent newIntent(Context packageContext, String urlImage) {
        Intent intent = new Intent(packageContext, AvatarImageActivity.class);
        intent.putExtra(EXTRA_AVATAR_IMAGE_URL, urlImage);
        return intent;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @Bind(R.id.imageView_AvatarImageActivity)
    ImageView mAvatarImageView;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected boolean isFullScreen() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_avatar_image;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarVisibility(View.GONE);

        Intent currentIntent = getIntent();
        if (currentIntent != null) {
            String url = currentIntent.getStringExtra(EXTRA_AVATAR_IMAGE_URL);
            loadImageGlideFromCache(url);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void loadImageGlideFromCache(String url) {
        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mAvatarImageView);
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
