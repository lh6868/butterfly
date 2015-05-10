package com.baidu.butterfly.login;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.butterfly.R;
import com.baidu.butterfly.location.LocationApplication;
import com.baidu.butterfly.location.MainLocActivity;

public class WelcomeActivity extends Activity {
    private LocationApplication userInfo ;
    private TextView text_name;

    private Button btn_locate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        btn_locate = (Button) findViewById(R.id.btn_view);

        userInfo = (LocationApplication)this.getApplication();
        text_name = (TextView) findViewById(R.id.tv_userName);
        text_name.setText(userInfo.getUserName());
        //  System.out.println(userInfo.getUserName());

        // 注册监听事件
        btn_locate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //跳转界面
                Intent intent = new Intent(WelcomeActivity.this, MainLocActivity.class);
                WelcomeActivity.this.startActivity(intent);
                //finish();
            }
        });

    }
}
