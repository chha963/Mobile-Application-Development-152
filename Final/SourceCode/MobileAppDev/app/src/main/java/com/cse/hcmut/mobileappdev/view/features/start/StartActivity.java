package com.cse.hcmut.mobileappdev.view.features.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.activity.MyActivity;
import com.cse.hcmut.mobileappdev.manager.LogInManager;
import com.cse.hcmut.mobileappdev.view.features.home.MainActivity;
import com.cse.hcmut.mobileappdev.view.features.start.signin.SignInSingleFragment;

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
//        setToolbarVisibility(View.GONE);

        if (savedInstanceState == null) {
            String token = LogInManager.get(this).getToken();

            displayDefaultView(new SignInSingleFragment());
            // Get token, ưe sign in lasted time
            if (!token.equals("")) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }


    private void displayDefaultView(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.replace(R.id.frame_container_StartActivity, fragment);
            transaction.commit();
        }
    }

    private void displayView(Fragment fragment) {
        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.setCustomAnimations(
                    R.anim.slide_in_left,
                    R.anim.slide_out_right,
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
            );

            transaction.replace(R.id.frame_container_StartActivity, fragment);
            transaction.addToBackStack(fragment.toString());

            transaction.commit();
        } else {
            Log.e("StartActivity", "Error in creating fragment");
        }
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------
}
