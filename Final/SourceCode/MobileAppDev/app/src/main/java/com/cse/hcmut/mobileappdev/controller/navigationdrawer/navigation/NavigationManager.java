package com.cse.hcmut.mobileappdev.controller.navigationdrawer.navigation;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.view.features.home.Bookmark.BookmarkSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.home.LogOut.LogOutSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.home.Main.ControllerViewMainFragment;
import com.cse.hcmut.mobileappdev.view.features.home.PersonalInfo.PersonalInfoSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.home.PostProduct.PostProductFragment;
import com.cse.hcmut.mobileappdev.view.features.home.Setup.SetupSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.home.Support.SupportSingleFragment;

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
    public void init (FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
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
            mFragmentManager.executePendingTransactions();
        }

    }

    private void popAllFragmentInBackStack() {

        // Clear all in back stack
        int backStackCount = mFragmentManager.getBackStackEntryCount();

        for (int i = 0; i < backStackCount; i++) {

            // Get the back stack fragment id
            int backStackId = mFragmentManager.getBackStackEntryAt(i).getId();

            mFragmentManager.popBackStackImmediate(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);

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
            mFragmentManager.popBackStackImmediate();
        }

    }

    // Methods to create fragment menu

    public void startMenuHomeItem() {
        Fragment fragment = ControllerViewMainFragment.newInstance();
        openAsRoot(fragment);
    }

    public void startMenuPersonalInfoItem() {
        Fragment fragment = PersonalInfoSingleFragment.newInstance();
        openAsRoot(fragment);
    }

    public void startMenuBookmarkItem() {
        Fragment fragment = BookmarkSingleFragment.newInstance();
        openAsRoot(fragment);
    }

    public void startMenuPostProductItem() {
        Fragment fragment = PostProductFragment.newInstance();
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
        Fragment fragment = LogOutSingleFragment.newInstance();
        openAsRoot(fragment);
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
