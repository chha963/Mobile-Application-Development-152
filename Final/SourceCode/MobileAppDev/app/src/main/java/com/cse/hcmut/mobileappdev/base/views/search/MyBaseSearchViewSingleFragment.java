package com.cse.hcmut.mobileappdev.base.views.search;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.home.Search.modelcontroller.SearchResult;
import com.cse.hcmut.mobileappdev.view.features.home.Search.modelcontroller.SearchResultAdapter;

import org.cryse.widget.persistentsearch.PersistentSearchView;
import org.cryse.widget.persistentsearch.SearchSuggestionsBuilder;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.BindDimen;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class MyBaseSearchViewSingleFragment extends MyBaseSingleFragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    protected abstract String getLogoTextString();

    protected abstract String getSearchTextHint();

    protected abstract SearchSuggestionsBuilder getSuggestionBuilder();

    protected abstract void onHomeButtonNormalListener();

    protected abstract void onHomeButtonSearchingListener();

    protected abstract void onHomeButtonEditingListener();

    protected abstract void onSearchTintViewOnClickListener();

    protected abstract PersistentSearchView.SearchListener onSearchListener();

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @Bind(R.id.searchView_SearchViewSingleFragment)
    protected PersistentSearchView mSearchView;

    @Bind(R.id.searchTintView_SearchViewSingleFragment)
    protected View mSearchTintView;

    // List search result view
    @Bind(R.id.recyclerViewSearchResult_SearchViewSingleFragment)
    protected RecyclerView mRecyclerView;

    @BindDimen(R.dimen.search_view_horizontal_padding)
    int mHorizontalPadding;

    @BindDimen(R.dimen.search_view_vertical_padding)
    int mVerticalPadding;

    // List search result
    protected SearchResultAdapter mResultAdapter;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public MyBaseSearchViewSingleFragment() {
        // Required empty public constructor
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.single_fragment_search_view;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        if (rootView != null) {
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupWidgets();
        bindWidgetsToListener();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupSuggestionBuilder();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Activity.RESULT_OK) {
            ArrayList<String> matches =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            mSearchView.populateEditText(matches);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void bindWidgetsToListener() {
        bindSearchViewListener();
        bindSearchTintViewListener();
    }

    private void bindSearchViewListener() {
        mSearchView.setHomeButtonNormalListener(new PersistentSearchView.HomeButtonNormalListener() {
            @Override
            public void onHomeButtonNormalClick() {
                onHomeButtonNormalListener();
            }
        });

        mSearchView.setHomeButtonEditingListener(new PersistentSearchView.HomeButtonEditingListener() {
            @Override
            public void onHomeButtonEditClick() {
                onHomeButtonEditingListener();
            }
        });

        mSearchView.setHomeButtonSearchingListener(new PersistentSearchView.HomeButtonSearchingListener() {
            @Override
            public void onHomeButtonSearchClick() {
                onHomeButtonSearchingListener();
            }
        });

        mSearchView.setSearchListener(onSearchListener());
    }

    private void bindSearchTintViewListener() {
        mSearchTintView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchTintViewOnClickListener();
            }
        });
    }

    protected void setupWidgets() {

        mResultAdapter = new SearchResultAdapter(new ArrayList<SearchResult>());
        setupRecyclerViewWithAdapter(mRecyclerView, mResultAdapter);
        setupSearchView();

    }

    private void setupSearchView() {

        if (mSearchView != null) {
            // Set string
            mSearchView.setLogoTextString(getLogoTextString());
            mSearchView.setSearchTextHint(getSearchTextHint());

            // Set padding
            mSearchView.setHorizontalPadding(mHorizontalPadding);
            mSearchView.setVerticalPadding(mVerticalPadding);
        }
    }

    private void setupSuggestionBuilder() {

        if (mSearchView != null) {
            // Set suggestion builder
            mSearchView.setSuggestionBuilder(getSuggestionBuilder());
        }

    }

    private void setupRecyclerViewWithAdapter(RecyclerView recyclerView,RecyclerView.Adapter adapter) {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        setupAdapter(recyclerView,adapter);
    }

    private void setupAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------
}
