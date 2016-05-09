package com.cse.hcmut.mobileappdev.view.features.start.signup.content;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.adapters.NothingSelectedSpinnerAdapter;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;
import com.cse.hcmut.mobileappdev.constants.MyConstantEnums;
import com.cse.hcmut.mobileappdev.rest.model.UserSignUp;
import com.cse.hcmut.mobileappdev.rest.model.response.CityInfoResponse;
import com.cse.hcmut.mobileappdev.rest.model.response.SignUpResponse;
import com.cse.hcmut.mobileappdev.rest.model.responsecode.ResponseCode;
import com.cse.hcmut.mobileappdev.rest.services.ServiceGenerator;
import com.cse.hcmut.mobileappdev.rest.services.signup.CityInfoService;
import com.cse.hcmut.mobileappdev.rest.services.signup.SignUpService;
import com.cse.hcmut.mobileappdev.utils.ColorFilterGenerator;
import com.cse.hcmut.mobileappdev.utils.ResourceUtils;
import com.cse.hcmut.mobileappdev.view.features.start.signup.dialog.PolicyDialogSingleFragment;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dinhn on 4/24/2016.
 */
public class ContentSignUpSingleFragment extends MyBaseSingleFragment implements DatePickerDialog.OnDateSetListener {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    private static final String DIALOG_POLICY = "DialogPolicy";

