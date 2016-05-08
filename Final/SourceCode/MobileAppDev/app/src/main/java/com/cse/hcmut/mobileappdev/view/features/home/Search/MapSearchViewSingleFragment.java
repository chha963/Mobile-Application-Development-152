package com.cse.hcmut.mobileappdev.view.features.home.Search;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.search.MyDepthArrowHomeSearchViewSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.home.Search.animation.SimpleAnimationListener;
import com.cse.hcmut.mobileappdev.view.features.home.Search.suggestion.SampleSuggestionsBuilder;

import org.cryse.widget.persistentsearch.PersistentSearchView;
import org.cryse.widget.persistentsearch.SearchSuggestionsBuilder;

import butterknife.BindString;


/**
 * Created by dinhn on 3/24/2016.
 */
public class MapSearchViewSingleFragment extends MyDepthArrowHomeSearchViewSingleFragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static MapSearchViewSingleFragment newInstance() {

        Bundle args = new Bundle();

        MapSearchViewSingleFragment fragment = new MapSearchViewSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @BindString(R.string.logo_text_map_search_view)
    String mSearchViewLogoString;

    @BindString(R.string.text_hint_map_search_view)
    String mSearchViewTextHintString;

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
        return new SampleSuggestionsBuilder(getActivity());
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
        Activity activity = getActivity();
        if (activity != null) {
            activity.onBackPressed();
        }
    }

    @Override
    protected void onHomeButtonEditingListener() {

    }

    @Override
    protected void onSearchTintViewOnClickListener() {
        mSearchView.cancelEditing();
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
                    public void onSearch(String s) {

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

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}

