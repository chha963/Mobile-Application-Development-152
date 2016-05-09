package com.cse.hcmut.mobileappdev.rest.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;

/**
 * Created by dinhn on 5/8/2016.
 */
public class UserSignUp {

    @SerializedName("last_name")
    private String mLastName;

    @SerializedName("first_name")
    private String mFirstName;

    @SerializedName("name")
    private String mName;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("password")
    private String mPassword;

    @SerializedName("phone")
    private String mPhone;

    @SerializedName("address")
    private String mAddress;

    @SerializedName("birthday")
    private BigInteger mBirthday = BigInteger.ZERO;

    public UserSignUp(String lastName, String firstName, String name, String email, String password, String phone, String address, BigInteger birthday) {
        mLastName = lastName;
        mFirstName = firstName;
        mName = name;
        mEmail = email;
        mPassword = password;
        mPhone = phone;
        mAddress = address;
        mBirthday = birthday;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getName() {
        return mName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getAddress() {
        return mAddress;
    }

    public BigInteger getBirthday() {
        return mBirthday;
    }
}
