package com.example.su.waterquality.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.su.waterquality.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText et_username;
    EditText et_password;
    Button bt_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initListener();
    }



    private void initViews() {
        setContentView(R.layout.activity_login);
        et_username= (EditText) findViewById(R.id.et_username);
        et_password= (EditText) findViewById(R.id.et_password);
        bt_login= (Button) findViewById(R.id.bt_login);
    }

    private void initListener() {
        bt_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_login:
                Intent toMainMenuActivity=new Intent(this,MainActivity.class);
                startActivity(toMainMenuActivity);
        }
    }
}
