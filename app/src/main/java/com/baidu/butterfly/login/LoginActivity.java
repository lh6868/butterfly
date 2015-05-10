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
        //ǿ�������̳߳��з�������
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //ȥ������
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        //���ʵ������
        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
        userName = (EditText) findViewById(R.id.et_zh);
        password = (EditText) findViewById(R.id.et_mima);
        rem_pw = (CheckBox) findViewById(R.id.cb_mima);
        auto_login = (CheckBox) findViewById(R.id.cb_auto);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        btnQuit = (ImageButton) findViewById(R.id.img_btn);


        //�жϼ�ס�����ѡ���״̬
        if (sp.getBoolean("ISCHECK", false)) {
            //����Ĭ���Ǽ�¼����״̬
            rem_pw.setChecked(true);
            userName.setText(sp.getString("USER_NAME", ""));
            password.setText(sp.getString("PASSWORD", ""));
            //�ж��Զ���½��ѡ��״̬
            if (sp.getBoolean("AUTO_ISCHECK", false)) {
                //����Ĭ�����Զ���¼״̬
                auto_login.setChecked(true);
                //��ת����
                Intent intent = new Intent(LoginActivity.this, LogonActivity.class);
                LoginActivity.this.startActivity(intent);

            }
        }

        // ��¼�����¼�  ����Ĭ��Ϊ�û���Ϊ��butterfly ���룺xixihaha
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

                    //��¼�ɹ��ͼ�ס�����Ϊѡ��״̬�ű����û���Ϣ
                    if(rem_pw.isChecked())
                    {
                        //��ס�û��������롢
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("USER_NAME", userNameValue);
                        editor.putString("PASSWORD", passwordValue);
                        //ȫ�ּ�¼�û���Ϣ
                        editor.commit();
                    }
                    //��ת����
                    Intent intent = new Intent(LoginActivity.this,LogonActivity.class);
                    LoginActivity.this.startActivity(intent);
                    //finish();

                }else{
                    //String str = "�û�����������������µ�¼��";
                    String str = "increct password, please login again!";
                    Toast.makeText(LoginActivity.this,str, Toast.LENGTH_LONG).show();
                }

            }
        });

        //������ס�����ѡ��ť�¼�
        rem_pw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rem_pw.isChecked()) {

                    System.out.println("��ס������ѡ��");
                    sp.edit().putBoolean("ISCHECK", true).commit();

                } else {

                    System.out.println("��ס����û��ѡ��");
                    sp.edit().putBoolean("ISCHECK", false).commit();

                }

            }
        });

        //�����Զ���¼��ѡ���¼�
        auto_login.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (auto_login.isChecked()) {
                    System.out.println("�Զ���¼��ѡ��");
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();

                } else {
                    System.out.println("�Զ���¼û��ѡ��");
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                }
            }
        });


        // ע������¼�
        btn_register.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //��ת����
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
