package com.example.su.waterquality.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.su.waterquality.R;
import com.example.su.waterquality.adapters.MenuItemAdapter;
import com.example.su.waterquality.models.MenuItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SU on 2017/3/15.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,AdapterView.OnItemClickListener{
    private GridView mGridView;
    private List<MenuItems> mDatas;
    private MenuItemAdapter mAdapter;

    public static final int UPLOAD=1;
    public static final int QUERY=2;
    public static final int SETTING=3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initDatas();
        initListeners();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_collect) {
            Intent uploadintent=new Intent(this,WaterQualityActivity.class);
            uploadintent.putExtra("type",UPLOAD);
            startActivity(uploadintent);
        } else if (id == R.id.nav_query) {
            Intent queryintent=new Intent(this,WaterQualityActivity.class);
            queryintent.putExtra("type",QUERY);
            startActivity(queryintent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_setting) {
            Intent settingintent=new Intent(this,WaterQualityActivity.class);
            settingintent.putExtra("type",SETTING);
            startActivity(settingintent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initViews(){
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mGridView = (GridView) findViewById(R.id.gridview);
    }


    private void initDatas() {
        mDatas = new ArrayList<>();
        mDatas.add(new MenuItems("上传", R.mipmap.upload));
        mDatas.add(new MenuItems("查询", R.mipmap.query));
        mDatas.add(new MenuItems("设置", R.mipmap.setting));
        mAdapter = new MenuItemAdapter(this, mDatas);
        mGridView.setAdapter(mAdapter);
    }


    private void initListeners() {
        mGridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                Intent uploadintent=new Intent(this,WaterQualityActivity.class);
                uploadintent.putExtra("type",UPLOAD);
                startActivity(uploadintent);
                break;
            case 1:
                Intent queryintent=new Intent(this,WaterQualityActivity.class);
                queryintent.putExtra("type",QUERY);
                startActivity(queryintent);
                break;
            case 2:
                Intent settingintent=new Intent(this,WaterQualityActivity.class);
                settingintent.putExtra("type",SETTING);
                startActivity(settingintent);
                break;
        }
    }
}
