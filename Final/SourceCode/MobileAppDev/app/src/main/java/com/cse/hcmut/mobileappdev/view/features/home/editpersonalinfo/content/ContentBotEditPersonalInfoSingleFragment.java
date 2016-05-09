package com.cse.hcmut.mobileappdev.view.features.home.editpersonalinfo.content;

import android.app.Activity;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
import com.cse.hcmut.mobileappdev.manager.LogInManager;
import com.cse.hcmut.mobileappdev.models.account.Account;
import com.cse.hcmut.mobileappdev.utils.ColorFilterGenerator;
import com.cse.hcmut.mobileappdev.utils.MyUtils;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dinhn on 5/2/2016.
 */
public class ContentBotEditPersonalInfoSingleFragment extends MyBaseSingleFragment {

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

    public static ContentBotEditPersonalInfoSingleFragment newInstance() {

        Bundle args = new Bundle();

        ContentBotEditPersonalInfoSingleFragment fragment = new ContentBotEditPersonalInfoSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @BindView(R.id.emailEditText_ContentBotEditPersonalInfoSingleFragment)
    EditText mEmailEditText;

    @BindView(R.id.phoneNumberEditText_ContentBotEditPersonalInfoSingleFragment)
    EditText mPhoneNumberEditText;

    @BindView(R.id.addressStreetEditText_ContentBotEditPersonalInfoSingleFragment)
    EditText mAddressStreetEditText;

    @BindView(R.id.emailImageView_ContentBotEditPersonalInfoSingleFragment)
    ImageView mEmailImageView;

    @BindView(R.id.phoneNumberImageView_ContentBotEditPersonalInfoSingleFragment)
    ImageView mPhoneNumberImageView;

    @BindView(R.id.oldPasswordImageView_ContentBotEditPersonalInfoSingleFragment)
    ImageView mOldPasswordImageView;

    @BindView(R.id.newPasswordImageView_ContentBotEditPersonalInfoSingleFragment)
    ImageView mNewPasswordImageView;

    @BindView(R.id.reNewPasswordImageView_ContentBotEditPersonalInfoSingleFragment)
    ImageView mReNewPasswordImageView;

    @BindView(R.id.addressWardSpinner_ContentBotEditPersonalInfoSingleFragment)
    Spinner mAddressWardSpinner;

    @BindView(R.id.addressDistrictSpinner_ContentBotEditPersonalInfoSingleFragment)
    Spinner mAddressDistrictSpinner;

    @BindView(R.id.addressProvinceCitySpinner_ContentBotEditPersonalInfoSingleFragment)
    Spinner mAddressProvinceCitySpinner;

    @BindView(R.id.btnEditInfo_ContentBotEditPersonalInfoSingleFragment)
    Button mEditInfoButton;

    @BindString(R.string.edit_txt_address_ward_hint_edit_personal_info)
    String mAddressWardSpinnerTextHint;

    @BindString(R.string.edit_txt_address_district_hint_edit_personal_info)
    String mAddressDistrictSpinnerTextHint;

    @BindString(R.string.edit_txt_address_city_hint_edit_personal_info)
    String mAddressProvinceCitySpinnerTextHint;

    @BindColor(R.color.icon_edit_personal_info)
    int iconColor;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.single_fragment_content_bot_edit_personal_info;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @OnClick(R.id.btnEditInfo_ContentBotEditPersonalInfoSingleFragment)
    void editInfo() {
        MyUtils.showFeatureIsDevelopingDialog(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changeColorIcons();
        setupSpinnerDropDownList();
        setupDataToWidgets();

        mAddressStreetEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                    v.setPressed(true);
                    mAddressStreetEditText.clearFocus();
                    closeKeyboardIfAppear();
                    mAddressWardSpinner.performClick();
                    return true;
                }
                return false;
            }
        });
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

    private void changeColorIcons() {

        if (mEmailImageView != null) {
            mEmailImageView.setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP);
        }

        if (mPhoneNumberImageView != null) {
            Drawable drawable = mPhoneNumberImageView.getDrawable();
            ColorFilter colorFilter = ColorFilterGenerator.from(drawable).to(iconColor);
            mPhoneNumberImageView.setColorFilter(colorFilter);
        }

        if (mOldPasswordImageView != null) {
            mOldPasswordImageView.setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP);
        }

        if (mNewPasswordImageView != null) {
            mNewPasswordImageView.setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP);
        }

        if (mReNewPasswordImageView != null) {
            mReNewPasswordImageView.setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP);
        }

    }

    private void setupSpinnerDropDownList() {

        String[] itemsWard = MyConstantEnums.Ward.getArrayValues();
        String[] itemsDistrict = MyConstantEnums.District.getArrayValues();
        String[] itemsProvinceCity = MyConstantEnums.ProvinceCity.getArrayValues();

        Activity activity = getActivity();

        if (activity != null) {

            int layoutDropDownSpinner = R.layout.layout_spinner_dropdown_item;
            int layoutSpinnerItem = R.layout.layout_spinner_item;

            ArrayAdapter<String> wardsArrayAdapter
                    = new ArrayAdapter<>(activity, layoutDropDownSpinner, itemsWard);

            ArrayAdapter<String> districtsArrayAdapter
                    = new ArrayAdapter<>(activity, layoutDropDownSpinner, itemsDistrict);

            ArrayAdapter<String> provincesCitiesArrayAdapter
                    = new ArrayAdapter<>(activity, layoutDropDownSpinner, itemsProvinceCity);

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

            NothingSelectedSpinnerAdapter provincesCitiesAdapter = new NothingSelectedSpinnerAdapter(
                    provincesCitiesArrayAdapter,
                    layoutSpinnerItem,
                    activity
            );
            provincesCitiesAdapter.setTextHint(mAddressProvinceCitySpinnerTextHint);

            mAddressWardSpinner.setAdapter(wardsAdapter);
            mAddressDistrictSpinner.setAdapter(districtsAdapter);
            mAddressProvinceCitySpinner.setAdapter(provincesCitiesAdapter);

        }

    }

    private void setupDataToWidgets() {

        Account account = LogInManager.get(getActivity()).getAccount();

        if (mEmailEditText != null) {
            mEmailEditText.setText(account.getEmail());
        }

        if (mPhoneNumberEditText != null) {
            mPhoneNumberEditText.setText(account.getPhoneNumber());
        }

        if (mAddressStreetEditText != null) {
            mAddressStreetEditText.setText("Đường Nguyễn Du");
        }

        if (mAddressWardSpinner != null) {
            mAddressWardSpinner.setSelection(1);
        }

        if (mAddressDistrictSpinner != null) {
            mAddressDistrictSpinner.setSelection(1);
        }

        if (mAddressProvinceCitySpinner != null) {
            mAddressProvinceCitySpinner.setSelection(1);
        }

    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
