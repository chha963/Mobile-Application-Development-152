package com.cse.hcmut.mobileappdev.models.product;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by dinhn on 3/28/2016.
 */
public class TopProductLab {

    private static TopProductLab sTopProductLab;

    private Context mContext;

    private ArrayList<Product> mProductList = null;

    public static TopProductLab get(Context context) {
        if (sTopProductLab == null) {
            sTopProductLab = new TopProductLab(context);
        }
        return sTopProductLab;
    }

    public static void clear() {
        sTopProductLab = null;
    }

    private TopProductLab(Context context) {
        mContext = context;
        mProductList = new ArrayList<>();
        generateTestcase();
    }

    private void generateTestcase() {
        //Product(String id, int type,String iconId, String name, String description,  int price)
        /*
        "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoViApp-ViewPager/image1_zpsq55dnvug.png"
        "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoViApp-ViewPager/image2_zpspmggl5tf.png"
        "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoViApp-ViewPager/image3_zpshoclglx0.png"
        "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoViApp-ViewPager/image4_zpsvjsrq1pw.png"
        "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoViApp-ViewPager/image5_zpsbjxp2l9r.png"
        * */
        Product p1 = new Product("1", Product.INDEX_BUY_PRODUCT,
                "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoViApp-ViewPager/image1_zpsq55dnvug.png",
                "Sản phẩm #1", "Ghi chú của sản phẩm 1", 10000);

        Product p2 = new Product("2", Product.INDEX_BUY_PRODUCT,
                "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoViApp-ViewPager/image5_zpsbjxp2l9r.png",
                "Sản phẩm #2", "Ghi chú của sản phẩm 2", 20000);

        Product p3 = new Product("3", Product.INDEX_SELL_PRODUCT,
                "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoViApp-ViewPager/image4_zpsvjsrq1pw.png",
                "Sản phẩm #3", "Ghi chú của sản phẩm 3", 30000);

        Product p4 = new Product("4", Product.INDEX_BUY_PRODUCT,
                "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoViApp-ViewPager/image2_zpspmggl5tf.png",
                "Sản phẩm #4", "Ghi chú của sản phẩm 4", 40000);

        Product p5 = new Product("5", Product.INDEX_SELL_PRODUCT,
                "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoViApp-ViewPager/image3_zpshoclglx0.png",
                "Sản phẩm #5", "Ghi chú của sản phẩm 5", 50000);

        mProductList.add(p1);
        mProductList.add(p2);
        mProductList.add(p3);
        mProductList.add(p4);
        mProductList.add(p5);
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public ArrayList<Product> getProductList() {
        return mProductList;
    }

    public Product getProduct(String id) {
        Product productRetrieved = null;
        for (Product p : mProductList) {
            if (p.getId().equals(id)) {
                productRetrieved = p;
                break;
            }
        }
        return productRetrieved;
    }

    public void insertProduct(Product p) {
        if (mProductList != null) {
            mProductList.add(p);
        }
    }

    public boolean removeProduct(Product p) {
        boolean succeed = false;
        if (mProductList != null) {
            succeed = mProductList.remove(p);
        }
        return succeed;
    }
}
