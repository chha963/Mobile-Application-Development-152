package com.cse.hcmut.mobileappdev.controller.navigationdrawer.drawer;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.controller.navigationdrawer.builder.MyAccountHeaderBuilder;
import com.cse.hcmut.mobileappdev.controller.navigationdrawer.draweritem.MyHighlightDrawerItem;
import com.cse.hcmut.mobileappdev.controller.navigationdrawer.draweritem.MySectionDrawerItem;
import com.cse.hcmut.mobileappdev.controller.navigationdrawer.navigation.NavigationManager;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

/**
 * Created by dinhn on 4/2/2016.
 */
public class DrawerManager {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    public enum MenuItem {

        MENU_REDIRECTION_SECTION("ĐIỀU HƯỚNG"),
        MENU_HOME_ITEM("Trang chủ"),
        MENU_PERSONAL_INFO_ITEM("Thông tin của tôi"),
        MENU_BOOKMARK_ITEM("Gói hàng"),

        MENU_FEATURE_SECTION("CHỨC NĂNG"),
        MENU_POST_PRODUCT_HIGHLIGHT_ITEM("Đăng sản phẩm"),
        MENU_SUPPORT_ITEM("Hỗ trợ"),
        MENU_SETUP_ITEM("Cài đặt"),
        MENU_LOGOUT_ITEM("Đăng xuất");

        private String mLabel;

        MenuItem(String label) {
            mLabel = label;
        }

