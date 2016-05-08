package com.cse.hcmut.mobileappdev.view.features.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.activity.MyActivity;
import com.cse.hcmut.mobileappdev.controller.MainActivity.MainActivityReceiver;
import com.cse.hcmut.mobileappdev.controller.MainActivity.MainActivitySender;
import com.cse.hcmut.mobileappdev.view.features.home.Main.ControllerViewMainFragment;
import com.cse.hcmut.mobileappdev.view.features.home.Main.Map.MapMainSingleFragment;
import com.cse.hcmut.mobileappdev.controller.navigationdrawer.drawer.DrawerManager;
import com.cse.hcmut.mobileappdev.controller.navigationdrawer.navigation.NavigationManager;
import com.cse.hcmut.mobileappdev.view.features.home.Main.ProductList.gridlayout.ProductListContainerSectionClickedFragment;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends MyActivity
        implements  Drawer.OnDrawerItemClickListener,
                    NavigationManager.NavigationListener,
                    MainActivityReceiver.OnReceiverProductListMainSingleFragment
{
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

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    // Navigation Drawer

    public NavigationManager mNavigationManager = NavigationManager.get();
    public DrawerManager mDrawerManager = DrawerManager.get();

    // Sender callback interface
    private MainActivitySender.OnSenderFromMainFragment mClickMainFragmentSender;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onReceiveProductListMainTabLayoutIndex(int tabPosition) {
        if (mClickMainFragmentSender != null) {
            mClickMainFragmentSender.onSelectedTabProductListMainFragment(tabPosition);
        }
    }

    @Override
    public void onReceiveProductListMainSectionClickIndex(int sectionPosition) {
        if (mClickMainFragmentSender != null) {
            mClickMainFragmentSender.onSelectedSectionProductListMainFragment(sectionPosition);
        }
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        boolean isHandled = mDrawerManager.handleItemSelected(this, view, position, drawerItem);

        if (isHandled == true) {
            mDrawerManager.getDrawer().closeDrawer();
        }

        return isHandled;
    }

    @Override
    public void onBackStackChanged() {
        // check if we display a root fragment and enable drawer only on root fragments
        boolean rootFragment = mNavigationManager.isRootFragmentVisible();
        mDrawerManager.enableDrawer(rootFragment);
        mDrawerManager.enableActionBarDrawerToogle(rootFragment);
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarVisibility(View.GONE);

        // Initialize the NavigationManager with this activity's FragmentManager
        mNavigationManager.init(getSupportFragmentManager());
        mNavigationManager.setNavigationListener(this);

        // First Run
        if (savedInstanceState == null) {
            initDrawer();
            mNavigationManager.startMenuHomeItem();
        } else { // restore state: fragment, ... when run
            //Fragment bundeProductSectionClickedBundle = getSupportFragmentManager().getFragment(savedInstanceState,);
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            Fragment curFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container_MainActivity);
            if (curFragment instanceof MapMainSingleFragment) {
                onReceiveProductListMainTabLayoutIndex(ControllerViewMainFragment.INDEX_PRODUCT_LIST_FRAGMENT);
            } else {
                showExitDialog();
            }
        } else {
            mNavigationManager.navigateBack(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it's present
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when a fragment is attached to the activity.
     *
     * @param fragment
     */
    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment instanceof ControllerViewMainFragment) {
            mClickMainFragmentSender = (ControllerViewMainFragment) fragment;
        }
    }

    protected void showExitDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage(R.string.exit_message)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void initDrawer() {
        mDrawerManager.buildDrawer(this);
        mDrawerManager.getDrawer().setOnDrawerItemClickListener(this);
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------\
}
