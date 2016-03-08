package com.cse.hcmut.mobileappdev.features.start;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.features.start.SignIn.SignInFragment;

public class StartActivity extends AppCompatActivity implements SignInFragment.OnFragmentPass {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if (savedInstanceState == null) {
            displayView(new SignInFragment());
        }
    }

    void displayView(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (transaction != null) {
                transaction.replace(R.id.frame_container_StartActivity, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        } else {
            Log.e("StartActivity", "Error in creating fragment");
        }
    }

    @Override
    public void onFragmentPass(Fragment fragment) {
        displayView(fragment);
    }
}
