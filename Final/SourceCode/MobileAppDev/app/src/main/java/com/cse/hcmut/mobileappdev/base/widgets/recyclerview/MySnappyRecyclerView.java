package com.cse.hcmut.mobileappdev.base.widgets.recyclerview;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_FLING;
import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;

/**
 * Created by dinhn on 4/4/2016.
 */
public final class MySnappyRecyclerView extends RecyclerView {
    private boolean mSnapEnabled = false;
    private boolean mUserScrolling = false;
    private boolean mScrolling = false;
    private int mScrollState;
    private long lastScrollTime = 0;
    private Handler mHandler = new Handler();

    private boolean mScaleUnfocusedViews = false;

    private final static int MINIMUM_SCROLL_EVENT_OFFSET_MS = 20;

    private static int mFirstItemMargin = 0;
    private static int mMarginBetweenViews = 0;
    private static int EXTERNAL_CHECK_CENTER = 18;


    private static final int NO_SCALE_MARGIN_BETWEEN_VIEWS = 6;
    private static final int NO_SCALE_EXTERNAL_SCROLL = 10;
    private static final int NO_SCALE_EXTERNAL_CHECK_CENTER = 9;

    private final static float FRICTION_FLING = 0.3f;

    public MySnappyRecyclerView(Context context) {
        super(context);

//        mMarginBetweenViews = (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//                NO_SCALE_MARGIN_BETWEEN_VIEWS,
//                context.getResources().getDisplayMetrics()
//        );
//
//        EXTERNAL_SCROLL = (int)TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//                NO_SCALE_EXTERNAL_SCROLL,
//                context.getResources().getDisplayMetrics()
//        );
//
//        EXTERNAL_CHECK_CENTER = (int)TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//                NO_SCALE_EXTERNAL_CHECK_CENTER,
//                context.getResources().getDisplayMetrics()
//        );

    }

    public MySnappyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);

