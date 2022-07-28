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
            this.startActivity(StartActivityUtil.getInstance().getSettingActivityIntent("cs"));
        });

        Intent i = getIntent();
        if (i.getStringExtra("key").equals("true")) {
            print(i.getStringExtra("key"));
            print(i.getIntExtra("key2", 0) + "");
            print(i.getDoubleExtra("key3", 0.00) + "");
            print(i.getBooleanExtra("key4", false) + "");
            print(i.getByteExtra("key5", (byte) 0) + "");
            print(i.getCharExtra("key6", ' ') + "");
            print(i.getShortExtra("key7", (short) 0) + "");
            print(i.getLongExtra("key8", 0L) + "");
            print(i.getFloatExtra("key9", 0.0f) + "");

            User user = (User) i.getSerializableExtra("key10");
            Person person = i.getParcelableExtra("key11");
            print(user.name);
            print(person.getUsername());
        }

    }

    public void print(String str) {
        AppLog.i(TAG, "Extra = %s", str);
    }
}