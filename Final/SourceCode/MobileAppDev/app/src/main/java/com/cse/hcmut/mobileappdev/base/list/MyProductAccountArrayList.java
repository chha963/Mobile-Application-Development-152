package com.cse.hcmut.mobileappdev.base.list;

import com.cse.hcmut.mobileappdev.models.product.Product;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dinhn on 4/28/2016.
 */
public class MyProductAccountArrayList extends ArrayList<Product> {

    // default only contain 5 products
    private static int mNumberProductLimit = 5;

    public MyProductAccountArrayList() {
        super(mNumberProductLimit);
    }

    public MyProductAccountArrayList(int numberProductLimit) {
        super(numberProductLimit);
        mNumberProductLimit = numberProductLimit;
    }

    @Override
    public Product get(int index) {
        Product product = null;
        if (index < mNumberProductLimit) {
            product = super.get(index);
        }
        return product;
    }

    @Override
    public Product set(int index, Product product) {
        Product tmpProduct = get(index);
        if (index < mNumberProductLimit) {
            super.set(index, tmpProduct);
        }
        return tmpProduct;
    }

    @Override
    public boolean add(Product product) {
        boolean canAdd = false;
        if (this.size() < mNumberProductLimit) {
            canAdd = super.add(product);
        }
        return canAdd;
    }

    @Override
    public void add(int index, Product product) {
        // We can only add element with index near limit
        if (this.size() < mNumberProductLimit && index < (mNumberProductLimit - 1)) {
            super.add(index, product);
        }
    }

    @Override
    public boolean addAll(Collection<? extends Product> collection) {
        boolean canAdd = false;
        if (((this.size() + collection.size()) <= mNumberProductLimit)) {
            canAdd = super.addAll(collection);
        }
        return canAdd;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Product> collection) {
        boolean canAdd = false;
        if (((this.size() + collection.size()) <= mNumberProductLimit) && index < (mNumberProductLimit - 1)) {
            canAdd = super.addAll(index, collection);
        }
        return canAdd;
    }

    @Override
    public Product remove(int index) {
        Product product = null;
        if (index < mNumberProductLimit) {
            product = super.remove(index);
        }
        return product;
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        if ((fromIndex < mNumberProductLimit) && (toIndex < mNumberProductLimit)) {
            super.removeRange(fromIndex, toIndex);
        }
    }

}