//        mMarginBetweenViews = (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//                NO_SCALE_MARGIN_BETWEEN_VIEWS,
//                context.getResources().getDisplayMetrics()
//        );
//
//        EXTERNAL_SCROLL = (int)TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//                NO_SCALE_EXTERNAL_SCROLL,
//                context.getResources().getDisplayMetrics()
//        );
//
//        EXTERNAL_CHECK_CENTER = (int)TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//                NO_SCALE_EXTERNAL_CHECK_CENTER,
//                context.getResources().getDisplayMetrics()
//        );

    }

    /**
     * Begin a standard fling with an initial velocity along each axis in pixels per second.
     * If the velocity given is below the system-defined minimum this method will return false
     * and no fling will occur.
     *
     * @param velocityX Initial horizontal velocity in pixels per second
     * @param velocityY Initial vertical velocity in pixels per second
     * @return true if the fling was started, false if the velocity was too low to fling or
     * LayoutManager does not support scrolling in the axis fling is issued.
     * @see LayoutManager#canScrollVertically()
     * @see LayoutManager#canScrollHorizontally()
     */
    @Override
    public boolean fling(int velocityX, int velocityY) {

        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager castedLinearLayoutManager = (LinearLayoutManager) layoutManager;
            int orientation = castedLinearLayoutManager.getOrientation();
            switch (orientation) {
                case VERTICAL:
                    velocityY = (int) (velocityY * FRICTION_FLING);
                    break;
                case HORIZONTAL:
                    velocityX = (int) (velocityX * FRICTION_FLING);
                    break;
            }
        }

        return super.fling(velocityX, velocityY);
    }

    /**
     * Enable snapping behaviour for this recyclerView
     *
     * @param enabled enable or disable the snapping behaviour
     */
    public void setSnapEnabled(boolean enabled) {
        mSnapEnabled = enabled;

        if (enabled) {
            addOnLayoutChangeListener(new OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    if (left == oldLeft && right == oldRight && top == oldTop && bottom == oldBottom) {
                        removeOnLayoutChangeListener(this);
                        updateViews();
                        if (!mUserScrolling) {
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    scrollToView(getChildAt(0));
                                }
                            }, 20);
                        }
                    }
                }
            });

            addOnScrollListener(new OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    updateViews();
                    super.onScrolled(recyclerView, dx, dy);
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);

                    /** if scroll is caused by a touch (scroll touch, not any touch) **/
                    if (newState == SCROLL_STATE_TOUCH_SCROLL) {
                        /** if scroll was initiated already, this is not a user scrolling, but probably a tap, else set userScrolling **/
                        if (!mScrolling) {
                            mUserScrolling = true;
                        }
                    } else if (newState == SCROLL_STATE_IDLE) {
                        if (mUserScrolling) {
                            scrollToTargetView(getCenterView());
                        }

                        mUserScrolling = false;
                        mScrolling = false;
                    } else if (newState == SCROLL_STATE_FLING) {
                        mScrolling = true;
                    }

                    mScrollState = newState;
                }
            });
        } else {
            addOnScrollListener(null);
        }
    }

    /**
     * Enable snapping behaviour for this recyclerView
     *
     * @param enabled             enable or disable the snapping behaviour
     * @param scaleUnfocusedViews downScale the views which are not focused based on how far away they are from the center
     */
    public void setSnapEnabled(boolean enabled, boolean scaleUnfocusedViews) {
        this.mScaleUnfocusedViews = scaleUnfocusedViews;
        setSnapEnabled(enabled);
    }

    private void updateViews() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            setMarginsForChild(child);

            if (mScaleUnfocusedViews) {
                float percentage = getPercentageFromCenter(child);
                float scale = 1f - (0.7f * percentage);

                child.setScaleX(scale);
                child.setScaleY(scale);
            }
        }
    }

    /**
     * Adds the margins to a childView so a view will still center even if it's only a single child
     *
     * @param child childView to set margins for
     */
    private void setMarginsForChild(View child) {
        int lastItemIndex = getLayoutManager().getItemCount() - 1;
        int childIndex = getChildLayoutPosition(child);

        int startMargin = childIndex == 0 ? mFirstItemMargin : 0;
        int endMargin = 0;

        /** if sdk minimum level is 17, set RTL margins **/
        if (Build.VERSION.SDK_INT >= 17) {
            ((ViewGroup.MarginLayoutParams) child.getLayoutParams()).setMarginStart(startMargin);
            ((ViewGroup.MarginLayoutParams) child.getLayoutParams()).setMarginEnd(endMargin);
        }

        ((ViewGroup.MarginLayoutParams) child.getLayoutParams()).setMargins(startMargin, 0, endMargin, 0);
        child.requestLayout();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (!mSnapEnabled)
            return super.dispatchTouchEvent(event);

        long currentTime = System.currentTimeMillis();

        /** if touch events are being spammed, this is due to user scrolling right after a tap,
         * so set userScrolling to true **/
        if (mScrolling && mScrollState == SCROLL_STATE_TOUCH_SCROLL) {
            if ((currentTime - lastScrollTime) < MINIMUM_SCROLL_EVENT_OFFSET_MS) {
                mUserScrolling = true;
            }
        }

        lastScrollTime = currentTime;
//
//        if (!mUserScrolling) {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                if (targetView != getCenterView()) {
//                    scrollToView(targetView);
//                    return true;
//                }
//            }
//        }
//        float a = event.getX();

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
//        if (!mSnapEnabled)
//            return super.onInterceptTouchEvent(e);
//
//        View targetView = getChildClosestToPosition((int) e.getX());
//
//        if (targetView != getCenterView()) {
//            return false;
//        }

        return super.onInterceptTouchEvent(e);
    }

    private View getChildClosestToPosition(int x) {
        if (getChildCount() <= 0)
            return null;

        int itemWidth = getChildAt(0).getMeasuredWidth();

        int closestX = 9999;
        View closestChild = null;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            int childCenterX = ((int) child.getX() + itemWidth / 2 - 2 * mMarginBetweenViews);
            int xDistance = childCenterX - x;

            /** if child center is closer than previous closest, set it as closest  **/
            if (Math.abs(xDistance) < Math.abs(closestX)) {
                closestX = xDistance;
                closestChild = child;
            }
        }

        return closestChild;
    }

    private View getCenterView() {
        return getChildClosestToPosition(getMeasuredWidth() / 2);
    }

    private void scrollToView(View child) {
        if (child == null)
            return;

        stopScroll();

        int scrollDistance = getScrollDistance(child);

        if (scrollDistance != 0)
            smoothScrollBy(scrollDistance, 0);

    }

    private void scrollToTargetView(View child) {
        if (child == null)
            return;

        stopScroll();

        int scrollDistance = getScrollDistance(child);

        boolean isRecyclerViewIncludeLastChildVisible = false;
        boolean isRecyclerViewLastChildCompletelyVisible = false;
        boolean isRecyclerViewIncludeFourthLastChildFirstVisible = false;

        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager castedLinearLayoutManager = (LinearLayoutManager) layoutManager;
            Adapter adapter = getAdapter();
            if (adapter != null) {
                int lastIndexItems = adapter.getItemCount() - 1;
                if (castedLinearLayoutManager.findLastVisibleItemPosition() == lastIndexItems) {
                    isRecyclerViewIncludeLastChildVisible = true;
                }

                if (castedLinearLayoutManager.findLastCompletelyVisibleItemPosition() == lastIndexItems) {
                    isRecyclerViewLastChildCompletelyVisible = true;
                }

                if (castedLinearLayoutManager.findFirstVisibleItemPosition() == (lastIndexItems - 3)) {
                    isRecyclerViewIncludeFourthLastChildFirstVisible = true;
                }

            }
        }

        if (isRecyclerViewIncludeLastChildVisible && !isRecyclerViewIncludeFourthLastChildFirstVisible) {
            if (isRecyclerViewLastChildCompletelyVisible) {
                smoothScrollBy(-1, 0);
            } else {
                if (scrollDistance != 0) {
                    smoothScrollBy(scrollDistance, 0);
                }
            }
        } else {
            if (scrollDistance != 0) {
                smoothScrollBy(scrollDistance, 0);
            }
        }

