package com.cse.hcmut.mobileappdev.utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

/**
 * Created by dinhn on 3/6/2016.
 */
public class MyUtils {
    /*
    Check if ImageView had "real" content image
     */
    public static boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable) drawable).getBitmap() != null;
        }
        return hasImage;
    }

    public static String addSeparatorEveryThreeCharacterFromLast(String numberString, String separator) {
        StringBuilder strB = new StringBuilder();
        strB.append(numberString);
        int Three = 0;

        for (int i = (numberString.length() - 1); i > 0; i--) {
            Three++;
            if (Three == 3) {
                strB.insert(i, separator);
                Three = 0;
            }
        }
        return strB.toString();
    }// end Spacer()

    public static String addSeparatorEveryThreeCharacterFromBegin(String numberString, String separator) {
        StringBuilder strB = new StringBuilder();
        strB.append(numberString);
        int Three = 0;
        int length = numberString.length();
        for (int i = 0; i < length; i++) {
            Three++;
            if (Three == 3) {
                strB.insert(i + 1, separator);
                i++;
                Three = 0;
            }
        }
        return strB.toString();
    }// end Spacer()

    public static void showFeatureIsDevelopingDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Tính năng này đang được phát triển")
                .setMessage("Chúng tôi đang phát triển tính năng này, xin chờ phiên bản cập nhật tiếp theo")
                .setPositiveButton("OK", null)
                .show();
    }
//    public static <T> T swap (T... args) {
//        return args[0];
//    }
}
