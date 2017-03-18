package com.example.su.waterquality.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.su.waterquality.R;

/**
 * Created by su on 2017/1/12.
 */

public class WaterQualityActivity extends AppCompatActivity{
    private ActionBar actionbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        Intent intent=getIntent();
        initFragment(intent.getIntExtra("type",0));
    }

    private void initViews() {
        setContentView(R.layout.activity_main);
        actionbar=getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    private void initFragment(int type) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (type==1){
            CollectionFragment collectionFragment=new CollectionFragment();
            fragmentTransaction.add(R.id.fragment_container,collectionFragment);
            setTitle("水质信息上传");
        }else if (type==2){
            QueryFragment queryFragment=new QueryFragment();
            fragmentTransaction.add(R.id.fragment_container,queryFragment);
            setTitle("水质信息查询");
        }else if (type==3){
            setTitle("应用设置");
        }
        fragmentTransaction.commit();

    }

    private void setTitle(String title) {
        actionbar.setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