    private static final int REQUEST_DIALOG_POLICY = 1;

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static ContentSignUpSingleFragment newInstance() {

        Bundle args = new Bundle();

        ContentSignUpSingleFragment fragment = new ContentSignUpSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @BindView(R.id.usernameImageView_ContentBotSignUpSingleFragment)
    ImageView mUsernameImageView;

    @BindView(R.id.passwordImageView_ContentBotSignUpSingleFragment)
    ImageView mPasswordImageView;

    @BindView(R.id.rePasswordImageView_ContentBotSignUpSingleFragment)
    ImageView mRePasswordImageView;

    @BindView(R.id.emailImageView_ContentSignUpSingleFragment)
    ImageView mEmailImageView;

    @BindView(R.id.phoneNumberImageView_ContentSignUpSingleFragment)
    ImageView mPhoneNumberImageView;

    @BindView(R.id.birthDateImageView_ContentSignUpSingleFragment)
    ImageView mBirthDateImageView;

    @BindView(R.id.lastNameEditText_ContentSignUpSingleFragment)
    EditText mLastNameEditText;

    @BindView(R.id.firstNameEditText_ContentSignUpSingleFragment)
    EditText mFirstNameEditText;

    @BindView(R.id.usernameEditText_ContentSignUpSingleFragment)
    EditText mUsernameEditText;

    @BindView(R.id.passwordEditText_ContentSignUpSingleFragment)
    EditText mPasswordEditText;

    @BindView(R.id.rePasswordEditText_ContentSignUpSingleFragment)
    EditText mRePasswordEditText;

    @BindView(R.id.emailEditText_ContentSignUpSingleFragment)
    EditText mEmailEditText;

    @BindView(R.id.phoneNumberEditText_ContentSignUpSingleFragment)
    EditText mPhoneNumberEditText;

    @BindView(R.id.addressStreetEditText_ContentSignUpSingleFragment)
    EditText mAddressStreetEditText;

    @BindView(R.id.addressWardSpinner_ContentSignUpSingleFragment)
    Spinner mAddressWardSpinner;

    @BindView(R.id.addressDistrictSpinner_ContentSignUpSingleFragment)
    Spinner mAddressDistrictSpinner;

    @BindView(R.id.addressProvinceCityEditText_ContentSignUpSingleFragment)
    EditText mAddressProvinceCityEditText;

    @BindView(R.id.birthDateEditText_ContentSignUpSingleFragment)
    EditText mBirthDateEditText;

    @BindString(R.string.edit_txt_address_ward_hint_sign_up)
    String mAddressWardSpinnerTextHint;

    @BindString(R.string.edit_txt_address_district_hint_sign_up)
    String mAddressDistrictSpinnerTextHint;

    @BindString(R.string.edit_txt_address_city_hint_sign_up)
    String mAddressProvinceCitySpinnerTextHint;

    @BindView(R.id.txtViewPolicy_ContentSignUpSingleFragment)
    TextView mPolicyTextView;

    @BindView(R.id.btnSignUp_ContentSignUpSingleFragment)
    Button mSignUpButton;

    @BindColor(R.color.icon_sign_up)
    int iconColor;

    private String mLastName = "";

    private String mFirstName = "";

    private String mName = "";

    private String mEmail = "";

    private String mPassword = "";

    private String mRePassword = "";

    private String mPhone = "";

    private String mAddress = "";

    private String mAddressStreet = "";

    private String mWard = "";

    private String mDistrict = "";

    private String mCity = "TP.Hồ Chí Minh";

    private String mCountry = "Việt Nam";

    private BigInteger mBirthday = BigInteger.ZERO;

    String[] mItemsWard = MyConstantEnums.Ward.getArrayValues();

    String[] mItemsDistrict = MyConstantEnums.District.getArrayValues();

    String[] mItemsProvinceCity = MyConstantEnums.ProvinceCity.getArrayValues();


    // ---------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.single_fragment_content_bot_sign_up;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int realMonthOfYear = monthOfYear + 1;
        mBirthDateEditText.setText("Ngày " + dayOfMonth + " tháng " + realMonthOfYear + " năm " + year);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        mBirthday = BigInteger.valueOf(calendar.getTimeInMillis());
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @OnClick(R.id.txtViewSignIn_ContentSignUpSingleFragment)
    void backToSignIn() {
        Activity activity = getActivity();
        if (activity != null) {
            activity.onBackPressed();
        }
    }

    @OnClick(R.id.txtViewPolicy_ContentSignUpSingleFragment)
    void showPolicy() {
        FragmentManager fragmentManager = getFragmentManager();
        PolicyDialogSingleFragment fragment = createPolicyDialog();
        fragment.setTargetFragment(ContentSignUpSingleFragment.this, REQUEST_DIALOG_POLICY);
        fragment.show(fragmentManager, DIALOG_POLICY);
    }

    @OnClick(R.id.btnSignUp_ContentSignUpSingleFragment)
    void signUp() {
        Activity activity = getActivity();
        if (activity != null) {
            if (isValidSignUpInfo(activity)) {
                new SignUpTask(activity).execute();
            }
        }

    }

    // Use onTouch to prevent keyboard auto appear
    @OnTouch(R.id.birthDateEditText_ContentSignUpSingleFragment)
    boolean chooseBirthDate(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return true;
            }
            mBirthDateEditText.requestFocus();
            mLastClickTime = SystemClock.elapsedRealtime();
            v.setPressed(true);
            closeKeyboardIfAppear();
            showDatePickerDialog();
        }
        return true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changeColorIcons();
//        setupSpinnerDropDownList();
        bindWidgetsToListener();
        doGetCityInfoService(getActivity());

    }

    private void bindWidgetsToListener() {

        mLastNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    v.setPressed(true);
                    mFirstNameEditText.requestFocus();
                    return true;
                }
                return false;
            }
        });

        mAddressStreetEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    v.setPressed(true);
                    closeKeyboardIfAppear();
                    mAddressStreetEditText.clearFocus();
                    mAddressWardSpinner.performClick();
                    return true;
                }
                return false;
            }
        });

        mFirstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mFirstName = mFirstNameEditText.getText().toString();
            }
        });

        mLastNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mLastName = mLastNameEditText.getText().toString();
            }
        });

        mUsernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mName = mUsernameEditText.getText().toString();
            }
        });

        mEmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mEmail = mEmailEditText.getText().toString();
            }
        });

        mPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPassword = mPasswordEditText.getText().toString();
            }
        });

        mRePasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mRePassword = mRePasswordEditText.getText().toString();
            }
        });

        mPhoneNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPhone = mPhoneNumberEditText.getText().toString();
            }
        });

        mAddressStreetEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAddressStreet = mAddressStreetEditText.getText().toString();
            }
        });

        mAddressWardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // first item is text hint (default)
                if (position != 0) {
                    mWard = mAddressWardSpinner.getSelectedItem().toString();
                } else {
                    mWard = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mWard = "";
            }
        });

        mAddressDistrictSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    mDistrict = mAddressDistrictSpinner.getSelectedItem().toString();
                } else {
                    mDistrict = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDistrict = "";
            }
        });

    }

    private void changeColorIcons() {

        if (mUsernameImageView != null) {
            mUsernameImageView.setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP);
        }

        if (mPasswordImageView != null) {
            mPasswordImageView.setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP);
        }

        if (mRePasswordImageView != null) {
            mRePasswordImageView.setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP);
        }

        if (mEmailImageView != null) {
            mEmailImageView.setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP);
        }

        if (mPhoneNumberImageView != null) {
            Drawable drawable = mPhoneNumberImageView.getDrawable();
            ColorFilter colorFilter = ColorFilterGenerator.from(drawable).to(iconColor);
            mPhoneNumberImageView.setColorFilter(colorFilter);
        }

        if (mBirthDateImageView != null) {
            mBirthDateImageView.setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP);
        }

    }

    private void setupSpinnerDropDownList() {

        Activity activity = getActivity();

        if (activity != null) {

            int layoutDropDownSpinner = R.layout.layout_spinner_dropdown_item;
            int layoutSpinnerItem = R.layout.layout_spinner_item;

            ArrayAdapter<String> wardsArrayAdapter
                    = new ArrayAdapter<>(activity, layoutDropDownSpinner, mItemsWard);

            ArrayAdapter<String> districtsArrayAdapter
                    = new ArrayAdapter<>(activity, layoutDropDownSpinner, mItemsDistrict);

//            ArrayAdapter<String> provincesCitiesArrayAdapter
//                    = new ArrayAdapter<>(activity, layoutDropDownSpinner, mItemsProvinceCity);


            NothingSelectedSpinnerAdapter wardsAdapter = new NothingSelectedSpinnerAdapter(
                    wardsArrayAdapter,
                    layoutSpinnerItem,
                    activity
            );
            wardsAdapter.setTextHint(mAddressWardSpinnerTextHint);

            NothingSelectedSpinnerAdapter districtsAdapter = new NothingSelectedSpinnerAdapter(
                    districtsArrayAdapter,
                    layoutSpinnerItem,
                    activity
            );
            districtsAdapter.setTextHint(mAddressDistrictSpinnerTextHint);

//            NothingSelectedSpinnerAdapter provincesCitiesAdapter = new NothingSelectedSpinnerAdapter(
//                    provincesCitiesArrayAdapter,
//                    layoutSpinnerItem,
//                    activity
//            );
//            provincesCitiesAdapter.setTextHint(mAddressProvinceCitySpinnerTextHint);

            mAddressWardSpinner.setAdapter(wardsAdapter);
            mAddressDistrictSpinner.setAdapter(districtsAdapter);
//            mAddressProvinceCitySpinner.setAdapter(provincesCitiesAdapter);

        }

    }

    private void closeKeyboardIfAppear() {
        // close keyboard first if it existed
        Activity activity = getActivity();
        if (activity != null) {
            View view = activity.getCurrentFocus();

            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if ((view != null) && (imm != null)) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    private void showDatePickerDialog() {

        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.vibrate(false);
        datePickerDialog.showYearPickerFirst(true);
        datePickerDialog.setAccentColor(ResourceUtils.getDefaultGreenColorTabLayout(getActivity()));
        datePickerDialog.setTitle("CHỌN NGÀY SINH CỦA BẠN");
        datePickerDialog.setMaxDate(now);
        datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
    }

    private PolicyDialogSingleFragment createPolicyDialog() {

        final PolicyDialogSingleFragment policyDialogSingleFragment
                = PolicyDialogSingleFragment.newInstance();

        policyDialogSingleFragment.setCallbacksListener(new PolicyDialogSingleFragment.CallbacksListener() {
            @Override
            public void onCloseButtonClicked() {

            }
        });

        return policyDialogSingleFragment;
    }

    private void doSignUpService(final Context context) {
        final SignUpService signUpService = ServiceGenerator.createService(SignUpService.class);

//        Object wardObject = mAddressWardSpinner.getSelectedItem();
//        Object districtObject = mAddressDistrictSpinner.getSelectedItem();
//
//        if (wardObject != null && districtObject != null) {
//            mWard = wardObject.toString();
//            mDistrict = districtObject.toString();
//        }

        mAddress = mAddressStreet + ", " + mWard + ", " + mDistrict + ", " + mCity + ", " + mCountry;

        UserSignUp userSignUp = new UserSignUp(
                mLastName,
                mFirstName,
                mName,
                mEmail,
                mPassword,
                mPhone,
                mAddress,
                mBirthday
        );

        Call<SignUpResponse> call = signUpService.register(userSignUp);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
//                Log.d("signup","sign up succeed ");
//                    Log.d("signup","sign in html code = " + response.raw().toString());
                if (response.isSuccessful()) {
                    int responseCode = response.body().getResponseCd();

                    if (responseCode == ResponseCode.SUCCESS) {
                        showSignUpSuccessDialog(context);
                    } else if (responseCode == ResponseCode.ACCOUNT_EXISTED) {
                        showUserEmailExistedDialog(context);
                    } else if (responseCode == ResponseCode.WRONG_ADDRESS) {
                        showWrongAddressDialog(context);
                    } else {
                        showCanNotSignUpDialog(context);
                    }
//                    Log.d("signup","sign up respsonse mg = " + response.body().getResponseMsg());
//                    Log.d("signup","sign up respsonse mg = " + response.body().getResponseCd());
                } else {
                    showCanNotSignUpDialog(context);
                }
            }


            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                showCanNotSignUpDialog(context);
            }
        });


    }

    private void doGetCityInfoService(Context context) {
        final CityInfoService cityInfoService = ServiceGenerator.createService(CityInfoService.class);

        Call<CityInfoResponse> call = cityInfoService.getCityInfo();
        call.enqueue(new Callback<CityInfoResponse>() {
            @Override
            public void onResponse(Call<CityInfoResponse> call, Response<CityInfoResponse> response) {
                if (response.isSuccessful()) {
                    CityInfoResponse cityInfoResponse = response.body();
                    List<String> wardList = cityInfoResponse.getCityInfoResultSet().getWard();
                    List<String> districtList = cityInfoResponse.getCityInfoResultSet().getDistrict();

                    String[] tmpWardList = wardList.toArray(new String[wardList.size()]);
                    String[] tmpDistrictList = districtList.toArray(new String[districtList.size()]);

                    mItemsWard = ResourceUtils.makeNewStringArrayWithPrefix(tmpWardList, "Phường ");
                    mItemsDistrict = ResourceUtils.makeNewStringArrayWithPrefix(tmpDistrictList, "Quận ");
                }
                setupSpinnerDropDownList();
            }

            @Override
            public void onFailure(Call<CityInfoResponse> call, Throwable t) {
                setupSpinnerDropDownList();
            }
        });
    }

    private boolean isValidSignUpInfo(Context context) {

        boolean checkInfo = true;

        if (!isFillAllInformation()) {
            checkInfo = false;
            showEmptyFieldDialog(context);
        } else if (!isValidPassword()) {
            checkInfo = false;
            showInvalidPasswordDialog(context);
        } else if (!isPasswordMatchRePassword()) {
            checkInfo = false;
            showNotMatchPasswordDialog(context);
        } else if (!isValidEmail()) {
            checkInfo = false;
            showInvalidEmailFormatDialog(context);
        } else {
            checkInfo = true;
        }

        return checkInfo;
    }

    private boolean isValidEmail() {

        boolean checkEmail;

        final Pattern emailPattern = Pattern.compile("([a-zA-Z0-9_.+-]+)(@)([a-zA-Z0-9-]+)(\\.[a-zA-Z0-9-]+)*");
        final Matcher emailEditTextMatcher = emailPattern.matcher(mEmail);

        checkEmail = emailEditTextMatcher.matches();

        return checkEmail;
    }

    private void showInvalidEmailFormatDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Định dạng email không chính xác")
                .setMessage("Vui lòng kiểm tra lại email")
                .setPositiveButton("OK", null)
                .show();
    }

    private boolean isFillAllInformation() {

        boolean check = true;

        if (mFirstName.equals("")
                || mLastName.equals("")
                || mName.equals("")
                || mPassword.equals("")
                || mRePassword.equals("")
                || mEmail.equals("")
                || mPhone.equals("")
                || mAddressStreet.equals("")
                || mWard.equals("")
                || mDistrict.equals("")
                || (mBirthday.compareTo(BigInteger.ZERO) == 0)) {
            check = false;
        }

        return check;
    }

    private void showEmptyFieldDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Thông tin không đầy đủ")
                .setMessage("Vui lòng kiểm tra lại và điền tất cả các thông tin tài khoản")
                .setPositiveButton("OK", null)
                .show();
    }

    private boolean isValidPassword() {
        return mPassword.length() >= 6;
    }

    private void showInvalidPasswordDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Mật khẩu không đủ an toàn")
                .setMessage("Mật khẩu phải có từ 6 ký tự trở lên")
                .setPositiveButton("OK", null)
                .show();
    }

    private boolean isPasswordMatchRePassword() {
        return mPassword.equals(mRePassword);
    }

    private void showNotMatchPasswordDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Mật khẩu không trùng khớp")
                .setMessage("Mật khẩu và nhập lại mật khẩu phải giống nhau")
                .setPositiveButton("OK", null)
                .show();
    }

    private void showCanNotSignUpDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Không thể đăng ký tài khoản")
                .setMessage("Vui lòng kiểm tra lại kết nối internet và thử lại sau")
                .setPositiveButton("OK", null)
                .show();
    }

    private void showUserEmailExistedDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Tài khoản/Email này đã đăng ký")
                .setMessage("Vui lòng đăng ký tài khoản/email khác")
                .setPositiveButton("OK", null)
                .show();
    }

    private void showWrongAddressDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Địa chỉ này không tồn tại")
                .setMessage("Vui lòng kiểm tra và chọn lại địa chỉ khác")
                .setPositiveButton("OK", null)
                .show();
    }

    private void showSignUpSuccessDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Đăng ký thành công")
                .setMessage("Nhấn OK để quay về trang đăng nhập")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Activity activity = getActivity();
                        if (activity != null) {
                            activity.onBackPressed();
                        }
                    }
                })
                .show();
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // INNER CLASSES
    // ---------------------------------------------------------------------------------------------

    private class SignUpTask extends AsyncTask<Void, Void, Integer> {

        private Context mContext = null;

        private ProgressDialog mSignUpProgressDialog = null;

        public SignUpTask(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            mSignUpProgressDialog = ProgressDialog.show(mContext, "Đăng ký tài khoản", "Vui lòng chờ trong giây lát ...", true);
        }

        @Override
        protected Integer doInBackground(Void... params) {

            final SignUpService signUpService = ServiceGenerator.createService(SignUpService.class);

            mAddress = mAddressStreet + ", " + mWard + ", " + mDistrict + ", " + mCity + ", " + mCountry;

            UserSignUp userSignUp = new UserSignUp(
                    mLastName,
                    mFirstName,
                    mName,
                    mEmail,
                    mPassword,
                    mPhone,
                    mAddress,
                    mBirthday
            );

            int responseCode = ResponseCode.NO_CONNECTION;

            Call<SignUpResponse> call = signUpService.register(userSignUp);

            try {
                Response<SignUpResponse> response = call.execute();
                if (response.isSuccessful()) {
                    responseCode = response.body().getResponseCd();
                }
            } catch (IOException e) {
                responseCode = ResponseCode.NO_CONNECTION;
            }

            return responseCode;
        }

        @Override
        protected void onPostExecute(Integer responseCode) {
            mSignUpProgressDialog.dismiss();

            if (responseCode == ResponseCode.SUCCESS) {
                showSignUpSuccessDialog(mContext);
            } else if (responseCode == ResponseCode.ACCOUNT_EXISTED) {
                showUserEmailExistedDialog(mContext);
            } else if (responseCode == ResponseCode.WRONG_ADDRESS) {
                showWrongAddressDialog(mContext);
            } else if (responseCode == ResponseCode.NO_CONNECTION) {
                showCanNotSignUpDialog(mContext);
            }
        }
    }
}
