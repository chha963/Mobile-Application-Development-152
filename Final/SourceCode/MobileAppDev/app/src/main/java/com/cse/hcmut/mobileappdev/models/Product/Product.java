package com.cse.hcmut.mobileappdev.models.Product;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by dinhn on 3/25/2016.
 */
public class Product implements Parcelable {

    public static final int INDEX_BUY_PRODUCT = 0;
    public static final int INDEX_SELL_PRODUCT = 1;

    private String mId;

    private int mType;

    private String mName = "";
    private String mAuthor = "";
    private int mPrice = 0;
    private String mDatePost = "";
    private String mAddress = "";
    private String mPhoneNumber = "";
    private String mBriefDescription = "";
    private String mOtherFullDescription = "";

    // Image
    private String mIconId = "";
    private String mCatalogueId = "";
    private ArrayList<String> mGalleryIdList = null;

    // Use for testing viewpager
    public Product(String id, int type, String iconId, String name, String briefDescription, int price) {
        mId = id;
        mType = type;
        mName = name;
        mPrice = price;
        mBriefDescription = briefDescription;
        mIconId = iconId;
    }

    // Use for testing product list, detail, post
    public Product(String id, int type, String name, String author, int price, String datePost,String briefDescription, String otherFullDescription, String phoneNumber, String address, String catalogueId, String iconId, ArrayList<String> galleryIdList) {
        mId = id;
        mType = type;
        mName = name;
        mAuthor = author;
        mPrice = price;
        mDatePost = datePost;
        mBriefDescription = briefDescription;
        mOtherFullDescription = otherFullDescription;
        mPhoneNumber = phoneNumber;
        mAddress = address;
        mCatalogueId = catalogueId;
        mIconId = iconId;
        mGalleryIdList = galleryIdList;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public int getType() {
        return mType;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public String getDatePost() {
        return mDatePost;
    }

    public void setDatePost(String datePost) {
        mDatePost = datePost;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getBriefDescription() {
        return mBriefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        mBriefDescription = briefDescription;
    }

    public String getIconId() {
        return mIconId;
    }

    public void setIconId(String iconId) {
        mIconId = iconId;
    }

    public ArrayList<String> getGalleryIdList() {
        return mGalleryIdList;
    }

    public void setGalleryIdList(ArrayList<String> galleryIdList) {
        mGalleryIdList = galleryIdList;
    }

    public String getCatalogueId() {
        return mCatalogueId;
    }

    public void setCatalogueId(String catalogueId) {
        mCatalogueId = catalogueId;
    }

    public String getOtherFullDescription() {
        return mOtherFullDescription;
    }

    public void setOtherFullDescription(String otherFullDescription) {
        mOtherFullDescription = otherFullDescription;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeInt(this.mType);
        dest.writeString(this.mName);
        dest.writeString(this.mAuthor);
        dest.writeInt(this.mPrice);
        dest.writeString(this.mDatePost);
        dest.writeString(this.mAddress);
        dest.writeString(this.mPhoneNumber);
        dest.writeString(this.mBriefDescription);
        dest.writeString(this.mOtherFullDescription);
        dest.writeString(this.mIconId);
        dest.writeString(this.mCatalogueId);
        dest.writeStringList(this.mGalleryIdList);
    }

    protected Product(Parcel in) {
        this.mId = in.readString();
        this.mType = in.readInt();
        this.mName = in.readString();
        this.mAuthor = in.readString();
        this.mPrice = in.readInt();
        this.mDatePost = in.readString();
        this.mAddress = in.readString();
        this.mPhoneNumber = in.readString();
        this.mBriefDescription = in.readString();
        this.mOtherFullDescription = in.readString();
        this.mIconId = in.readString();
        this.mCatalogueId = in.readString();
        this.mGalleryIdList = in.createStringArrayList();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
