package com.cse.hcmut.mobileappdev.base.list;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dinhn on 5/7/2016.
 */
public class MyGalleryPostProductBitmapList extends ArrayList<Bitmap> {

    // default only contain 5 products
    private static int mNumberUrlsLimit = 5;

    public MyGalleryPostProductBitmapList() {
        super(mNumberUrlsLimit);
    }

    public MyGalleryPostProductBitmapList(int numberUrlsLimit) {
        super(numberUrlsLimit);
        mNumberUrlsLimit = numberUrlsLimit;
    }

    @Override
    public Bitmap get(int index) {
        Bitmap bitmap = null;
        if (index < mNumberUrlsLimit) {
            bitmap = super.get(index);
        }
        return bitmap;
    }

    @Override
    public Bitmap set(int index, Bitmap bitmap) {
        Bitmap tmpProduct = get(index);
        if (index < mNumberUrlsLimit) {
            super.set(index, bitmap);
        }
        return tmpProduct;
    }

    @Override
    public boolean add(Bitmap bitmap) {
        boolean canAdd = false;
        if (this.size() < mNumberUrlsLimit) {
            canAdd = super.add(bitmap);
        }
        return canAdd;
    }

    @Override
    public void add(int index, Bitmap bitmap) {
        // We can only add element with index near limit
        if (this.size() < mNumberUrlsLimit && index < (mNumberUrlsLimit - 1)) {
            super.add(index, bitmap);
        }
    }

    @Override
    public boolean addAll(Collection<? extends Bitmap> collection) {
        boolean canAdd = false;
        if (((this.size() + collection.size()) <= mNumberUrlsLimit)) {
            canAdd = super.addAll(collection);
        }
        return canAdd;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Bitmap> collection) {
        boolean canAdd = false;
        if (((this.size() + collection.size()) <= mNumberUrlsLimit) && index < (mNumberUrlsLimit - 1)) {
            canAdd = super.addAll(index, collection);
        }
        return canAdd;
    }

    @Override
    public Bitmap remove(int index) {
        Bitmap bitmap = null;
        if (index < mNumberUrlsLimit) {
            bitmap = super.remove(index);
        }
        return bitmap;
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        if ((fromIndex < mNumberUrlsLimit) && (toIndex < mNumberUrlsLimit)) {
            super.removeRange(fromIndex, toIndex);
        }
    }

}
