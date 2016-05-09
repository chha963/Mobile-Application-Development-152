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
import com.cse.hcmut.mobileappdev.controller.navigationdrawer.drawer.DrawerManager;
import com.cse.hcmut.mobileappdev.controller.navigationdrawer.navigation.NavigationManager;
import com.cse.hcmut.mobileappdev.models.tinydb.TinyDB;
import com.cse.hcmut.mobileappdev.utils.ResourceUtils;
import com.cse.hcmut.mobileappdev.view.features.home.main.ControllerViewMainFragment;
import com.cse.hcmut.mobileappdev.view.features.home.main.productlist.horizontalscroll.content.ProductListMainSingleFragment;
import com.cse.hcmut.mobileappdev.view.features.home.map.MapMainSingleFragment;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends MyActivity
        implements Drawer.OnDrawerItemClickListener,
        NavigationManager.NavigationListener,
        MainActivityReceiver.OnReceiveFromProductListMainSingleFragment {
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

    public NavigationManager mNavigationManager;
    public DrawerManager mDrawerManager;

    // Sender callback interface
    private MainActivitySender.OnSendToMainFragment mClickMainFragmentSender;

    // Menu Item selected (to restore last Drawer when pause app - avoid leak memory)
    private DrawerManager.MenuItem mLastMenuItem = null;

    private RoundedImageView mCropSelectionMainImageView;

    private RoundedImageView mCropSelectionEditIconImageView;

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
//        boolean rootFragment = mNavigationManager.isRootFragmentVisible();
//        mDrawerManager.enableDrawer(rootFragment);
//        mDrawerManager.enableActionBarDrawerToggle(rootFragment);
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setToolbarVisibility(View.GONE);

        // Initialize the NavigationManager with this activity's FragmentManager
        mNavigationManager = NavigationManager.get();
        mDrawerManager = DrawerManager.get();
        setupNavigation();

        // First Run
        if (savedInstanceState == null) {
            setupDrawer();
            storeDefaultStateToLocal();
            mNavigationManager.startMenuHomeItem();
        } else { // restore state: fragment, ... when run
            //Fragment bundeProductSectionClickedBundle = getSupportFragmentManager().getFragment(savedInstanceState,);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLastMenuItem = DrawerManager.get().getLasttSelectedItem();
    }

    @Override
    protected void onStop() {
        super.onStop();
        DrawerManager.clear();
        NavigationManager.clear();
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
                        moveTaskToBack(true);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void setupNavigation() {
        if (mNavigationManager != null) {
            mNavigationManager.init(getSupportFragmentManager(), MainActivity.this);
            mNavigationManager.setNavigationListener(this);
        }
    }

    private void setupDrawer() {
        if (mDrawerManager != null) {
            if (mLastMenuItem != null) {
                mDrawerManager.setDefaultItemSelected(mLastMenuItem);
            }
            mDrawerManager.buildDrawer(this);
            mDrawerManager.getDrawer().setOnDrawerItemClickListener(this);
        }
    }

    private void storeDefaultStateToLocal() {
        TinyDB tinyDB = new TinyDB(this);

        int sectionLength = ResourceUtils.getNumberOfMainSection(this);
        ArrayList<Integer> horizontalScrollStateList = new ArrayList<>(Collections.nCopies(sectionLength, 0));

        tinyDB.putListInt(ProductListMainSingleFragment.KEY_LOCAL_HORIZONTAL_SCROLL_STATE_LIST, horizontalScrollStateList);
        tinyDB.putObject(MapMainSingleFragment.KEY_LOCAL_MAP_STATE, null);
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------\
}
