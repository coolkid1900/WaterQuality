package com.example.su.waterquality.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.example.su.waterquality.R;

import java.util.concurrent.TimeUnit;

/**
 * 闪屏,使用本地广播注册监听网络变化的Receiver
 *
 * @author Xingfeng
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//
        setContentView(R.layout.activity_splash);
        new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    TimeUnit.SECONDS.sleep(2);
                    Intent toLoginActivity = new Intent(SplashActivity.this,
                            LoginActivity.class);
                    startActivity(toLoginActivity);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }
}
