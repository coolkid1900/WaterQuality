package com.example.su.waterquality.models;

/**
 * Created by SU on 2017/3/16.
 */

public class MenuItems {
    int icon;

    String title;

    public MenuItems( String title,int icon) {
        this.icon = icon;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
