package com.cse.hcmut.mobileappdev.controller.navigationdrawer.draweritem;

import com.cse.hcmut.mobileappdev.R;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

/**
 * Created by dinhn on 4/2/2016.
 */
public class MyHighlightDrawerItem extends PrimaryDrawerItem {
    @Override
    public int getLayoutRes() {
        return R.layout.material_drawer_item_highlight;
    }

    @Override
    public int getType() {
        return R.id.material_drawer_item_highlight;
    }
}
