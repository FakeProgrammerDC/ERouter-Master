package com.dongchao.sample;

import android.app.Application;

import com.dongchao.erouter.utils.ERouterInit;


public class MainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ERouterInit.init(this);
    }
}
