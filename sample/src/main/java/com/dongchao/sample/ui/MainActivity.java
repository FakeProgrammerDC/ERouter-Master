package com.dongchao.sample.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dongchao.erouter.IntentCall;
import com.dongchao.sample.R;
import com.dongchao.sample.RouterUtil;
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
    }

    public void loginOut(View view) {
        LoginStatus.updateLoginStatus(!LoginStatus.getLoginStatus());
        String s = LoginStatus.getLoginStatus() ? "已登录" : "未登录";
        mainText.setText("这是首页" + s);
        loginOutBtn.setText(LoginStatus.getLoginStatus() ? "退出" : "登录");
    }

    public void btnStartSettingMulti(View view) {
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    RouterUtil.getCheckLoginInstance().startSettingActivity("btnStartSettingMulti").startIntent();
                }
            }).start();
        }
    }

    public void btnStartSetting(View view) {
        RouterUtil.getCheckLoginInstance().startSettingActivity("btnStartSetting").startIntent();
    }

    public void btnStartPay(View view) {
        IntentCall intentCall = RouterUtil.getInstance().startPayActivity("btnStartPay");
        if (intentCall.getIntent() != null) {
            this.startActivity(intentCall.getIntent());
        }

//        StartActivityUtil.getInstance().startPayActivity
//                ("true", 1, 1.11, true, (byte) 1, '1', (short) 11, 111L, 1.1f,
//                        new User("chao"), new Person("chaogege", "gegeg", 22));
    }
}