        public String getTitle() {
            return mLabel;
        }

    }

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    private static DrawerManager sDrawerManager = null;

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static DrawerManager get() {
        if (sDrawerManager == null) {
            sDrawerManager = new DrawerManager();
        }
        return sDrawerManager;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    private MenuItem mLastSelectedItem = null;

    private Drawer mDrawer;

    public NavigationManager mNavigationManager = NavigationManager.get();


    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    private DrawerManager() {
        setDefaultItemSelected(MenuItem.MENU_HOME_ITEM);
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    /**
     * Creates a new drawer for this application with predefined items
     *
     * @param activity
     */
    public void buildDrawer(Activity activity) {

        ArrayList<IDrawerItem> items = getDrawerItems();

        AccountHeader accountHeader = buildAccountHeader(activity);

        mDrawer = new DrawerBuilder()
                .withActivity(activity)
                .withDrawerItems(items)
                .withSelectedItemByPosition(mLastSelectedItem.ordinal() + 1)
                .withAccountHeader(accountHeader)
                .addDrawerItems()
                .build();

    }

    private AccountHeader buildAccountHeader(final Activity activity) {
        // Profile Attribute (get from account, connect to database)
        String name = "FITAROART";
        String iconUrl = "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoViApp-Navigation/miss_fortune_icon_zps4hswrc3v.png";

        // Sample profile
        IProfile profile = new ProfileDrawerItem()
                .withName(name)
                .withIcon(iconUrl);

        AccountHeader accountHeader =
                new MyAccountHeaderBuilder()
                        .withSelectionListEnabledForSingleProfile(false)
                        .withDividerBelowHeader(false)
                        .withActivity(activity)
                        .withAccountHeader(R.layout.my_drawer_header)
                        .withHeaderBackground(R.drawable.ic_header_background_navigation_drawer)
                        .withHeightRes(R.dimen.material_drawer_header_background_height)
                        .withProfileImagesClickable(false)
                        .addProfiles(profile)
                        .build();

        return accountHeader;
    }

    private void decorate(ArrayList<IDrawerItem> itemsList) {

        for (IDrawerItem item : itemsList) {
            if (item instanceof PrimaryDrawerItem) {
                ((PrimaryDrawerItem) item).withSelectedColorRes(R.color.menu_item_selected);
            }
        }

    }

    public boolean handleItemSelected(Context context, View view, int i, IDrawerItem iDrawerItem) {

        boolean isDrawerChanged = false;

        i--;
        if (i >= 0 && i < MenuItem.values().length) {
            MenuItem item = MenuItem.values()[i];

            if (item != mLastSelectedItem) {
                switch (item) {
                    // If click section, do nothing
                    case MENU_FEATURE_SECTION:
                    case MENU_REDIRECTION_SECTION:
                        break;

                    case MENU_HOME_ITEM:
                        mNavigationManager.startMenuHomeItem();
                        break;

                    case MENU_PERSONAL_INFO_ITEM:
                        mNavigationManager.startMenuPersonalInfoItem();
                        break;

                    case MENU_BOOKMARK_ITEM:
                        mNavigationManager.startMenuBookmarkItem();
                        break;

                    case MENU_POST_PRODUCT_HIGHLIGHT_ITEM:
                        mNavigationManager.startMenuPostProductItem();
                        break;

                    case MENU_SUPPORT_ITEM:
                        mNavigationManager.startMenuSupportItem();
                        break;

                    case MENU_SETUP_ITEM:
                        mNavigationManager.startMenuSetupItem();
                        break;

                    case MENU_LOGOUT_ITEM:
                        mNavigationManager.startMenuLogoutItem();
                        break;

                    default:
                        mNavigationManager.startMenuHomeItem();
                        break;

                }
                mLastSelectedItem = item;
                closeDrawer();
                isDrawerChanged = true;
            }
        }
        return isDrawerChanged;
    }

    private void closeDrawer() {
        if (mDrawer != null) {
            mDrawer.closeDrawer();
        }
    }

    /**
     * Enables / Disables the drawer
     *
     * @param isEnabled
     */
    public void enableDrawer(boolean isEnabled) {
        if (mDrawer != null) {
            int lockMode = (isEnabled == true) ? DrawerLayout.LOCK_MODE_UNDEFINED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
            mDrawer.getDrawerLayout()
                    .setDrawerLockMode(lockMode);
        }
    }

    /**
     * Enable / disable the drawer indicator
     *
     * @param isEnabled
     */
    public void enableActionBarDrawerToogle(boolean isEnabled) {
        if (mDrawer != null) {
            ActionBarDrawerToggle actionBarDrawerToggle = mDrawer.getActionBarDrawerToggle();
            if (actionBarDrawerToggle != null) {
                actionBarDrawerToggle.setDrawerIndicatorEnabled(isEnabled);
            }
        }
    }

    /**
     * Initializes the drawer state
     */
    public void reset() {
        setDefaultItemSelected(MenuItem.MENU_HOME_ITEM);
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

    public void setDefaultItemSelected(MenuItem menuItem) {
        mLastSelectedItem = menuItem;
    }

    public int getLastestSelectedItemPosition() {
        return mLastSelectedItem.ordinal();
    }

    public ArrayList<IDrawerItem> getDrawerItems() {

        ArrayList<IDrawerItem> items = new ArrayList<>();

        // Todo: Create menu item list here

        items.add(new MySectionDrawerItem()
                .withName(MenuItem.MENU_REDIRECTION_SECTION.getTitle())
                .withDivider(false));

        items.add(new PrimaryDrawerItem()
                .withName(MenuItem.MENU_HOME_ITEM.getTitle())
                .withIcon(R.drawable.ic_navigation_drawer_item));

        items.add(new PrimaryDrawerItem()
                .withName(MenuItem.MENU_PERSONAL_INFO_ITEM.getTitle())
                .withIcon(R.drawable.ic_navigation_drawer_item));

        items.add(new PrimaryDrawerItem()
                .withName(MenuItem.MENU_BOOKMARK_ITEM.getTitle())
                .withIcon(R.drawable.ic_navigation_drawer_item));


        items.add(new MySectionDrawerItem()
                .withName(MenuItem.MENU_FEATURE_SECTION.getTitle())
                .withDivider(false));

        items.add(new MyHighlightDrawerItem()
                .withName(MenuItem.MENU_POST_PRODUCT_HIGHLIGHT_ITEM.getTitle())
                .withIcon(R.drawable.ic_navigation_drawer_item));

        items.add(new PrimaryDrawerItem()
                .withName(MenuItem.MENU_SUPPORT_ITEM.getTitle())
                .withIcon(R.drawable.ic_navigation_drawer_item));

        items.add(new PrimaryDrawerItem()
                .withName(MenuItem.MENU_SETUP_ITEM.getTitle())
                .withIcon(R.drawable.ic_navigation_drawer_item));

        items.add(new PrimaryDrawerItem()
                .withName(MenuItem.MENU_LOGOUT_ITEM.getTitle())
                .withIcon(R.drawable.ic_navigation_drawer_item));

        decorate(items);

        return items;
    }

    public Drawer getDrawer() {
        return mDrawer;
    }
}
