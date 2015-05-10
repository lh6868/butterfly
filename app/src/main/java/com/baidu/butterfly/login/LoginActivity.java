package com.baidu.butterfly.login;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import com.baidu.butterfly.R;
import com.baidu.butterfly.location.LocationApplication;
import com.baidu.butterfly.register.RegisterActivity;

public class LoginActivity extends Activity  {
    private EditText userName, password;
    private CheckBox rem_pw, auto_login;
    private Button btn_login;
    private Button btn_register;
    private ImageButton btnQuit;
    private String userNameValue,passwordValue;
    private SharedPreferences sp;

    @Override
    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //强制在主线程程中访问网络
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //去除标题
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        //获得实例对象
        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
        userName = (EditText) findViewById(R.id.et_zh);
        password = (EditText) findViewById(R.id.et_mima);
        rem_pw = (CheckBox) findViewById(R.id.cb_mima);
        auto_login = (CheckBox) findViewById(R.id.cb_auto);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        btnQuit = (ImageButton) findViewById(R.id.img_btn);


        //判断记住密码多选框的状态
        if (sp.getBoolean("ISCHECK", false)) {
            //设置默认是记录密码状态
            rem_pw.setChecked(true);
            userName.setText(sp.getString("USER_NAME", ""));
            password.setText(sp.getString("PASSWORD", ""));
            //判断自动登陆多选框状态
            if (sp.getBoolean("AUTO_ISCHECK", false)) {
                //设置默认是自动登录状态
                auto_login.setChecked(true);
                //跳转界面
                Intent intent = new Intent(LoginActivity.this, LogonActivity.class);
                LoginActivity.this.startActivity(intent);

            }
        }

        // 登录监听事件  现在默认为用户名为：butterfly 密码：xixihaha
        btn_login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                userNameValue = userName.getText().toString();
                passwordValue = password.getText().toString();

                // if(userNameValue.equals("a")&&passwordValue.equals("a"))
                if(ValidateUser.validateNoAuto(userNameValue, passwordValue))
                {
                    Toast.makeText(LoginActivity.this, "login success", Toast.LENGTH_SHORT).show();
                    LocationApplication userInfo = (LocationApplication)getApplication();
                    userInfo.setUserName(userNameValue);
                    userInfo.setUserId(0);

                    //登录成功和记住密码框为选中状态才保存用户信息
                    if(rem_pw.isChecked())
                    {
                        //记住用户名、密码、
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("USER_NAME", userNameValue);
                        editor.putString("PASSWORD", passwordValue);
                        //全局记录用户信息
                        editor.commit();
                    }
                    //跳转界面
                    Intent intent = new Intent(LoginActivity.this,LogonActivity.class);
                    LoginActivity.this.startActivity(intent);
                    //finish();

                }else{
                    //String str = "用户名或密码错误，请重新登录！";
                    String str = "increct password, please login again!";
                    Toast.makeText(LoginActivity.this,str, Toast.LENGTH_LONG).show();
                }

            }
        });

        //监听记住密码多选框按钮事件
        rem_pw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rem_pw.isChecked()) {

                    System.out.println("记住密码已选中");
                    sp.edit().putBoolean("ISCHECK", true).commit();

                } else {

                    System.out.println("记住密码没有选中");
                    sp.edit().putBoolean("ISCHECK", false).commit();

                }

            }
        });

        //监听自动登录多选框事件
        auto_login.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (auto_login.isChecked()) {
                    System.out.println("自动登录已选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();

                } else {
                    System.out.println("自动登录没有选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                }
            }
        });


        // 注册监听事件
        btn_register.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //跳转界面
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
                //finish();
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }}
