package com.cse.hcmut.mobileappdev.navigationdrawer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.utils.MyUtils;

import java.util.ArrayList;

/**
 * Created by dinhn on 3/8/2016.
 */
public class NavigationDrawerListAdapter extends BaseAdapter {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private Context mContext = null;

    // Header
    private String mName;
    private String mEmail;
    private int mImageProfile; // resource id

    // List Item
    private ArrayList<NavigationDrawerItem> mNavigationDrawerItemList;

    public NavigationDrawerListAdapter(Context context,
                                       ArrayList<NavigationDrawerItem> navigationDrawerItemList,
                                       String name,
                                       String email,
                                       int imageProfile) {
        mContext = context;
        mNavigationDrawerItemList = navigationDrawerItemList;
        mName = name;
        mEmail = email;
        mImageProfile = imageProfile;
    }

    // Number of item present in the list
    @Override
    public int getCount() {
        return mNavigationDrawerItemList.size() + 1; // number of title + header;
    }

    @Override
    public Object getItem(int position) {
        return mNavigationDrawerItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (isPositionHeader(position)) // header
            {
                convertView = inflater.inflate(R.layout.header, null);
            } else // item
            {
                convertView = inflater.inflate(R.layout.item_row, null);
            }
        } else {
            if (isPositionHeader(position)) { // header
                viewHolder = new ViewHolder(convertView, TYPE_HEADER);

                String name = viewHolder.getTextName().getText().toString();
                String email = viewHolder.getTextEmail().getText().toString();
                ImageView imageProfile = viewHolder.getImageProfile();

                if (imageProfile != null && !MyUtils.hasImage(imageProfile)) {
                    viewHolder.getImageProfile().setImageResource(mImageProfile);
                }

                if (name.equals("")) {
                    viewHolder.getTextName().setText(mName);
                }

                if (email.equals("")) {
                    viewHolder.getTextEmail().setText(mEmail);
                }
            } else { // item
                // header is position 0 => item is (position - 1)
                final NavigationDrawerItem item = mNavigationDrawerItemList.get(position - 1);

                viewHolder = new ViewHolder(convertView, TYPE_ITEM);

                String title = viewHolder.getTextTitle().getText().toString();
                ImageView icon = viewHolder.getImageIcon();

                if (icon != null && !MyUtils.hasImage(icon)) {
                    viewHolder.getImageIcon().setImageResource(item.getIcon());
                }

                if (title.equals("")) {
                    viewHolder.getTextTitle().setText(item.getTitle().toString());
                }
            }
        }
        return convertView;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private class ViewHolder {
        int typeHolder; // item or header
        //header
        ImageView imageProfile = null;
        TextView textName = null;
        TextView textEmail = null;
        // item
        TextView textTitle = null;
        ImageView imageIcon = null;

        public ViewHolder(View itemView, int typeView) {
            if (typeView == TYPE_HEADER) {
                textName = (TextView) itemView.findViewById(R.id.txtViewNameHeaderNavigation);
                textEmail = (TextView) itemView.findViewById(R.id.txtViewEmailHeaderNavigation);
                imageProfile = (ImageView) itemView.findViewById(R.id.circleImageViewHeaderNavigation);
            } else if (typeView == TYPE_ITEM) {
                textTitle = (TextView) itemView.findViewById(R.id.navigationItemText);
                imageIcon = (ImageView) itemView.findViewById(R.id.navigationItemIcon);
            }
            typeHolder = typeView;
        }

        public int getTypeHolder() {
            return typeHolder;
        }

        public ImageView getImageProfile() {
            return imageProfile;
        }

        public void setImageProfile(ImageView imageProfile) {
            this.imageProfile = imageProfile;
        }

        public TextView getTextName() {
            return textName;
        }

        public void setTextName(TextView textName) {
            this.textName = textName;
        }

        public TextView getTextEmail() {
            return textEmail;
        }

        public void setTextEmail(TextView textEmail) {
            this.textEmail = textEmail;
        }

        public TextView getTextTitle() {
            return textTitle;
        }

        public void setTextTitle(TextView textTitle) {
            this.textTitle = textTitle;
        }

        public ImageView getImageIcon() {
            return imageIcon;
        }

        public void setImageIcon(ImageView imageIcon) {
            this.imageIcon = imageIcon;
        }
    }
}
