package com.cse.hcmut.mobileappdev.view.features.start.signin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;
import com.cse.hcmut.mobileappdev.constants.MyConstantValues;
import com.cse.hcmut.mobileappdev.manager.LogInManager;
import com.cse.hcmut.mobileappdev.rest.model.UserLogin;
import com.cse.hcmut.mobileappdev.rest.model.response.LoginResponse;
import com.cse.hcmut.mobileappdev.rest.model.responsecode.ResponseCode;
import com.cse.hcmut.mobileappdev.rest.model.resultset.LoginResultSet;
import com.cse.hcmut.mobileappdev.rest.services.ServiceGenerator;
import com.cse.hcmut.mobileappdev.rest.services.login.LoginService;
import com.cse.hcmut.mobileappdev.utils.NetworkUtils;
import com.cse.hcmut.mobileappdev.view.features.home.MainActivity;
import com.cse.hcmut.mobileappdev.view.features.start.forgotpassword.ForgotPasswordContainerFragment;
import com.cse.hcmut.mobileappdev.view.features.start.signup.SignUpContainerFragment;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignInSingleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignInSingleFragment#newInstance} factory method to
 * createAlertDialog an instance of this fragment.
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

    public enum LoginType {
        NORMAL("normal");

        private String mLabel;

        LoginType(String label) {
            mLabel = label;
        }

        public String getTitle() {
            return mLabel;
        }

        public static String[] getArrayValues() {
            LoginType[] districts = values();
            String[] names = new String[districts.length];

            for (int i = 0; i < districts.length; i++) {
                names[i] = districts[i].getTitle();
            }
            return names;
        }
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

    @BindView(R.id.blurBackgroundImageView_SignInSingleFragment)
    ImageView blurBackgroundImageView;

    @BindView(R.id.txtViewSignUp_SignInSingleFragment)
    TextView mSignUpTextView;

    @BindView(R.id.txtViewForgotPassword_SignInSingleFragment)
    TextView mForgotPasswordTextView;

    @BindView(R.id.editTxtUsername_SignInSingleFragment)
    EditText mUsernameEditText;

    @BindView(R.id.editTxtPassword_SignInSingleFragment)
    EditText mPasswordEditText;

    @BindView(R.id.btnSignIn_SignInSingleFragment)
    Button mSignInBtn;

    @BindView(R.id.progressBar_SignInSingleFragment)
    ProgressBar mProgressBar;

    @BindView(R.id.relativeLayoutWidgets_SignInSingleFragment)
    View mWidgetsView;

    String mUsername = "";

    String mPassword = "";

    @BindString(R.string.admin_username)
    String mAdminUsername;

    @BindString(R.string.admin_password)
    String mAdminPassword;

    @BindString(R.string.admin_token)
    String mAdminToken;

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
        return R.layout.single_fragment_sign_in;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @OnClick(R.id.btnSignIn_SignInSingleFragment)
    public void signIn() {

        long currentClickTime = SystemClock.elapsedRealtime();
        // Prevent multiple click button
        if ((currentClickTime - mLastClickTime) < MyConstantValues.TIME_INTERVAL_BUTTON_CLICK) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        // If this is offline admin account
        if (isAdminAccountOffline()) {

            Activity activity = getActivity();

            if (activity != null) {
                LogInManager.get(activity).updateAndStoreUsernameLocal(mAdminUsername);
                LogInManager.get(activity).updateAndStoreTokenLocal(mAdminToken);
            }

            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);

        } else if (!isUsernamePasswordEmpty(getActivity())) {
            new SignInAndLoadDataTask(getActivity()).execute();
        }

    }

    @OnClick(R.id.txtViewSignUp_SignInSingleFragment)
    public void signUp() {

        long currentClickTime = SystemClock.elapsedRealtime();
        // Prevent multiple click button
        if ((currentClickTime - mLastClickTime) < MyConstantValues.TIME_INTERVAL_BUTTON_CLICK) {
            return;
        }

        mLastClickTime = SystemClock.elapsedRealtime();
        mFragmentCallback.onFragmentPass(SignUpContainerFragment.newInstance());

    }

    @OnClick(R.id.txtViewForgotPassword_SignInSingleFragment)
    public void forgotPassword() {

        long currentClickTime = SystemClock.elapsedRealtime();
        // Prevent multiple click button
        if ((currentClickTime - mLastClickTime) < MyConstantValues.TIME_INTERVAL_BUTTON_CLICK) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        mFragmentCallback.onFragmentPass(new ForgotPasswordContainerFragment());

    }

    @OnTextChanged(value = R.id.editTxtUsername_SignInSingleFragment, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void updateUsername(CharSequence text) {
        mUsername = text.toString();
    }

    @OnTextChanged(value = R.id.editTxtPassword_SignInSingleFragment, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void updatePassword(CharSequence text) {
        mPassword = text.toString();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mFragmentCallback = (OnFragmentPass) context;
        } catch (ClassCastException e) {
//            Log.d("SignInFragment", context.toString() + " must implement on OnFragmentPass");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        stopSignInProgress();
    }

    private void doLoginService(final Context context) {
        final LoginService loginService = ServiceGenerator.createService(LoginService.class);
        UserLogin user = new UserLogin(mUsername, mPassword, LoginType.NORMAL.getTitle());

        Call<LoginResponse> call = loginService.basicLogin(user);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    int resultCode = loginResponse.getResponseCd();

                    if (resultCode == ResponseCode.SUCCESS) {

                        LoginResultSet loginResultSet = loginResponse.getResultSet();
                        String token = loginResultSet.getToken();
                        updateToken(token);
                        updateUsernameLocal(mUsername);

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);

                    } else { // login fail
                        stopSignInProgress();
                        showInvalidUsernamePasswordDialog(context);
                    }
                } else { // response not success due to some network problems
                    stopSignInProgress();
                    showNoConnectionDialog(context);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                stopSignInProgress();
                showInvalidUsernamePasswordDialog(context);
            }
        });
    }

    private void onSignInProgress() {

        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        if (mWidgetsView != null) {
            mWidgetsView.setVisibility(View.GONE);
        }

    }

    private void stopSignInProgress() {

        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }

        if (mWidgetsView != null) {
            mWidgetsView.setVisibility(View.VISIBLE);
        }
    }

    private void updateToken(String token) {
        Activity activity = getActivity();
        if (activity != null) {
            LogInManager.get(activity).updateAndStoreTokenLocal(token);
        }
    }

    private void updateUsernameLocal(String username) {
        Activity activity = getActivity();
        if (activity != null) {
            LogInManager.get(activity).updateAndStoreUsernameLocal(username);
        }
    }

    private void showNoConnectionDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Không thể kết nối")
                .setMessage("Rất tiếc, hiện thời bạn không thể truy cập vào máy chủ của chúng tôi."
                        + System.getProperty("line.separator") + System.getProperty("line.separator")
                        + "Vui lòng kiểm tra lại kết nối Internet")
                .setPositiveButton("OK", null)
                .show();
    }

    private void showInvalidUsernamePasswordDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Thông tin tài khoản không hợp lệ")
                .setMessage("Thông tin tài khoản bạn vừa nhập không trùng khớp với dữ liệu của chúng tôi"
                        + System.getProperty("line.separator") + System.getProperty("line.separator")
                        + "Vui lòng kiểm tra lại tên tài khoản và mật khẩu")
                .setPositiveButton("OK", null)
                .show();
    }

    private void showEmptyUsernameDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Chưa nhập username")
                .setMessage("Hãy nhập vào username")
                .setPositiveButton("OK", null)
                .show();
    }

    private void showEmptyPasswordDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Chưa nhập password")
                .setMessage("Hãy nhập vào password")
                .setPositiveButton("OK", null)
                .show();
    }

    private boolean isUsernamePasswordEmpty(Context context) {

        boolean checkUsernameAndPassword = false;

        if (mUsername.equals("")) {
            showEmptyUsernameDialog(context);
            checkUsernameAndPassword = true;
        } else if (mPassword.equals("")) {
            showEmptyPasswordDialog(context);
            checkUsernameAndPassword = true;
        }

        return checkUsernameAndPassword;
    }

    private boolean isAdminAccountOffline() {

        boolean isAdmin = false;

        if(mUsername.equals(mAdminUsername) && mPassword.equals(mAdminPassword)) {
            isAdmin = true;
        }

        return isAdmin;
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // INNER CLASSES
    // ---------------------------------------------------------------------------------------------

    private class SignInAndLoadDataTask extends AsyncTask<Void, Void, Boolean> {

        private Context mContext = null;

        public SignInAndLoadDataTask(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            onSignInProgress();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            boolean canConnected = NetworkUtils.isNetworkConnected(mContext);

            if (canConnected) {
                // update data here
            }

            return canConnected;
        }

        @Override
        protected void onPostExecute(Boolean canConnected) {
            if (canConnected) {
                doLoginService(mContext);
            } else {
                stopSignInProgress();
                showNoConnectionDialog(mContext);
            }
        }
    }
}