//        if (child != nearLastChild) {
//            if (scrollDistance != 0)
//                smoothScrollBy(scrollDistance, 0);
//        } else {
//            if (scrollDistance < 0) {
//                smoothScrollBy(-1, 0);
//            } else if (scrollDistance > 0) {
//                smoothScrollBy(scrollDistance, 0);
//            }
//        }
    }

    private int getScrollDistance(View child) {
        int itemWidth = getChildAt(0).getMeasuredWidth();
        int centerX = getMeasuredWidth() / 2;

        int childCenterX = ((int) child.getX() + (itemWidth / 2)) - 2 * mMarginBetweenViews;

        return childCenterX - centerX;
    }

    private float getPercentageFromCenter(View child) {
        float centerX = (getMeasuredWidth() / 2.0f);
        float childCenterX = child.getX() + (child.getWidth() / 2);

        float offSet = Math.max(centerX, childCenterX) - Math.min(centerX, childCenterX);

        int maxOffset = (getMeasuredWidth() / 2) + child.getWidth();

        return (offSet / maxOffset);
    }

    public boolean isChildCenterView(View child) {
        return child == getCenterView();
    }

    public int getHorizontalScrollOffset() {
        return computeHorizontalScrollOffset();
    }

    public int getVerticalScrollOffset() {
        return computeVerticalScrollOffset();
    }

    public void smoothUserScrollBy(int x, int y) {
        mUserScrolling = true;
        smoothScrollBy(x, y);
    }

    public void setMarginBetweenViews(int marginBetweenViews) {
        mMarginBetweenViews = marginBetweenViews;
    }

    public void setFirstMarginView(int firstMarginView) {
        mFirstItemMargin = firstMarginView;
    }

    public void scrollLayoutToPosition(int position, int numberOfItems) {
        super.scrollToPosition(position);

        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager ll = ((LinearLayoutManager) layoutManager);
            if (position == 0) {
                mUserScrolling = false;
                smoothScrollToPosition(0);
            } else {
                mUserScrolling = true;
                ll.scrollToPositionWithOffset(position, mMarginBetweenViews);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
    }

}
