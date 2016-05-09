package com.cse.hcmut.mobileappdev.view.features.home.search;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.search.MyBurgerHomeSearchViewSingleFragment;
import com.cse.hcmut.mobileappdev.controller.navigationdrawer.drawer.DrawerManager;
import com.cse.hcmut.mobileappdev.utils.MyUtils;
import com.cse.hcmut.mobileappdev.utils.ResourceUtils;
import com.cse.hcmut.mobileappdev.view.features.home.search.advancesearch.AdvanceSearchDialogFragment;
import com.cse.hcmut.mobileappdev.view.features.home.search.animation.SimpleAnimationListener;
import com.cse.hcmut.mobileappdev.view.features.home.search.suggestion.SampleSuggestionsBuilder;
import com.mikepenz.materialdrawer.Drawer;

import org.cryse.widget.persistentsearch.PersistentSearchView;
import org.cryse.widget.persistentsearch.SearchSuggestionsBuilder;

import butterknife.BindString;

/**
 * Created by dinhn on 3/24/2016.
 */
public class ProductSearchViewSingleFragment extends MyBurgerHomeSearchViewSingleFragment {

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

    private static final int REQUEST_ADVANCE_SEARCH = 1;

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static ProductSearchViewSingleFragment newInstance() {

        Bundle args = new Bundle();

        ProductSearchViewSingleFragment fragment = new ProductSearchViewSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

//    private MainActivityReceiver.OnReceiverHomeButtonProductListSearchView mHomeButtonCallback;

    @BindString(R.string.logo_text_product_list_search_view)
    String mSearchViewLogoString;

    @BindString(R.string.text_hint_product_list_search_view)
    String mSearchViewTextHintString;

    @BindString(R.string.value_advance_search_option)
    String mAdvanceSearchValue;

    int mAdvanceSearchColor;

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
//        mHomeButtonCallback.onClickNavigationMenu();
        Drawer drawer = DrawerManager.get().getDrawer();
        if (drawer != null) {
            drawer.openDrawer();
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

        PersistentSearchView.SearchListener listener
                = new PersistentSearchView.SearchListener() {
            @Override
            public void onSearchCleared() {
            }

            @Override
            public void onSearchTermChanged(String s) {
            }

            @Override
            public void onSearch(String string) {
                if (string.equals(mAdvanceSearchValue)) {
                    mSearchView.closeSearch();
                    FragmentManager fragmentManager = getFragmentManager();
                    AdvanceSearchDialogFragment fragment = createAdvanceSearchDialog();
                    fragment.setTargetFragment(ProductSearchViewSingleFragment.this, REQUEST_ADVANCE_SEARCH);
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
                if (mResultAdapter != null) {
                    mResultAdapter.clear();
                    if (mRecyclerView != null) {
                        if (mRecyclerView.getVisibility() == View.VISIBLE) {
                            mRecyclerView.setVisibility(View.GONE);
                        }
                    }
                }
            }
        };

        return listener;
    }


    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity activity = getActivity();
        mAdvanceSearchColor = ResourceUtils.getDefaultGreenColorTabLayout(activity);

    }

    private AdvanceSearchDialogFragment createAdvanceSearchDialog() {

        final AdvanceSearchDialogFragment advanceSearchAlertDialog =
                AdvanceSearchDialogFragment.newInstance(mAdvanceSearchColor);

        advanceSearchAlertDialog.setCallbacksListener(new AdvanceSearchDialogFragment.CallbacksListener() {
            @Override
            public void onPositiveButtonClicked() {
                //do something
                MyUtils.showFeatureIsDevelopingDialog(getActivity());
                mSearchView.cancelEditing();
            }

            @Override
            public void onNegativeButtonClicked() {
                //do something
                mSearchView.cancelEditing();
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
//                Log.d("ProductSearchView",
//                        context.toString() + " must implement on ProductSearchViewFragment");
//            }
//        }
//    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
