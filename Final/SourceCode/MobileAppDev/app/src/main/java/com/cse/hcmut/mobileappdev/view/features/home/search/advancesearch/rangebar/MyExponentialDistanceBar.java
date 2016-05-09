package com.cse.hcmut.mobileappdev.view.features.home.search.advancesearch.rangebar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.TypedValue;

import com.appyvet.rangebar.PinView;

/**
 * Created by dinhn on 4/19/2016.
 */
public class MyExponentialDistanceBar {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    private static final float INCREASE_TICK_MIN_FACTOR = 0.4f;

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    private final Paint mBarPaint;

    private final Paint mTickPaint;

    // Left-coordinate of the horizontal bar.
    private final float mLeftX;

    private final float mRightX;

    private final float mY;

    private int mNumSegments;

    private float mTickDistance;

    private final float mTickHeight;

    private float mTickXCoordinateArray[];

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    /**
     * Bar constructor
     *
     * @param ctx          the context
     * @param x            the start x co-ordinate
     * @param y            the y co-ordinate
     * @param length       the length of the bar in px
     * @param tickCount    the number of ticks on the bar
     * @param tickHeightDP the height of each tick
     * @param tickColor    the color of each tick
     * @param barWeight    the weight of the bar
     * @param barColor     the color of the bar
     */
    public MyExponentialDistanceBar(Context ctx,
                                    float x,
                                    float y,
                                    float length,
                                    int tickCount,
                                    float tickHeightDP,
                                    int tickColor,
                                    float barWeight,
                                    int barColor) {

        mLeftX = x;
        mRightX = x + length;
        mY = y;

        mNumSegments = tickCount - 1;
        updateTickIndexArrayCoordinateX();

        mTickHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                tickHeightDP,
                ctx.getResources().getDisplayMetrics());

        // Initialize the paint.
        mBarPaint = new Paint();
        mBarPaint.setColor(barColor);
        mBarPaint.setStrokeWidth(barWeight);
        mBarPaint.setAntiAlias(true);
        mTickPaint = new Paint();
        mTickPaint.setColor(tickColor);
        mTickPaint.setStrokeWidth(barWeight);
        mTickPaint.setAntiAlias(true);
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    /**
     * Draws the bar on the given Canvas.
     *
     * @param canvas Canvas to draw on; should be the Canvas passed into {#link
     *               View#onDraw()}
     */
    public void draw(Canvas canvas) {
        mBarPaint.setStrokeCap(Paint.Cap.ROUND);
        mBarPaint.setStrokeWidth(10);
        canvas.drawLine(mLeftX, mY, mRightX, mY, mBarPaint);
    }

    /**
     * Draws the tick marks on the bar.
     *
     * @param canvas Canvas to draw on; should be the Canvas passed into {#link
     *               View#onDraw()}
     */
    public void drawTicks(Canvas canvas) {

        // Loop through and draw each tick (except final tick).
        for (int i = 0; i < mNumSegments; i++) {
            float distance = mTickXCoordinateArray[i];
            final float x = distance + mLeftX;
            canvas.drawCircle(x, mY, mTickHeight, mTickPaint);
        }
        // Draw final tick. We draw the final tick outside the loop to avoid any
        // rounding discrepancies.
        canvas.drawCircle(mRightX, mY, mTickHeight, mTickPaint);
    }

    public float getDistanceFrom(int index) {

        float distance = 0;

        if (index > 0) {

            int indexExcludeFirstZeroElement = index - 1;
            int indexGroup = 0;
            int indexInCurrentGroup = 0;

            int numberOfItemOneSegment = (mNumSegments > 4) ? ((mNumSegments) / 5) : 1;

            if (index > (numberOfItemOneSegment * 5)) {
                indexGroup = 5;
                indexInCurrentGroup = ((indexExcludeFirstZeroElement) % indexGroup) + 1;
            } else {
                indexGroup = (index - 1) / numberOfItemOneSegment;
                indexInCurrentGroup = ((indexExcludeFirstZeroElement) % numberOfItemOneSegment) + 1;
            }

            switch (indexGroup) {
                case 0:
                    distance = (1.4f * indexInCurrentGroup) * mTickDistance;
                    break;
                case 1:
                    distance = (1.4f * numberOfItemOneSegment + 1.2f * indexInCurrentGroup) * mTickDistance;
                    break;
                case 2:
                    distance = (2.6f * numberOfItemOneSegment + 1.0f * indexInCurrentGroup) * mTickDistance; // 1.4 = 0.6 + 0.8
                    break;
                case 3:
                    distance = (3.6f * numberOfItemOneSegment + 0.8f * indexInCurrentGroup) * mTickDistance;
                    break;
                case 4:
                    distance = (4.4f * numberOfItemOneSegment + 0.6f * indexInCurrentGroup) * mTickDistance;
                    break;
                case 5:
                    distance = (5.0f * numberOfItemOneSegment + INCREASE_TICK_MIN_FACTOR * indexInCurrentGroup) * mTickDistance;
                    break;
            }
        }
        return distance;
    }

