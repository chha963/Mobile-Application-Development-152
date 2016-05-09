package com.cse.hcmut.mobileappdev.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.models.product.Product;
import com.cse.hcmut.mobileappdev.view.features.home.main.productlist.horizontalscroll.content.ProductListMainSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.home.search.suggestion.MapSuggestionsBuilder;

/**
 * Created by dinhn on 4/5/2016.
 */
public class ResourceUtils {

    public static String[] makeNewStringArrayWithPrefix(String[] input, String prefix) {
        int length = input.length;
        String[] output = new String[length];
        for (int index = 0; index < length; index++) {
            output[index] = prefix + input[index];
        }
        return output;
    }

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

    public static int getDefaultGreenColorTabLayout(Context context) {
        return ContextCompat.getColor(context, R.color.tab_layout_default_green_background);
    }

    public static int getAnotherBlueColorTabLayout(Context context) {
        return ContextCompat.getColor(context, R.color.tab_layout_another_blue_background);
    }

    public static int getAnotherPinkColorTabLayout(Context context) {
        return ContextCompat.getColor(context, R.color.tab_layout_another_pink_background);
    }


    public static int getButtonDrawableIdWith(int productType) {
        int drawableId = 0;
        switch (productType) {
            case Product.INDEX_BUY_PRODUCT:
                drawableId = R.drawable.bg_default_button_product_type;
                break;
            case Product.INDEX_SELL_PRODUCT:
                drawableId = R.drawable.bg_another_button_product_type;
                break;
            default:
                drawableId = R.drawable.bg_default_button_product_type;
                break;
        }
        return drawableId;
    }

    public static Drawable getFeatureDrawablePersonalInfoWith(Context context, int tabPosition) {
        int drawableId = 0;
        switch (tabPosition) {

            case 0:
            case 1:
                drawableId = R.drawable.ic_plus_icon;
                break;

            case 2:
                drawableId = R.drawable.ic_bottom_menu_list;
                break;

            default:
                drawableId = R.drawable.ic_plus_icon;
                break;

        }
        return ContextCompat.getDrawable(context, drawableId);
    }

    public static Drawable getAdvanceSearchIconDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.ic_advance_search_default);
    }

    public static Drawable getMapSuggestionIconDrawable(Context context, MapSuggestionsBuilder.IconType suggestionType) {
        int resId = 0;
        switch (suggestionType) {
            case FOOD:
                resId = R.drawable.ic_buy_food;
                break;
            case CLOTH:
                resId = R.drawable.ic_buy_cloth;
                break;
            case COSMETIC:
                resId = R.drawable.ic_buy_perfume;
                break;
            case ELECTRONIC:
                resId = R.drawable.ic_buy_electronic;
                break;
            case STATIONERY:
                resId = R.drawable.ic_buy_office;
                break;
            default:
                resId = R.drawable.ic_navigation_drawer_item;
                break;
        }
        return ContextCompat.getDrawable(context, resId);
    }

    public static int getProductTypeFromColor(Context context, int color) {
        int productType = 0;

        if (color == getDefaultGreenColorTabLayout(context)) {
            productType = Product.INDEX_BUY_PRODUCT;
        } else if (color == getAnotherBlueColorTabLayout(context)) {
            productType = Product.INDEX_SELL_PRODUCT;
        }

        return productType;
    }

    public static int getColorForProductType(Context context, int index) {
        int color = 0;
        switch (index) {
            case Product.INDEX_BUY_PRODUCT:
                color = getDefaultGreenColorTabLayout(context);
                break;
            case Product.INDEX_SELL_PRODUCT:
                color = getAnotherBlueColorTabLayout(context);
                break;
            default:
                color = getDefaultGreenColorTabLayout(context);
                break;
        }
        return color;
    }

    public static int getColorForPersonalInfoTabPosition(Context context, int position) {
        int color = 0;
        switch (position) {
            case 0:
                color = getDefaultGreenColorTabLayout(context);
                break;
            case 1:
                color = getAnotherBlueColorTabLayout(context);
                break;
            case 2:
                color = getAnotherPinkColorTabLayout(context);
                break;
            default:
                color = getDefaultGreenColorTabLayout(context);
                break;
        }
        return color;
    }

    public static int getColorForSectionType(Context context, ProductListMainSingleFragment.SectionItem sectionItem) {
        int color = 0;
        switch (sectionItem) {
            case SECTION_BUY:
                color = getDefaultGreenColorTabLayout(context);
                break;
            case SECTION_SELL:
                color = getAnotherBlueColorTabLayout(context);
                break;
            default:
                color = getDefaultGreenColorTabLayout(context);
                break;
        }
        return color;
    }

    public static int getColorForSectionTitleString(Context context, final String sectionString) {
        int color = getDefaultGreenColorTabLayout(context);

        ProductListMainSingleFragment.SectionItem[] sectionArray = ProductListMainSingleFragment.SectionItem.values();
        int lengthSectionItemValues = sectionArray.length;

        for (int i = 0; i < lengthSectionItemValues; i++) {
            if (sectionArray[i].getTitle().equalsIgnoreCase(sectionString)) {
                color = getColorForSectionType(context, sectionArray[i]);
                break;
            }
        }

        return color;
    }
}
