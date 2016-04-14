package com.cse.hcmut.mobileappdev.view.features.start;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.activity.MyActivity;
import com.cse.hcmut.mobileappdev.view.features.start.SignIn.SignInSingleFragment;

public class StartActivity extends MyActivity implements SignInSingleFragment.OnFragmentPass {

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

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    public void onFragmentPass(Fragment fragment) {
        displayView(fragment);
    }

    @Override
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarVisibility(View.GONE);

        if (savedInstanceState == null) {
            displayView(new SignInSingleFragment());
        }
    }

    private void displayView(Fragment fragment) {
        if (fragment != null) {
            displayView(R.id.frame_container_StartActivity, fragment);
        } else {
            Log.e("StartActivity", "Error in creating fragment");
        }
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------
}
