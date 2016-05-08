package com.cse.hcmut.mobileappdev.controller.navigationdrawer.draweritem;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.fastadapter.utils.ViewHolderFactory;
import com.mikepenz.materialdrawer.holder.ColorHolder;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.AbstractDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.model.interfaces.Typefaceable;
import com.mikepenz.materialize.util.UIUtils;

/**
 * Created by dinhn on 4/2/2016.
 */
public class MySectionDrawerItem extends AbstractDrawerItem<MySectionDrawerItem, MySectionDrawerItem.MyViewHolder> implements Nameable<MySectionDrawerItem>, Typefaceable<MySectionDrawerItem> {
    private StringHolder name;
    private boolean divider = true;

    private ColorHolder textColor;

    private Typeface typeface = null;

    private ColorHolder layoutColor;

    public MySectionDrawerItem withName(StringHolder name) {
        this.name = name;
        return this;
    }

    public MySectionDrawerItem withName(String name) {
        this.name = new StringHolder(name);
        return this;
    }

    public MySectionDrawerItem withName(@StringRes int nameRes) {
        this.name = new StringHolder(nameRes);
        return this;
    }

    public MySectionDrawerItem withDivider(boolean divider) {
        this.divider = divider;
        return this;
    }

    public MySectionDrawerItem withTextColor(int textColor) {
        this.textColor = ColorHolder.fromColor(textColor);
        return this;
    }

    public MySectionDrawerItem withTextColorRes(int textColorRes) {
        this.textColor = ColorHolder.fromColorRes(textColorRes);
        return this;
    }

    public MySectionDrawerItem withLayoutColor(int layoutColor) {
        this.layoutColor = ColorHolder.fromColor(layoutColor);
        return this;
    }

    public MySectionDrawerItem withLayoutColorRes(int layoutColorRes) {
        this.layoutColor = ColorHolder.fromColorRes(layoutColorRes);
        return this;
    }

    public MySectionDrawerItem withTypeface(Typeface typeface) {
        this.typeface = typeface;
        return this;
    }

    public boolean hasDivider() {
        return divider;
    }

    public ColorHolder getTextColor() {
        return textColor;
    }

    public ColorHolder getLayoutColor() {
        return layoutColor;
    }

    public StringHolder getName() {
        return name;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public int getType() {
        return com.mikepenz.materialdrawer.R.id.material_drawer_item_section;
    }

    @Override
    @LayoutRes
    public int getLayoutRes() {
        return com.mikepenz.materialdrawer.R.layout.material_drawer_item_section;
    }

    @Override
    public Typeface getTypeface() {
        return typeface;
    }

    @Override
    public void bindView(MyViewHolder viewHolder) {
        Context ctx = viewHolder.itemView.getContext();

        //set the identifier from the drawerItem here. It can be used to run tests
        viewHolder.itemView.setId(hashCode());

        //define this item to be not clickable nor enabled
        viewHolder.view.setClickable(false);
        viewHolder.view.setEnabled(false);

        //define view color
        viewHolder.view.setBackgroundColor(ColorHolder.color(getLayoutColor(), ctx, com.cse.hcmut.mobileappdev.R.attr.my_material_drawer_section_background, com.cse.hcmut.mobileappdev.R.color.my_material_drawer_section_background));

        //define the text color
        viewHolder.name.setTextColor(ColorHolder.color(getTextColor(), ctx, com.cse.hcmut.mobileappdev.R.attr.my_material_drawer_section_text, com.cse.hcmut.mobileappdev.R.color.my_material_drawer_section_text));

        //set the text for the name
        StringHolder.applyTo(this.getName(), viewHolder.name);

        //define the typeface for our textViews
        if (getTypeface() != null) {
            viewHolder.name.setTypeface(getTypeface());
        }

        //hide the divider if we do not need one
        if (this.hasDivider()) {
            viewHolder.divider.setVisibility(View.VISIBLE);
        } else {
            viewHolder.divider.setVisibility(View.GONE);
        }

        //set the color for the divider
        viewHolder.divider.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(ctx, com.mikepenz.materialdrawer.R.attr.material_drawer_divider, com.mikepenz.materialdrawer.R.color.material_drawer_divider));

        //call the onPostBindView method to trigger post bind view actions (like the listener to modify the item if required)
        onPostBindView(this, viewHolder.itemView);
    }

    @Override
    public ViewHolderFactory<MyViewHolder> getFactory() {
        return new ItemFactory();
    }

    public static class ItemFactory implements ViewHolderFactory<MyViewHolder> {
        public MyViewHolder create(View v) {
            return new MyViewHolder(v);
        }
    }

    protected static class MyViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private View divider;
        private TextView name;

        private MyViewHolder(View view) {
            super(view);
            this.view = view;
            this.divider = view.findViewById(com.mikepenz.materialdrawer.R.id.material_drawer_divider);
            this.name = (TextView) view.findViewById(com.mikepenz.materialdrawer.R.id.material_drawer_name);
        }
    }
}
