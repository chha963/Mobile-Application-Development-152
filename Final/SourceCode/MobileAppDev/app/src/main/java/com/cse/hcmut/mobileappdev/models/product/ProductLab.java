package com.cse.hcmut.mobileappdev.models.product;

import android.content.Context;

import com.cse.hcmut.mobileappdev.R;

import java.util.ArrayList;

/**
 * Created by dinhn on 3/30/2016.
 */
public class ProductLab {

    private static ProductLab sProductLab;

    private Context mContext;

    private ArrayList<Product> mProductList = null;

    public static ProductLab get(Context context) {
        if (sProductLab == null) {
            sProductLab = new ProductLab(context);
        }
        return sProductLab;
    }

    public static void clear() {
        sProductLab = null;
    }

    private ProductLab(Context context) {
        mContext = context;
        mProductList = new ArrayList<>();
        generateTestcase();
    }

    private void generateTestcase() {
        //public Product(String id, int type, String name, int price, String datePost, String iconId)
        /*
        http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoViApp-ViewPager/GoViApp-PostProduct/post1-new_zps72jgqofr.jpg
        * */
        String url = "";

        String catalogueId = "";

        String briefDescription = mContext.getResources().getString(R.string.test_brief_description);
        String otherDescription = mContext.getResources().getString(R.string.test_other_description);

        String phoneNumber = "0933905863";
        String address = "Khoa Khoa học và Kỹ thuật Máy tính - A3, 268 Lý Thường Kiệt, phường 14, Hồ Chí Minh, Vietnam";

        ArrayList<String> galleryIds = new ArrayList<>();
        galleryIds.add("http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoViApp-Gallery/gallery3_zpsgbeeuln7.jpg");
        galleryIds.add("http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoViApp-Gallery/gallery1_zps0i1j0a4x.jpg");
        galleryIds.add("http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoViApp-Gallery/gallery2_zpsx8guhzh1.jpg");
        galleryIds.add("http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoViApp-Gallery/gallery4_zpsea9jgl2i.jpg");
        galleryIds.add("http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoViApp-Gallery/gallery5_zps49vdmkmn.jpg");

        // Normal product
        for (int i = 0; i < 40; i++) {
            if (i % 2 == 0) {
                url = "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoViApp-ViewPager/GoViApp-PostProduct/post1-new_zps72jgqofr.jpg";
                catalogueId = "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoviApp-DetailProduct/catalogue2-resize-new_zpsdddqlssa.jpg";
            } else {
                url = "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoViApp-ViewPager/GoViApp-PostProduct/post2_zpsuxapzwjy.jpg";
                catalogueId = "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoviApp-DetailProduct/Beautiful-Anime-Girl-Desktop-Background-Wallpapers_zpsmi4mbex7.jpg";
            }
            Product p = new Product(
                    String.valueOf(i),
                    i % 2,
                    "Sản phẩm #" + i,
                    "Người #" + i,
                    (i * 1000),
                    ((i % 8) + " Ngày"),
                    briefDescription,
                    otherDescription,
                    phoneNumber,
                    address,
                    catalogueId,
                    url,
                    galleryIds
            );
            mProductList.add(p);
        }

        // Account product

        String[] urlsBuy = {
                "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoviApp-PersonalInfo/Buy/icon_personal_buy_1_zpstwmuqdyy.png",
                "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoviApp-PersonalInfo/Buy/icon_personal_buy_4_zpschclbhcq.png",
                "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoviApp-PersonalInfo/Buy/icon_personal_buy_3_zpsrk6pf5xc.png",
                "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoviApp-PersonalInfo/Buy/icon_personal_buy_2_zpsgpqbhgxa.png"
        };

        String[] urlsSell = {
                "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoviApp-PersonalInfo/Sell/icon_personal_sell_3_zpse3lipgbd.png",
                "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoviApp-PersonalInfo/Sell/icon_personal_sell_1_zpsaxslrmyn.png",
                "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoviApp-PersonalInfo/Sell/icon_personal_sell_2_zpsj444mxof.png",
                "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoviApp-PersonalInfo/Sell/icon_personal_sell_4_zpszpntq1bd.png"
        };

        for (int i = 40; i < 48; i++) {
            if (i % 2 == 0) {
                url = urlsBuy[(i - 40) / 2];
                catalogueId = "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoviApp-DetailProduct/catalogue2-resize-new_zpsdddqlssa.jpg";
            } else {
                url = urlsSell[(i - 40) / 2];
                catalogueId = "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoviApp-DetailProduct/Beautiful-Anime-Girl-Desktop-Background-Wallpapers_zpsmi4mbex7.jpg";
            }
            Product p = new Product(
                    String.valueOf(i),
                    i % 2,
                    "Sản phẩm #" + i,
                    "Người #" + i,
                    (i * 1000),
                    ((i % 8) + " Ngày"),
                    briefDescription,
                    otherDescription,
                    phoneNumber,
                    address,
                    catalogueId,
                    url,
                    galleryIds
            );
            mProductList.add(p);
        }

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

    public ArrayList<Product> getProductListFilteredByType(int type) {
        ArrayList<Product> listProduct = ProductLab.get(mContext).getProductList();
        ArrayList<Product> filteredList = new ArrayList<>();
        for (Product p : listProduct) {
            if (p.getType() == type) {
                filteredList.add(p);
            }
        }
        return filteredList;
    }
}
