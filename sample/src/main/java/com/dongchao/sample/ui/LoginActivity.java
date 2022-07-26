package com.dongchao.sample.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dongchao.sample.R;
import com.dongchao.sample.StartActivityUtil;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.top).setOnClickListener(view -> {
            StartActivityUtil.getInstance().startSettingActivity("");
        });
    }
}