package com.cse.hcmut.mobileappdev.base.views.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.cse.hcmut.mobileappdev.manager.LogInManager;
import com.cse.hcmut.mobileappdev.models.product.ProductAccountLab;
import com.cse.hcmut.mobileappdev.models.product.ProductLab;
import com.cse.hcmut.mobileappdev.models.product.TopProductLab;
import com.cse.hcmut.mobileappdev.utils.LocaleUtils;

import butterknife.ButterKnife;

/**
 * Created by dinhn on 3/24/2016.
 */
public abstract class MyActivity extends AppCompatActivity {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    protected abstract int getLayoutId();

    protected abstract boolean isFullScreen();

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

//    protected Toolbar mToolbar;

    protected FrameLayout mContentLayout;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isFullScreen()) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }

        int layoutId = getLayoutId();
        setContentView(layoutId);

        ButterKnife.bind(this);

        LocaleUtils.updateConfig(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Hide soft keyboard if it visible
        View view = this.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if ((view != null) && (imm != null)) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        ProductLab.clear();
        TopProductLab.clear();
        ProductAccountLab.clear();
        LogInManager.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //    @Override
//    public void setContentView(int layoutResID) {
//        super.setContentView(R.layout.toolbar_layout);
//
//        mToolbar = (Toolbar)findViewById(R.id.toolbar_MyActivity);
//        mContentLayout = (FrameLayout)findViewById(R.id.contentLayout_MyActivity);
//
//        setSupportActionBar(mToolbar);
//
//        getLayoutInflater().inflate(layoutResID, mContentLayout);
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

//    public void setToolbarVisibility(int visibility) {
//        if (mToolbar != null) {
//            mToolbar.setVisibility(visibility);
//        }
//    }
}

