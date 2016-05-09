package com.cse.hcmut.mobileappdev.view.features.home.search;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.search.MyDepthArrowHomeSearchViewSingleFragment;
import com.cse.hcmut.mobileappdev.utils.MyUtils;
import com.cse.hcmut.mobileappdev.view.features.home.search.advancesearch.AdvanceSearchDialogFragment;
import com.cse.hcmut.mobileappdev.view.features.home.search.animation.SimpleAnimationListener;
import com.cse.hcmut.mobileappdev.view.features.home.search.suggestion.SampleSuggestionsBuilder;

import org.cryse.widget.persistentsearch.PersistentSearchView;
import org.cryse.widget.persistentsearch.SearchSuggestionsBuilder;

import butterknife.BindDimen;
import butterknife.BindString;

/**
 * Created by dinhn on 4/11/2016.
 */
public class ProductSearchViewStickyTopSingleFragment extends MyDepthArrowHomeSearchViewSingleFragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    private static final String DIALOG_ADVANCE_SEARCH = "DialogAdvanceSearch";

    private static final String ARG_MAIN_COLOR = "main_color";

    private static final int REQUEST_ADVANCE_SEARCH = 1;

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static Fragment newInstance(int color) {
        Bundle args = new Bundle();
        args.putInt(ARG_MAIN_COLOR, color);

        ProductSearchViewStickyTopSingleFragment fragment = new ProductSearchViewStickyTopSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

//    private MainActivityReceiver.OnReceiverHomeButtonMapSearchView mHomeButtonCallback;

    @BindString(R.string.logo_text_product_list_search_view)
    String mSearchViewLogoString;

    @BindString(R.string.text_hint_product_list_search_view)
    String mSearchViewTextHintString;

    @BindDimen(R.dimen.search_view_sticky_top_vertical_padding)
    int mStickyTopVerticalPadding;

    @BindString(R.string.value_advance_search_option)
    String advanceSearchValue;

    private int mMainColor;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected String getLogoTextString() {
        return mSearchViewLogoString;
    }

    @Override
    protected String getSearchTextHint() {
        return mSearchViewTextHintString;
    }

    @Override
    protected SearchSuggestionsBuilder getSuggestionBuilder() {
        return new SampleSuggestionsBuilder(getActivity(), mMainColor);
    }

    @Override
    protected void onHomeButtonNormalListener() {
        Activity activity = getActivity();
        if (activity != null) {
            activity.onBackPressed();
        }
    }

    @Override
    protected void onHomeButtonSearchingListener() {
        if (mSearchView != null) {
            mSearchView.cancelEditing();
        }
    }

    @Override
    protected void onHomeButtonEditingListener() {
        if (mSearchView != null) {
            mSearchView.cancelEditing();
        }
    }

    @Override
    protected void onSearchTintViewOnClickListener() {
        if (mSearchView != null) {
            mSearchView.cancelEditing();
        }
    }

    @Override
    protected PersistentSearchView.SearchListener onSearchListener() {

        PersistentSearchView.SearchListener listener =
                new PersistentSearchView.SearchListener() {
                    @Override
                    public void onSearchCleared() {

                    }

                    @Override
                    public void onSearchTermChanged(String s) {

                    }

                    @Override
                    public void onSearch(String string) {
                        if (string.equals(advanceSearchValue)) {
                            mSearchView.closeSearch();
                            FragmentManager fragmentManager = getFragmentManager();
                            AdvanceSearchDialogFragment fragment = createAdvanceSearchDialog();
                            fragment.setTargetFragment(ProductSearchViewStickyTopSingleFragment.this, REQUEST_ADVANCE_SEARCH);
                            fragment.show(fragmentManager, DIALOG_ADVANCE_SEARCH);

                        } else {
                            Toast.makeText(getActivity(), string + " Searched", Toast.LENGTH_LONG).show();
                            mRecyclerView.setVisibility(View.VISIBLE);
                            fillResultToRecyclerView(string);
                        }
                    }

                    @Override
                    public void onSearchEditOpened() {
                        mSearchTintView.setVisibility(View.VISIBLE);
                        mSearchTintView
                                .animate()
                                .alpha(1.0f)
                                .setDuration(300)
                                .setListener(new SimpleAnimationListener())
                                .start();
                    }

                    @Override
                    public void onSearchEditClosed() {
                        mSearchTintView
                                .animate()
                                .alpha(0.0f)
                                .setDuration(300)
                                .setListener(new SimpleAnimationListener() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        if (mSearchTintView != null) {
                                            mSearchTintView.setVisibility(View.GONE);
                                        }
                                    }
                                })
                                .start();
                    }

                    @Override
                    public boolean onSearchEditBackPressed() {
                        if (mSearchView != null) {
                            mSearchView.cancelEditing();
                        }
                        return false;
                    }

                    @Override
                    public void onSearchExit() {
                        mResultAdapter.clear();
                        if (mRecyclerView.getVisibility() == View.VISIBLE) {
                            mRecyclerView.setVisibility(View.GONE);
                        }
                    }
                };

        return listener;
    }


    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        mMainColor = ContextCompat.getColor(getActivity(), R.color.advance_search_default_background);
        Bundle args = getArguments();
        if (args != null) {
            mMainColor = args.getInt(ARG_MAIN_COLOR);
        }

        return rootView;
    }

    @Override
    protected void setupWidgets() {
        super.setupWidgets();
        moveSearchViewToTop();
    }

    private void moveSearchViewToTop() {
        mSearchView.setVerticalPadding(mStickyTopVerticalPadding);
    }

    private AdvanceSearchDialogFragment createAdvanceSearchDialog() {

        final AdvanceSearchDialogFragment advanceSearchAlertDialog =
                AdvanceSearchDialogFragment.newInstance(mMainColor);

        advanceSearchAlertDialog.setCallbacksListener(new AdvanceSearchDialogFragment.CallbacksListener() {
            @Override
            public void onPositiveButtonClicked() {
                //do something
                MyUtils.showFeatureIsDevelopingDialog(getActivity());
            }

            @Override
            public void onNegativeButtonClicked() {
            }
        });
        return advanceSearchAlertDialog;
    }

    //    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        if (context instanceof MainActivity) {
//            try {
//                mHomeButtonCallback = (MainActivity) context;
//            } catch (ClassCastException e) {
//                Log.d("MapSearchViewFragment",
//                        context.toString() + " must implement on MapSearchViewFragment");
//            }
//        }
//    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
