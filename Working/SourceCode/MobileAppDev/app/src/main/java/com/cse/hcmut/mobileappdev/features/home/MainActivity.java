package com.cse.hcmut.mobileappdev.features.home;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.constants.MyConstants;
import com.cse.hcmut.mobileappdev.features.home.Bookmark.BookmarkFragment;
import com.cse.hcmut.mobileappdev.features.home.Friends.FriendsFragment;
import com.cse.hcmut.mobileappdev.features.home.LogOut.LogOutFragment;
import com.cse.hcmut.mobileappdev.features.home.Main.WidgetMainFragment;
import com.cse.hcmut.mobileappdev.features.home.PersonalInfo.PersonalInfoFragment;
import com.cse.hcmut.mobileappdev.features.home.Setup.SetupFragment;
import com.cse.hcmut.mobileappdev.features.home.Support.SupportFragment;
import com.cse.hcmut.mobileappdev.navigationdrawer.NavigationDrawerItem;
import com.cse.hcmut.mobileappdev.navigationdrawer.NavigationDrawerListAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // App title
    private CharSequence mTitle;

    // Navigation title
    private CharSequence mDrawerTitle;

    //Navigation menu
    private String[] mNavigationMenuTitles;
    private TypedArray mNavigationMenuIcons;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private ArrayList<NavigationDrawerItem> mNavigationDrawerItemList;
    private NavigationDrawerListAdapter mAdapter;

    // Profile Info
    private String mNameProfile;
    private String mEmailProfile;
    private int mImageIdProfile;

    // Navigation Drawer Item


    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Profile
        mNameProfile = getResources().getString(R.string.header_name);
        mEmailProfile = getResources().getString(R.string.header_mail);
        mImageIdProfile = R.drawable.ic_circle_image_header;

        mToolbar = (Toolbar) findViewById(R.id.toolBar_MainActivity);
        setSupportActionBar(mToolbar);

        // load slide menu items title
        mNavigationMenuTitles = getResources().getStringArray(R.array.navigation_drawer_item_title_array);

        // load navigation drawer icons from resources
        mNavigationMenuIcons = getResources().obtainTypedArray(R.array.navigation_drawer_item_icon_array);

        mDrawerList = (ListView) findViewById(R.id.listNavigation_MainActivity);

        mNavigationDrawerItemList = new ArrayList<>();

        int titleListSize = mNavigationMenuTitles.length;
        int iconListSize = mNavigationMenuIcons.length();

        if (titleListSize == iconListSize) {
            for (int i = 0; i < iconListSize; i++) {
                NavigationDrawerItem item = new NavigationDrawerItem(mNavigationMenuTitles[i], mNavigationMenuIcons.getResourceId(i, -1));
                mNavigationDrawerItemList.add(item);
            }
        } else {
            Log.d("MainActivity", "Your string res icon and title must be same length");
        }

        mNavigationMenuIcons.recycle();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_MainActivity);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mAdapter.notifyDataSetChanged();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mAdapter = new NavigationDrawerListAdapter(this, mNavigationDrawerItemList, mNameProfile, mEmailProfile, mImageIdProfile);
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // First Run
        if (savedInstanceState == null) {
            displayView(MyConstants.HOME_POSITION_IN_NAVIGATION_MENU);
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

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(" " + title);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occured.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        switch (position) {
            case MyConstants.HOME_POSITION_IN_NAVIGATION_MENU:
                fragment = new WidgetMainFragment();
                break;
            case MyConstants.PERSONAL_POSITION_IN_NAVIGATION_MENU:
                fragment = new PersonalInfoFragment();
                break;
            case MyConstants.FRIENDS_POSITION_IN_NAVIGATION_MENU:
                fragment = new FriendsFragment();
                break;
            case MyConstants.BOOKMARK_POSITION_IN_NAVIGATION_MENU:
                fragment = new BookmarkFragment();
                break;
            case MyConstants.SUPPORT_POSITION_IN_NAVIGATION_MENU:
                fragment = new SupportFragment();
                break;
            case MyConstants.SETUP_POSITION_IN_NAVIGATION_MENU:
                fragment = new SetupFragment();
                break;
            case MyConstants.LOGOUT_POSITION_IN_NAVIGATION_MENU:
                fragment = new LogOutFragment();
                break;
            default:
                break;
        }
        // Commit and change fragment
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            if (transaction != null) {
                transaction.replace(R.id.frame_container_MainActivity, fragment);
                transaction.commit();
            }

            // Update selected item and title
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationMenuTitles[position - 1]);

            // close the drawer
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            Log.d("MainActivity", "Error in creating fragment");
        }
    }

    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // display view for selected navigation drawer item
            view.setSelected(true);
            displayView(position);
        }
    }
}
