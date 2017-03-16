package com.example.su.waterquality.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.su.waterquality.R;
import com.example.su.waterquality.models.MenuItems;

import java.util.List;

/**
 * Created by Xingfeng on 2016/5/21.
 */
public class MenuItemAdapter extends BaseAdapter {


    private LayoutInflater mInflater;
    private List<MenuItems> mDatas;

    public MenuItemAdapter(Context context, List<MenuItems> mDatas) {
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = mInflater.inflate(R.layout.item_mainmenu, parent, false);
        MenuItems mainMenuItem = mDatas.get(position);
        ImageView img = (ImageView) convertView.findViewById(R.id.menu_item_img);
        img.setImageResource(mainMenuItem.getIcon());
        TextView desc = (TextView) convertView.findViewById(R.id.menu_item_desc);
        desc.setText(mainMenuItem.getTitle());
        return convertView;
    }
}
