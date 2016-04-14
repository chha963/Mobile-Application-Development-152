package com.cse.hcmut.mobileappdev.models.Section;

import android.os.Parcel;
import android.os.Parcelable;

import com.cse.hcmut.mobileappdev.models.Product.Product;

import java.util.ArrayList;

/**
 * Created by dinhn on 3/30/2016.
 */
public class SectionDataMainFragment implements Parcelable {

    private String mHeaderTitle = "";
    private ArrayList<Product> mSectionItemList;

    public SectionDataMainFragment() {
    }

    public SectionDataMainFragment(String headerTitle, ArrayList<Product> sectionItemList) {
        mHeaderTitle = headerTitle;
        mSectionItemList = sectionItemList;
    }

    public String getHeaderTitle() {
        return mHeaderTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        mHeaderTitle = headerTitle;
    }

    public ArrayList<Product> getSectionItemList() {
        return mSectionItemList;
    }

    public void setSectionItemList(ArrayList<Product> sectionItemList) {
        mSectionItemList = sectionItemList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mHeaderTitle);
        dest.writeTypedList(mSectionItemList);
    }

    protected SectionDataMainFragment(Parcel in) {
        this.mHeaderTitle = in.readString();
        this.mSectionItemList = in.createTypedArrayList(Product.CREATOR);
    }

    public static final Parcelable.Creator<SectionDataMainFragment> CREATOR = new Parcelable.Creator<SectionDataMainFragment>() {
        @Override
        public SectionDataMainFragment createFromParcel(Parcel source) {
            return new SectionDataMainFragment(source);
        }

        @Override
        public SectionDataMainFragment[] newArray(int size) {
            return new SectionDataMainFragment[size];
        }
    };
}
