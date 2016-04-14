package com.cse.hcmut.mobileappdev.view.features.start.SignIn;

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
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;
import com.cse.hcmut.mobileappdev.constants.MyConstantValues;
import com.cse.hcmut.mobileappdev.view.features.home.MainActivity;
import com.cse.hcmut.mobileappdev.view.features.start.ForgotPassword.ForgotPasswordSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.start.SignUp.SignUpFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignInSingleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignInSingleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInSingleFragment extends MyBaseSingleFragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    /**
     * Interface to communicate between fragment and activity when click button, text
     **/
    public interface OnFragmentPass {
        void onFragmentPass(Fragment fragment);
    }

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

    @Bind(R.id.btnSignUp_SignInFragment)
    Button mSignUpBtn;

    @Bind(R.id.btnForgotPassword_SignInFragment)
    Button mForgotPasswordBtn;

    @Bind(R.id.btnSignIn_SignInFragment)
    Button mSignInBtn;

    // Callback to change fragment
    OnFragmentPass mFragmentCallback;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public SignInSingleFragment() {
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sign_in;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @OnClick(R.id.btnSignIn_SignInFragment)
    public void signIn() {

        long currentClickTime = SystemClock.elapsedRealtime();
        // Prevent multiple click button
        if ((currentClickTime - mLastClickTime) < MyConstantValues.TIME_INTERVAL_BUTTON_CLICK) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @OnClick(R.id.btnSignUp_SignInFragment)
    public void signUp() {

        long currentClickTime = SystemClock.elapsedRealtime();
        // Prevent multiple click button
        if ((currentClickTime - mLastClickTime) < MyConstantValues.TIME_INTERVAL_BUTTON_CLICK) {
            return;
        }

        mLastClickTime = SystemClock.elapsedRealtime();
        mFragmentCallback.onFragmentPass(new SignUpFragment());

    }

    @OnClick(R.id.btnForgotPassword_SignInFragment)
    public void forgotPassword() {

        long currentClickTime = SystemClock.elapsedRealtime();
        // Prevent multiple click button
        if ((currentClickTime - mLastClickTime) < MyConstantValues.TIME_INTERVAL_BUTTON_CLICK) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        mFragmentCallback.onFragmentPass(new ForgotPasswordSingleFragment());

    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

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
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        if (rootView != null) {
        }
        return rootView;
    }

}
