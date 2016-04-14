package com.cse.hcmut.mobileappdev.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.models.Product.Product;

/**
 * Created by dinhn on 4/5/2016.
 */
public class ResourceUtils {

    public static String[] getMainSectionTitleArray(Context context) {
        Resources res = context.getResources();
        return res.getStringArray(R.array.section_title_name_array);
    }

    public static int getNumberOfMainSection(Context context) {
        return getMainSectionTitleArray(context).length;
    }

    public static String[] getCategoryTitleArray(Context context) {
        Resources res = context.getResources();
        return res.getStringArray(R.array.category_title_array);
    }

    public static int getDefaultColorTabLayout(Context context) {
        return ContextCompat.getColor(context, R.color.tab_layout_default_background);
    }

    public static int getAnotherColorTabLayout(Context context) {
        return ContextCompat.getColor(context, R.color.tab_layout_another_background);
    }

    public static int getButtonDrawableForProductType(Context context, int index) {
        int drawableIndex = 0;
        switch (index) {
            case Product.INDEX_BUY_PRODUCT:
                drawableIndex = R.drawable.bg_default_button_product_type;
                break;
            case Product.INDEX_SELL_PRODUCT:
                drawableIndex = R.drawable.bg_another_button_product_type;
                break;
            default:
                drawableIndex = R.drawable.bg_default_button_product_type;
                break;
        }
        return drawableIndex;
    }

    public static int getColorForProductType(Context context, int index) {
        int color = 0;
        switch (index) {
            case Product.INDEX_BUY_PRODUCT:
                color = getDefaultColorTabLayout(context);
                break;
            case Product.INDEX_SELL_PRODUCT:
                color = getAnotherColorTabLayout(context);
                break;
            default:
                color = getDefaultColorTabLayout(context);
                break;
        }
        return color;
    }
}
