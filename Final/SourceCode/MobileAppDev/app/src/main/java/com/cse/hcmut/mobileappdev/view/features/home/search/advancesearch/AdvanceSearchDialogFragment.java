package com.cse.hcmut.mobileappdev.view.features.home.search.advancesearch;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import com.appyvet.rangebar.IRangeBarFormatter;
import com.appyvet.rangebar.RangeBar;
import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseDialogFragment;
import com.cse.hcmut.mobileappdev.utils.MyUtils;
import com.cse.hcmut.mobileappdev.view.features.home.search.advancesearch.rangebar.MyExponentialDistanceRangeBar;

/**
 * Created by dinhn on 4/17/2016.
 */
public class AdvanceSearchDialogFragment extends MyBaseDialogFragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    private static final int CONVERT_TICK_INDEX_TO_VND_CURRENCY = 20000;

    private static final String ARG_MAIN_COLOR = "main_color";

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static AdvanceSearchDialogFragment newInstance(int color) {

        Bundle args = new Bundle();
        args.putInt(ARG_MAIN_COLOR, color);

        AdvanceSearchDialogFragment fragment = new AdvanceSearchDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    protected RangeBar mDateLimitRangeBar;

    protected MyExponentialDistanceRangeBar mPriceLimitRangeBar;

    private int mDateLimitIndexRangeBar = 0;

    private int mMaxPriceIndexRangeBar = 0;

    private int mMinPriceIndexRangeBar = 0;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getMessageLayoutId() {
        return R.layout.layout_message_advance_search;
    }

    @Override
    protected String getTitleString() {
        return "TÌM KIẾM NÂNG CAO";
    }

    @Override
    protected String getPositiveButtonString() {
        return "TÌM KIẾM";
    }

    @Override
    protected String getNegativeButtonString() {
        return "HỦY";
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        Bundle args = getArguments();
        int color = ContextCompat.getColor(getActivity(), R.color.advance_search_default_background);
        if (args != null) {
            color = args.getInt(ARG_MAIN_COLOR);
        }

        View messageView = getMessageLayoutViewInflated();

        if (messageView != null) {
            bindWidgets(messageView);
            bindWidgetsToListener();
            initData();
            setupWidgets(color);
        }
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void bindWidgets(View rootView) {
        mDateLimitRangeBar = (RangeBar) rootView.findViewById(R.id.dateLimitRangeBar_AdvanceSearchMessageLayout);
        mPriceLimitRangeBar = (MyExponentialDistanceRangeBar) rootView.findViewById(R.id.priceLimitRangeBar_AdvanceSearchMessageLayout);
    }

    private void initData() {
        if (!mDateLimitRangeBar.isRangeBar()) {
            mDateLimitIndexRangeBar = mDateLimitRangeBar.getRightIndex();
        }

        int leftPriceIndex = mPriceLimitRangeBar.getLeftIndex();
        int rightPriceIndex = mPriceLimitRangeBar.getRightIndex();
        mMinPriceIndexRangeBar = (leftPriceIndex <= rightPriceIndex) ? leftPriceIndex : rightPriceIndex;
        mMaxPriceIndexRangeBar = (mMinPriceIndexRangeBar == leftPriceIndex) ? rightPriceIndex : leftPriceIndex;
    }

    private void setupWidgets(int color) {
        mDateLimitRangeBar.setConnectingLineColor(color);
        mPriceLimitRangeBar.setConnectingLineColor(color);
        mTitleTextView.setTextColor(color);
        mPositiveButton.setTextColor(color);
        mNegativeButton.setTextColor(color);
    }

    private void bindWidgetsToListener() {
        mDateLimitRangeBar.setFormatter(new IRangeBarFormatter() {
            @Override
            public String format(String s) {
                String formatDateString = getDateLimitStringFrom(s);
                mDateLimitRangeBar.invalidate();
                return formatDateString;
            }
        });

        mDateLimitRangeBar.setPinViewFormatter(new RangeBar.PinViewFormatter() {
            @Override
            public boolean getIsReverseHorizontalLeftThumb() {
                return false;
            }

            @Override
            public boolean getIsReverseHorizontalRightThumb() {
                return false;
            }
        });

        mDateLimitRangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar,
                                              int leftPinIndex,
                                              int rightPinIndex,
                                              String leftPinValue, String rightPinValue) {
                // We use progress bar only to keep track of date
                if (!rangeBar.isRangeBar()) {
                    mDateLimitIndexRangeBar = rightPinIndex;
                }
            }
        });

        mPriceLimitRangeBar.setPinViewFormatter(new MyExponentialDistanceRangeBar.PinViewFormatter() {
            @Override
            public boolean getIsReverseHorizontalLeftThumb() {
                return true;
            }

            @Override
            public boolean getIsReverseHorizontalRightThumb() {
                return false;
            }
        });

        mPriceLimitRangeBar.setFormatter(new IRangeBarFormatter() {
            @Override
            public String format(String s) {
                String formatPriceString = getFormatPriceStringFrom(s);
                mPriceLimitRangeBar.invalidate();
                return formatPriceString;
            }
        });

        mPriceLimitRangeBar.setOnRangeBarChangeListener(new MyExponentialDistanceRangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(MyExponentialDistanceRangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                // We don't care Left Pin or Right Pin is max or min
                mMinPriceIndexRangeBar = ((leftPinIndex <= rightPinIndex) ? leftPinIndex : rightPinIndex);
                mMaxPriceIndexRangeBar = (mMinPriceIndexRangeBar == leftPinIndex) ? rightPinIndex : leftPinIndex;
            }
        });
    }

    private String getFormatPriceStringFrom(String indexString) {
        String formatString = "";
        if (TextUtils.isDigitsOnly(indexString)) {
            String priceConvertedString = String.valueOf(Integer.valueOf(indexString) * CONVERT_TICK_INDEX_TO_VND_CURRENCY);
            formatString = MyUtils.addSeparatorEveryThreeCharacterFromLast(priceConvertedString, ".");
        }
        return formatString;
    }

    private String getDateLimitStringFrom(String indexString) {
        String formatString = "";
        if (TextUtils.isDigitsOnly(indexString)) {
            formatString = indexString + " ngày";
        }
        return formatString;
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

    public int getDateLimitValueRangeBar() {
        return mDateLimitIndexRangeBar + 1;
    }

    public int getMaxPriceValueRangeBar() {
        return mMaxPriceIndexRangeBar * CONVERT_TICK_INDEX_TO_VND_CURRENCY;
    }

    public int getMinPriceValueRangeBar() {
        return mMinPriceIndexRangeBar * CONVERT_TICK_INDEX_TO_VND_CURRENCY;
    }

    public String getMaxPriceValueStringRangeBar() {
        String maxPriceString = "";
        if (mPriceLimitRangeBar != null) {
            maxPriceString = String.valueOf(getMaxPriceValueRangeBar());
        }
        return maxPriceString;
    }

    public String getMinPriceValueStringRangeBar() {
        String minPriceString = "";
        if (mPriceLimitRangeBar != null) {
            minPriceString = String.valueOf(getMinPriceValueRangeBar());
        }
        return minPriceString;
    }

    public int getDateLimitIndexRangeBar() {
        return mDateLimitIndexRangeBar;
    }

    public int getMaxPriceIndexRangeBar() {
        return mMaxPriceIndexRangeBar;
    }

    public int getMinPriceIndexRangeBar() {
        return mMinPriceIndexRangeBar;
    }

}
