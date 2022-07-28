package com.dongchao.sample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dongchao.erouter.utils.AppLog;
import com.dongchao.sample.R;
import com.dongchao.sample.StartActivityUtil;
import com.dongchao.sample.data.Person;
import com.dongchao.sample.data.User;
import com.dongchao.sample.util.LoginStatus;

public class MainActivity extends AppCompatActivity {

    TextView mainText;
    private Button loginOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainText = findViewById(R.id.mainText);
        loginOutBtn = findViewById(R.id.loginOut);
        loginOut(null);
        findViewById(R.id.btnStartSetting).setOnClickListener(this::btnStartSetting);
        findViewById(R.id.btnStartPay).setOnClickListener(this::btnStartPay);
        findViewById(R.id.btnStartSettingMulti).setOnClickListener(this::btnStartSettingMulti);
        findViewById(R.id.btnStartPayMulti).setOnClickListener(this::btnStartPayMulti);
        findViewById(R.id.loginOut).setOnClickListener(this::loginOut);
    }

    private void loginOut(View view) {
        LoginStatus.updateLoginStatus(!LoginStatus.getLoginStatus());
        String s = LoginStatus.getLoginStatus() ? "已登录" : "未登录";
        mainText.setText("这是首页" + s);
        loginOutBtn.setText(LoginStatus.getLoginStatus() ? "退出" : "登录");
    }

    private void btnStartSettingMulti(View view) {
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    Intent intent = StartActivityUtil.getCheckLoginInstance().getSettingActivityIntent("测试");
                    if (intent != null) {
                        MainActivity.this.startActivity(intent);
                    }
                }
            }).start();
        }
    }

    private void btnStartPayMulti(View view) {
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    StartActivityUtil.getCheckLoginInstance().startPayActivity("测试");
                }
            }).start();
        }
    }

    private void btnStartSetting(View view) {
        Intent intent = StartActivityUtil.getInstance().getSettingActivityIntent("测试");
        if (intent != null) {
            this.startActivity(intent);
        }

    }

    private void btnStartPay(View view) {
        //StartActivityUtil.getInstance().startPayActivity("测试");
        StartActivityUtil.getInstance().startPayActivity
                ("true", 1, 1.11, true, (byte) 1, '1', (short) 11, 111L, 1.1f,
                        new User("chao"), new Person("chaogege", "gegeg", 22));
    }

}