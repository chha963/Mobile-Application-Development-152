package com.cse.hcmut.mobileappdev.utils;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
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

    public static String addSeparatorEveryThreeCharacterFromLast(String numberString, String separator){
        StringBuilder strB = new StringBuilder();
        strB.append(numberString);
        int Three = 0;

        for(int i=numberString.length();i>0;i--){
            Three++;
            if(Three == 3){
                strB.insert(i-1, separator);
                Three = 0;
            }
        }
        return strB.toString();
    }// end Spacer()

    public static String addSeparatorEveryThreeCharacterFromBegin(String numberString, String separator){
        StringBuilder strB = new StringBuilder();
        strB.append(numberString);
        int Three = 0;
        int length = numberString.length();
        for(int i = 0; i <= length; i++){
            Three++;
            if(Three == 3){
                strB.insert(i+1, separator);
                Three = 0;
            }
        }
        return strB.toString();
    }// end Spacer()
}
