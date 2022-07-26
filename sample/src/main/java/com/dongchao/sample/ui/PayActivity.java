package com.dongchao.sample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.dongchao.erouter.utils.AppLog;
import com.dongchao.sample.R;
import com.dongchao.sample.StartActivityUtil;
import com.dongchao.sample.data.Person;
import com.dongchao.sample.data.User;

public class PayActivity extends AppCompatActivity {


    private static final String TAG = "PayActivity";

//    @BindExtra("key")
//    String key;
//    @BindExtra("key2")
//    char key3;
//    @BindExtra("key1")
//    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);


        TextView textView = findViewById(R.id.payText);
        textView.setOnClickListener(view -> {
            StartActivityUtil.getInstance().startSettingActivity();
        });
//        AppLog.i(TAG,"BindExtra key = %s");
//        textView.setText(getIntent().getStringExtra("key") + getIntent().getStringExtra("key2"));
//        void startPayActivity(@Extra("key") String key,@Extra("key2") int key2,
//        @Extra("key3") double key3,@Extra("key4") boolean key4,
//        @Extra("key5") byte key5,@Extra("key6") char key6,
//        @Extra("key7") short key7,@Extra("key8") long key8,@Extra("key9") float key9);
        Intent i = getIntent();
        print(i.getStringExtra("key"));
        print(i.getIntExtra("key2", 0) + "");
        print(i.getDoubleExtra("key3", 0.00) + "");
        print(i.getBooleanExtra("key4", false) + "");
        byte b = 0X00;
        print(i.getByteExtra("key5", b) + "");
        short s = 0;
        float f = 0.0f;
        print(i.getCharExtra("key6", ' ') + "");
        print(i.getShortExtra("key7", s) + "");
        print(i.getLongExtra("key8", 0L) + "");
        print(i.getFloatExtra("key9", f) + "");
        User user = (User) i.getSerializableExtra("key10");
        print(user.name);
        Person person = i.getParcelableExtra("key11");
        print(person.getUsername());

    }

    public void print(String str) {
        AppLog.i(TAG,"Extra = %s", str);
    }
}