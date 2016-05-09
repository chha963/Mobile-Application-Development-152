package com.cse.hcmut.mobileappdev.view.features.home.personalinfo;

import android.app.Activity;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.activity.MyActivity;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseThreeTabLayoutChangeColorFragment;
import com.cse.hcmut.mobileappdev.manager.LogInManager;
import com.cse.hcmut.mobileappdev.models.product.Product;
import com.cse.hcmut.mobileappdev.utils.ColorFilterGenerator;
import com.cse.hcmut.mobileappdev.utils.MyUtils;
import com.cse.hcmut.mobileappdev.utils.ResourceUtils;
import com.cse.hcmut.mobileappdev.view.features.home.personalinfo.content.ContentPersonalInfoSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.home.personalinfo.header.HeaderDetailsTopPersonalInfoSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.home.personalinfo.header.HeaderImagePersonalInfoSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.home.postproduct.PostProductContainerFragment;

import butterknife.BindString;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalInfoContainerFragment extends MyBaseThreeTabLayoutChangeColorFragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    private static final String ARG_CURRENT_POSITION_TYPE = "arg_current_position";
    private static final String ARG_LAST_POSITION_TYPE = "arg_last_position";

    private static final int INDEX_BUY = 0;
    private static final int INDEX_SELL = 1;
    private static final int INDEX_BOOKMARK = 2;

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static PersonalInfoContainerFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt(ARG_CURRENT_POSITION_TYPE, position);

        PersonalInfoContainerFragment fragment = new PersonalInfoContainerFragment();
        fragment.setArguments(args);

        return fragment;

    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @BindString(R.string.first_tab_personal_info_fragment)
    String mFirstTabLabel;

    @BindString(R.string.second_tab_personal_info_fragment)
    String mSecondTabLabel;

    @BindString(R.string.third_tab_personal_info_fragment)
    String mThirdTabLabel;

    private int mCurrentColor = 0;

    private int mLastTabPosition = 0;

    private int mCurrentTabPosition = 0;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public PersonalInfoContainerFragment() {
        // Required empty public constructor
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected void onTabLayoutSelected(TabLayout.Tab tab) {
        int selectedPosition = tab.getPosition();
        mLastTabPosition = mCurrentTabPosition;

        if (mCurrentTabPosition != selectedPosition) {
            mCurrentTabPosition = selectedPosition;
            replaceFragmentAndColorWhenClickTab(selectedPosition);
            // Save current and last tab position
            saveTabPosition();
        }
    }

    @Override
    protected void onTabLayoutUnselected(TabLayout.Tab tab) {

    }

    @Override
    protected void onTabLayoutReselected(TabLayout.Tab tab) {
    }

    @Override
    protected Fragment getFirstHeaderTopFragment() {
        return HeaderImagePersonalInfoSingleFragment.newInstance();
    }

    @Override
    protected Fragment getSecondHeaderTopFragment() {
        return HeaderDetailsTopPersonalInfoSingleFragment.newInstance();
    }

    @Override
    protected Fragment getContentBotFragment() {
        return ContentPersonalInfoSingleFragment.newInstance(mCurrentTabPosition);
    }

    @Override
    protected String getFirstLabelTab() {
        return mFirstTabLabel;
    }

    @Override
    protected String getSecondLabelTab() {
        return mSecondTabLabel;
    }

    @Override
    protected String getThirdLabelTab() {
        return mThirdTabLabel;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mLastTabPosition = bundle.getInt(ARG_LAST_POSITION_TYPE);
            mCurrentTabPosition = bundle.getInt(ARG_CURRENT_POSITION_TYPE);
            mCurrentColor = ResourceUtils.getColorForPersonalInfoTabPosition(getActivity(), mCurrentTabPosition);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState == null) {

            setTabLayoutSelected(mCurrentTabPosition);
            selectDrawableAndChangeColorImageViewBottomWhenClickTab(mCurrentTabPosition);
            replaceWidgetsColorWhenClickTab(mCurrentTabPosition);

            String urlImageHeader = LogInManager.get(getActivity()).getAccount().getAvatarImageId();
            Glide.with(this)
                    .load(urlImageHeader)
                    .placeholder(R.drawable.ic_place_holder)
                    .dontAnimate()
                    .into(mAvatarImageView);

            mFeatureRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCurrentTabPosition != 2) {
                        replaceEditFragmentWith(new Product(mCurrentTabPosition));
                    } else {
                        MyUtils.showFeatureIsDevelopingDialog(getActivity());
                    }
                }
            });
        }

    }

    private void replaceEditFragmentWith(Product product) {

        MyActivity activity = (MyActivity) getActivity();
        if (activity != null) {

            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            Fragment fragment = PostProductContainerFragment.newInstance(product);

            fragmentTransaction.setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.fade_out
            );

            fragmentTransaction.replace(R.id.frame_container_MainActivity, fragment);
            fragmentTransaction.addToBackStack(fragment.toString());

            fragmentTransaction.commit();
        }
    }

    private FragmentTransaction createFragmentTransaction(FragmentManager fragmentManager, int position) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (ft != null) {
            switch (position) {

                case INDEX_BUY:
                    ft.setCustomAnimations(
                            R.anim.slide_in_right_short,
                            R.anim.slide_out_left_short,
                            R.anim.slide_in_left_short,
                            R.anim.slide_out_right_short
                    );
                    break;

                case INDEX_SELL:
                    if (mLastTabPosition == INDEX_BUY) {
                        ft.setCustomAnimations(
                                R.anim.slide_in_left_short,
                                R.anim.slide_out_right_short,
                                R.anim.slide_in_right_short,
                                R.anim.slide_out_left_short
                        );
                    } else if (mLastTabPosition == INDEX_BOOKMARK) {
                        ft.setCustomAnimations(
                                R.anim.slide_in_right_short,
                                R.anim.slide_out_left_short,
                                R.anim.slide_in_left_short,
                                R.anim.slide_out_right_short
                        );
                    }
                    break;

                case INDEX_BOOKMARK:
                    ft.setCustomAnimations(
                            R.anim.slide_in_left_short,
                            R.anim.slide_out_right_short,
                            R.anim.slide_in_right_short,
                            R.anim.slide_out_left_short
                    );
                    break;

            }
        }
        return ft;
    }

    private void replaceFragmentAndColorWhenClickTab(int position) {
        Activity activity = getActivity();
        if (activity != null) {

            Fragment contentFragment = ContentPersonalInfoSingleFragment.newInstance(mCurrentTabPosition);

            FragmentManager fragmentManager = getFragmentManager();

            FragmentTransaction animateFragmentTransaction = createFragmentTransaction(fragmentManager, position);

            if (animateFragmentTransaction != null) {

                if (contentFragment != null) {
                    animateFragmentTransaction.replace(CONTENT_BOT_LAYOUT_ID, contentFragment);
                }

                animateFragmentTransaction.commit();
            }

            fragmentManager.executePendingTransactions();
            selectDrawableAndChangeColorImageViewBottomWhenClickTab(position);
            replaceWidgetsColorWhenClickTab(position);
        }
    }

    private void saveTabPosition() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putInt(ARG_CURRENT_POSITION_TYPE, mCurrentTabPosition);
        bundle.putInt(ARG_LAST_POSITION_TYPE, mLastTabPosition);
    }

    protected void selectDrawableAndChangeColorImageViewBottomWhenClickTab(int tabPosition) {
        Activity activity = getActivity();
        if (activity != null) {
            Drawable drawable = ResourceUtils.getFeatureDrawablePersonalInfoWith(activity, tabPosition);
            int color = ResourceUtils.getColorForPersonalInfoTabPosition(activity, tabPosition);
            ColorFilter colorFilter = ColorFilterGenerator.from(drawable).to(color);
            drawable.setColorFilter(colorFilter);
            mFeatureImageView.setImageDrawable(drawable);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
