package com.cse.hcmut.mobileappdev.models.account;

import android.os.Parcel;
import android.os.Parcelable;

import com.cse.hcmut.mobileappdev.base.list.MyProductAccountArrayList;
import com.cse.hcmut.mobileappdev.models.product.Product;

/**
 * Created by dinhn on 4/28/2016.
 */
public class Account implements Parcelable {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_VIP = 1;

    private int mType;

    private String mId;
    private String mBannerImageId;
    private String mAvatarImageId;
    private String mUsername;
    private String mPassword;
    private String mEmail;
    private String mPhoneNumber;
    private String mAddress;
    private MyProductAccountArrayList mPersonalProductList;

    public Account(int type, String id, String bannerImageId, String avatarImageId, String username, String password, String email, String phoneNumber, String address, MyProductAccountArrayList personalProductList) {
        mType = type;
        mId = id;
        mBannerImageId = bannerImageId;
        mAvatarImageId = avatarImageId;
        mUsername = username;
        mPassword = password;
        mEmail = email;
        mPhoneNumber = phoneNumber;
        mAddress = address;
        mPersonalProductList = personalProductList;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public MyProductAccountArrayList getPersonalProductList() {
        return mPersonalProductList;
    }

    public void setPersonalProductList(MyProductAccountArrayList personalProductList) {
        mPersonalProductList = personalProductList;
    }

    public String getBannerImageId() {
        return mBannerImageId;
    }

    public void setBannerImageId(String bannerImageId) {
        mBannerImageId = bannerImageId;
    }

    public String getAvatarImageId() {
        return mAvatarImageId;
    }

    public void setAvatarImageId(String avatarImageId) {
        mAvatarImageId = avatarImageId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mType);
        dest.writeString(this.mId);
        dest.writeString(this.mBannerImageId);
        dest.writeString(this.mAvatarImageId);
        dest.writeString(this.mUsername);
        dest.writeString(this.mPassword);
        dest.writeString(this.mEmail);
        dest.writeString(this.mPhoneNumber);
        dest.writeString(this.mAddress);
        dest.writeList(this.mPersonalProductList);
    }

    protected Account(Parcel in) {
        this.mType = in.readInt();
        this.mId = in.readString();
        this.mBannerImageId = in.readString();
        this.mAvatarImageId = in.readString();
        this.mUsername = in.readString();
        this.mPassword = in.readString();
        this.mEmail = in.readString();
        this.mPhoneNumber = in.readString();
        this.mAddress = in.readString();
        this.mPersonalProductList = new MyProductAccountArrayList();
        in.readTypedList(mPersonalProductList, Product.CREATOR);
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel source) {
            return new Account(source);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };
}