    public int getTickIndexFromArrayCoordinateX(float x) {
        int selectIndex = 0;

        float checkRightXLeft = (mLeftX + (mLeftX + mTickXCoordinateArray[1])) / 2.0f;

        float checkLeftXRight = ((mLeftX + mTickXCoordinateArray[mNumSegments - 1]) + mRightX) / 2.0f;

        if (x <= checkRightXLeft) {
            selectIndex = 0;
        } else if (x >= checkLeftXRight) {
            selectIndex = mNumSegments;
        } else {
            float localX = x - mLeftX;
            int length = mTickXCoordinateArray.length - 1;
            for (int i = 1; i < length; i++) {
                float checkXRight = (mTickXCoordinateArray[i] + mTickXCoordinateArray[i + 1]) / 2.0f;
                float checkXLeft = (mTickXCoordinateArray[i] + mTickXCoordinateArray[i - 1]) / 2.0f;
                if (localX < checkXRight && localX > checkXLeft) {
                    selectIndex = i;
                    break;
                }
            }
        }
        return selectIndex;
    }

    private void updateTickIndexArrayCoordinateX() {

        if (mTickXCoordinateArray == null) { // first init
            mTickXCoordinateArray = new float[mNumSegments + 1];
        } else { // If somehow we change mNumSegments, re init it
            if (mTickXCoordinateArray.length != (mNumSegments + 1)) {
                mTickXCoordinateArray = new float[mNumSegments + 1];
            }
        }

        // We divide to 5 normal group (and 1 last group)  so number of pin remainder < 5
        int numberOfPinInLastGroup = (mNumSegments) % 5;

        final float barSize = mRightX - mLeftX;

        // length / (mNumSegments - numberOfPinInLastGroup + numberOfPinInLastGroup * 1.6)
        mTickDistance = barSize / (mNumSegments + (INCREASE_TICK_MIN_FACTOR - 1.0f) * numberOfPinInLastGroup);

        // update
        for (int i = 0; i < mNumSegments; i++) {
            mTickXCoordinateArray[i] = getDistanceFrom(i);
        }
        mTickXCoordinateArray[mNumSegments] = barSize;
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

    /**
     * Get the x-coordinate of the left edge of the bar.
     *
     * @return x-coordinate of the left edge of the bar
     */
    public float getLeftX() {
        return mLeftX;
    }

    /**
     * Get the x-coordinate of the right edge of the bar.
     *
     * @return x-coordinate of the right edge of the bar
     */
    public float getRightX() {
        return mRightX;
    }

    /**
     * Gets the x-coordinate of the nearest tick to the given x-coordinate.
     *
     * @param thumb the thumb to find the nearest tick for
     * @return the x-coordinate of the nearest tick
     */
    public float getNearestTickCoordinate(PinView thumb) {

        final int nearestTickIndex = getNearestTickIndex(thumb);

        // if this is mRight, return it
        if (nearestTickIndex == mNumSegments) {
            return mRightX;
        } else {
            return mLeftX + mTickXCoordinateArray[nearestTickIndex];
        }
    }

    /**
     * Gets the zero-based index of the nearest tick to the given thumb.
     *
     * @param thumb the Thumb to find the nearest tick for
     * @return the zero-based index of the nearest tick
     */
    public int getNearestTickIndex(PinView thumb) {
        return getTickIndexFromArrayCoordinateX(thumb.getX());
    }

    /**
     * Set the number of ticks that will appear in the RangeBar.
     *
     * @param tickCount the number of ticks
     */
    public void setTickCount(int tickCount) {

        final float barLength = mRightX - mLeftX;

        mNumSegments = tickCount - 1;
        updateTickIndexArrayCoordinateX();
    }


}
