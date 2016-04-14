package com.cse.hcmut.mobileappdev.base.views.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.controller.navigationdrawer.navigation.NavigationManager;

import butterknife.Bind;
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

    protected Toolbar mToolbar;

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

        int layoutId = getLayoutId();
        setContentView(layoutId);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        NavigationManager.clear();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.toolbar_layout);

        mToolbar = (Toolbar)findViewById(R.id.toolbar_MyActivity);
        mContentLayout = (FrameLayout)findViewById(R.id.contentLayout_MyActivity);

        setSupportActionBar(mToolbar);

        getLayoutInflater().inflate(layoutResID, mContentLayout);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void displayView(int layoutId, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(layoutId, fragment);
        transaction.commit();
    }

    protected void addView(int layoutId, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(layoutId, fragment);
        transaction.commit();
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

    public void setToolbarVisibility(int visibility) {
        mToolbar.setVisibility(visibility);
    }

}

