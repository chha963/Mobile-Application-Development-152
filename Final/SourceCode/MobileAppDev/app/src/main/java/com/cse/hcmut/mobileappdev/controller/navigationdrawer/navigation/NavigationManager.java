package com.cse.hcmut.mobileappdev.controller.navigationdrawer.navigation;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.manager.LogInManager;
import com.cse.hcmut.mobileappdev.models.product.Product;
import com.cse.hcmut.mobileappdev.utils.FragmentUtils;
import com.cse.hcmut.mobileappdev.view.features.home.main.ControllerViewMainFragment;
import com.cse.hcmut.mobileappdev.view.features.home.personalinfo.PersonalInfoContainerFragment;
import com.cse.hcmut.mobileappdev.view.features.home.postproduct.PostProductContainerFragment;
import com.cse.hcmut.mobileappdev.view.features.home.setup.SetupSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.home.support.SupportSingleFragment;

/**
 * Created by dinhn on 4/1/2016.
 */

// Singleton class
public class NavigationManager {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    public interface NavigationListener {
        void onBackStackChanged();
    }

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    private static NavigationManager sNavigationManager = null;

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static NavigationManager get() {

        if (sNavigationManager == null) {
            sNavigationManager = new NavigationManager();
        }

        return sNavigationManager;
    }

    public static void clear() {
        sNavigationManager = null;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    private FragmentManager mFragmentManager;

    private NavigationListener mNavigationListener;

    private Activity mActivity;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    private NavigationManager() {

    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    /**
     * Initialize the NavigationManager with a FragmentManager, which will be
     * used at the fragment transactions
     *
     * @param fragmentManager
     */
    public void init(FragmentManager fragmentManager, Activity activity) {
        this.mFragmentManager = fragmentManager;
        this.mActivity = activity;

        mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (mNavigationListener != null) {
                    mNavigationListener.onBackStackChanged();
                }
            }
        });
    }

    private void open(Fragment fragment) {
        if (mFragmentManager != null) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.frame_container_MainActivity, fragment)
                    .setCustomAnimations(
                            R.anim.slide_in_left,
                            R.anim.slide_out_right,
                            R.anim.slide_in_right,
                            R.anim.slide_out_left
                    )
                    .addToBackStack(fragment.toString())
                    .commit();
            mFragmentManager.executePendingTransactions();
        }
    }

    /**
     * Pops every fragment in back stack, remove current(if exist) and starts the given fragment
     * as a new one
     *
     * @param fragment
     */

    private void openAsRoot(Fragment fragment) {
        popAllFragmentInBackStack();
        removeCurrentFragment();
        open(fragment);
    }

    private void removeCurrentFragment() {

        Fragment currentFragment = mFragmentManager.findFragmentById(R.id.frame_container_MainActivity);

        if (currentFragment != null) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.remove(currentFragment);
            transaction.commit();
//            mFragmentManager.executePendingTransactions();
        }

    }

    private void updateActivity(Activity activity) {
        mActivity = activity;
    }

    private void popAllFragmentInBackStack() {

        // Clear all in back stack
        if (mFragmentManager != null) {
            FragmentUtils.sDisableFragmentAnimations = true;
            mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentUtils.sDisableFragmentAnimations = false;
        }

    }

    /**
     * Navigates back by popping the back stack. If there is no more items left we finish the
     * current activity.
     *
     * @param rootActivity
     */

    public void navigateBack(Activity rootActivity) {

        if (mFragmentManager.getBackStackEntryCount() == 0) {
            rootActivity.finish();
        } else {
            mFragmentManager.popBackStack();
        }

    }

    // Methods to createAlertDialog fragment menu

    public void startMenuHomeItem() {
        Fragment fragment = ControllerViewMainFragment.newInstance();
        openAsRoot(fragment);
    }

    public void startMenuPersonalInfoItem() {
        Fragment fragment = PersonalInfoContainerFragment.newInstance(0);
        openAsRoot(fragment);
    }

    public void startMenuBookmarkItem() {
        Fragment fragment = PersonalInfoContainerFragment.newInstance(2);
        openAsRoot(fragment);
    }

    public void startMenuBuyPostProductItem() {
        Fragment fragment = PostProductContainerFragment.newInstance(new Product(Product.INDEX_BUY_PRODUCT));
        openAsRoot(fragment);
    }

    public void startMenuSellPostProductItem() {
        Fragment fragment = PostProductContainerFragment.newInstance(new Product(Product.INDEX_SELL_PRODUCT));
        openAsRoot(fragment);
    }

    public void startMenuSupportItem() {
        Fragment fragment = SupportSingleFragment.newInstance();
        openAsRoot(fragment);
    }

    public void startMenuSetupItem() {
        Fragment fragment = SetupSingleFragment.newInstance();
        openAsRoot(fragment);
    }

    public void startMenuLogoutItem() {
        popAllFragmentInBackStack();
        removeCurrentFragment();
        if (mActivity != null) {
            LogInManager.get(mActivity).removeLoginInfoLocal();
            mActivity.finish();
        }
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

    public boolean isRootFragmentVisible() {
        return (mFragmentManager.getBackStackEntryCount() <= 1);
    }

    public NavigationListener getNavigationListener() {
        return mNavigationListener;
    }

    public void setNavigationListener(NavigationListener navigationListener) {
        mNavigationListener = navigationListener;
    }
}
