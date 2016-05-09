package com.cse.hcmut.mobileappdev.view.features.home.main.productlist.horizontalscroll.content;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.adapters.SectionListAdapter;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;
import com.cse.hcmut.mobileappdev.controller.MainActivity.MainActivityReceiver;
import com.cse.hcmut.mobileappdev.models.product.Product;
import com.cse.hcmut.mobileappdev.models.product.ProductLab;
import com.cse.hcmut.mobileappdev.models.section.SectionDataMainFragment;
import com.cse.hcmut.mobileappdev.models.tinydb.TinyDB;
import com.cse.hcmut.mobileappdev.view.features.home.MainActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListMainSingleFragment extends MyBaseSingleFragment
        implements SectionListAdapter.OnClickSection {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    public enum SectionItem {

        SECTION_BUY("MUA"),

        SECTION_SELL("BÁN");

        private String mLabel;

        SectionItem(String label) {
            mLabel = label;
        }

        public String getTitle() {
            return mLabel;
        }

        public int getPosition() {
            return this.ordinal();
        }

    }

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static ProductListMainSingleFragment newInstance() {

        Bundle args = new Bundle();

        ProductListMainSingleFragment fragment = new ProductListMainSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    public static final String KEY_LOCAL_HORIZONTAL_SCROLL_STATE_LIST =
            "com.cse.hcmut.mobileappdev.view.features.home.Main.ProductList.horizontalscroll.content" +
                    ".ProductListMainSingleFragment.horizontal_scroll_state";

    @BindView(R.id.recyclerViewVertical_ProductListMainSingleFragment)
    RecyclerView mVerticalSectionRecyclerView;

    ArrayList<SectionDataMainFragment> mSectionFilteredProductList = null;

    SectionListAdapter mSectionListAdapter = null;

    private MainActivityReceiver.OnReceiveFromProductListMainSingleFragment mSectionClickCallback;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public ProductListMainSingleFragment() {
        // Required empty public constructor
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.single_fragment_main_product_list;
    }

    @Override
    public void onClickSection(int clickPosition) {
        mSectionClickCallback.onReceiveProductListMainSectionClickIndex(clickPosition);
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MainActivity) {
            try {
                mSectionClickCallback = (MainActivity) context;
            } catch (ClassCastException e) {
//                Log.d("ProductListMainFragment",
//                        context.toString() + " must implement on ProductListMainFragment");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        Context context = getActivity();

        if (rootView != null) {
            setupWidgets(context);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSectionListAdapter.setOnClickSectionCallback(this);
    }

    private void setupWidgets(Context context) {
        setupAdapter(context);

        LinearLayoutManager layoutRecyclerView
                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        mVerticalSectionRecyclerView.setHasFixedSize(true);
        mVerticalSectionRecyclerView.setLayoutManager(
                layoutRecyclerView
        );

        mVerticalSectionRecyclerView.setAdapter(mSectionListAdapter);
        mVerticalSectionRecyclerView.setFocusable(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        Activity activity = getActivity();
        if (activity != null) {
            saveStateToLocalDatabase(activity);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Activity activity = getActivity();
        if (activity != null) {
            restoreStateFromLocalDatabase(activity);
        }
    }

    private void setupAdapter(Context context) {
        mSectionFilteredProductList = createDataProductBySection(context);
        mSectionListAdapter = new SectionListAdapter(context, mSectionFilteredProductList);
    }

    private ArrayList<SectionDataMainFragment> createDataProductBySection(Context context) {

        int numberOfSection = SectionItem.values().length;

        ArrayList<SectionDataMainFragment> sectionList = new ArrayList<>();

        for (int i = 0; i < numberOfSection; i++) {
            String sectionTitle = SectionItem.values()[i].getTitle();

            ArrayList<Product> listProductFiltered =
                    ProductLab.get(context).getProductListFilteredByType(i);

            SectionDataMainFragment singleItem =
                    new SectionDataMainFragment(sectionTitle, listProductFiltered);

            sectionList.add(singleItem);
        }

        return sectionList;
    }

    public void saveStateToLocalDatabase(Context context) {
        TinyDB tinyDB = new TinyDB(context);

        ArrayList<Integer> currentHorizontalScrollStateList = null;
        if (mSectionListAdapter != null) {
            currentHorizontalScrollStateList =
                    mSectionListAdapter.getCurrentHorizontalScrollStateList();
        }
        tinyDB.putListInt(KEY_LOCAL_HORIZONTAL_SCROLL_STATE_LIST, currentHorizontalScrollStateList);
    }

    public void restoreStateFromLocalDatabase(Context context) {
        TinyDB tinyDB = new TinyDB(context);
        if (mSectionListAdapter != null) {
            ArrayList<Integer> horizontalScrollStateList = tinyDB.getListInt(KEY_LOCAL_HORIZONTAL_SCROLL_STATE_LIST);
            mSectionListAdapter.setHorizontalScrollStateList(horizontalScrollStateList);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

//    public ArrayList<Integer> getHorizontalScrollState() {
//        ArrayList<Integer> currentHorizontalScrollStateList = null;
//        if (mSectionListAdapter != null) {
//            currentHorizontalScrollStateList =
//                    mSectionListAdapter.getCurrentHorizontalScrollStateList();
//        }
//        return currentHorizontalScrollStateList;
//    }
//
//    public void setHorizontalScrollStates(ArrayList<Integer> listScrollState) {
//        mHorizontalScrollState = listScrollState;
//    }
}
