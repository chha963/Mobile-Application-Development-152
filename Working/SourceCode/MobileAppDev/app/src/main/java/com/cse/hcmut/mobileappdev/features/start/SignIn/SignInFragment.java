package com.cse.hcmut.mobileappdev.features.start.SignIn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.MyFragment;
import com.cse.hcmut.mobileappdev.constants.MyConstants;
import com.cse.hcmut.mobileappdev.features.home.MainActivity;
import com.cse.hcmut.mobileappdev.features.start.ForgotPassword.ForgotPasswordFragment;
import com.cse.hcmut.mobileappdev.features.start.SignUp.SignUpFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignInFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends MyFragment {
    public SignInFragment() {
    }

    // Element in Layout
    private Button mSignUpBtn = null;
    private Button mForgotPasswordBtn = null;
    private Button mSignInBtn = null;

    // Callback to change fragment
    OnFragmentPass mFragmentCallback;

    /*
    Interface to communicate between fragment and activity when click button, text
     */
    public interface OnFragmentPass {
        void onFragmentPass(Fragment fragment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mFragmentCallback = (OnFragmentPass) context;
        } catch (ClassCastException e) {
            Log.d("SignInFragment", context.toString() + " must implement on OnFragmentPass");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_in, container, false);
        if (rootView != null) {
            mSignUpBtn = (Button) rootView.findViewById(R.id.btnSignUp_SignInFragment);
            mSignUpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long currentClickTime = SystemClock.elapsedRealtime();
                    // Prevent multiple click button
                    if ((currentClickTime - mLastClickTime) < MyConstants.TIME_INTERVAL_BUTTON_CLICK) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    mFragmentCallback.onFragmentPass(new SignUpFragment());
                }
            });

            mForgotPasswordBtn = (Button) rootView.findViewById(R.id.btnForgotPassword_SignInFragment);
            mForgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long currentClickTime = SystemClock.elapsedRealtime();
                    // Prevent multiple click button
                    if ((currentClickTime - mLastClickTime) < MyConstants.TIME_INTERVAL_BUTTON_CLICK) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    mFragmentCallback.onFragmentPass(new ForgotPasswordFragment());
                }
            });

            mSignInBtn = (Button) rootView.findViewById(R.id.btnSignIn_SignInFragment);
            mSignInBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long currentClickTime = SystemClock.elapsedRealtime();
                    // Prevent multiple click button
                    if ((currentClickTime - mLastClickTime) < MyConstants.TIME_INTERVAL_BUTTON_CLICK) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        }
        return rootView;
    }
}
