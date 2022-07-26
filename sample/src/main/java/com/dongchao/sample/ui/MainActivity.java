package com.dongchao.sample.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dongchao.erouter.utils.AppLog;
import com.dongchao.sample.R;
import com.dongchao.sample.StartActivityUtil;
import com.dongchao.sample.data.Person;
import com.dongchao.sample.data.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnStartSetting).setOnClickListener(view -> {
//            if (StartActivityUtil.getInstance().startSettingActivity("ce")){
//                AppLog.i("MainActivity","跳转成功");
//            }
            new Thread(() -> {
                for (int i = 0; i < 20; i++) {
                    StartActivityUtil.getInstance().startSettingActivity("测试");
                    StartActivityUtil.getInstance().startSettingActivity("测试");
                }
            }).start();

            new Thread(() -> {
                for (int i = 0; i < 20; i++) {
                    StartActivityUtil.getInstance().startSettingActivity("测试2");
                    StartActivityUtil.getInstance().startSettingActivity("测试2");
                }
            }).start();
        });

        short s = 11;
        byte b = 0X01;
        float f = 1.1f;

        findViewById(R.id.btnStartPay)
                .setOnClickListener(view -> StartActivityUtil.getInstance().startPayActivity
        ("测试", 1, 1.11, true, b, '1', s, 111L, f,
                new User("chao"), new Person("chaogege", "gegeg", 22)));
    }

}