package com.example.su.waterquality.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.su.waterquality.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et_username;
    EditText et_password;
    String username;
    String password;
    Button bt_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initListener();
    }


    private void initViews() {
        setContentView(R.layout.activity_login);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        bt_login = (Button) findViewById(R.id.bt_login);
    }

    private void initListener() {
        bt_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                username = et_username.getText().toString();
                password = et_password.getText().toString();
                ProgressDialog progressDialog=new ProgressDialog(this);
                progressDialog.setMessage("正在登录中……");
                progressDialog.show();
                if (TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
                    Toast.makeText(this,"用户名或密码为空，请重新登录！",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }else {
                    if (loginSuccess(username, password)) {
                        progressDialog.dismiss();
                        Intent toMainMenuActivity = new Intent(this, MainActivity.class);
                        startActivity(toMainMenuActivity);
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(this,"登录失败，请稍后再试！",Toast.LENGTH_LONG).show();
                    }
                }
        }
    }

    private boolean loginSuccess(String username, String password) {
        return true;
    }
}
