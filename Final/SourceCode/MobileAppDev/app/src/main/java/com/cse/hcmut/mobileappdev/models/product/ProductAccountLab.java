package com.cse.hcmut.mobileappdev.models.product;

import android.content.Context;

import com.cse.hcmut.mobileappdev.base.list.MyProductAccountArrayList;

import java.util.ArrayList;

/**
 * Created by dinhn on 5/1/2016.
 */
public class ProductAccountLab {

    private static final int NUMBER_OF_BUY_PRODUCT_LIMIT = 5;

    private static final int NUMBER_OF_SELL_PRODUCT_LIMIT = 5;

    private static ProductAccountLab sProductAccountLab;

    private Context mContext = null;

    private MyProductAccountArrayList mBuyProductList = null;

    private MyProductAccountArrayList mSellProductList = null;

    private ArrayList<Product> mBookmarkProductList = null;

    public static ProductAccountLab get(Context context) {
        if (sProductAccountLab == null) {
            sProductAccountLab = new ProductAccountLab(context);
        }
        return sProductAccountLab;
    }

    public static void clear() {
        sProductAccountLab = null;
    }

    private ProductAccountLab(Context context) {
        mContext = context;
        mBuyProductList = new MyProductAccountArrayList(NUMBER_OF_BUY_PRODUCT_LIMIT);
        mSellProductList = new MyProductAccountArrayList(NUMBER_OF_SELL_PRODUCT_LIMIT);
        mBookmarkProductList = new ArrayList<>();
        generateTestCase();
    }

    private void generateTestCase() {
        for (int i = 40; i < 48; i++) {
            Product p = ProductLab.get(mContext).getProduct(String.valueOf(i));
            if (i % 2 == 0) {
                mBuyProductList.add(p);
            } else {
                mSellProductList.add(p);
            }
        }
    }

    public MyProductAccountArrayList getBuyProductList() {
        return mBuyProductList;
    }

    public MyProductAccountArrayList getSellProductList() {
        return mSellProductList;
    }

    public ArrayList<Product> getBookmarkProductList() {
        return mBookmarkProductList;
    }

}
