package com.dongchao.sample.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dongchao.sample.R;
import com.dongchao.sample.RouterUtil;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.top).setOnClickListener(view -> {
            RouterUtil.getInstance().startSettingActivity("top").startIntent();
        });
    }
}