package com.cse.hcmut.mobileappdev.base.widgets.scrollview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dinhn on 3/25/2016.
 */
public class MyScrollView extends ScrollView {

    List<View> mInterceptScrollViews = new ArrayList<View>();

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addInterceptScrollView(View view) {
        mInterceptScrollViews.add(view);
    }

    public void removeInterceptScrollView(View view) {
        mInterceptScrollViews.remove(view);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        // check if we have any views that should use their own scrolling
        if (mInterceptScrollViews.size() > 0) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Rect bounds = new Rect();


            for (View view : mInterceptScrollViews) {
                view.getHitRect(bounds);

                if (bounds.contains(x, y)) {
                    //were touching a view that should intercept scrolling
                    return false;
                }
            }
        }

        return super.onInterceptTouchEvent(event);
    }
}
