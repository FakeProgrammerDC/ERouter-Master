package com.dongchao.sample;

import android.app.Application;

import com.dongchao.erouter.utils.ERouterInit;


public class MainApp extends Application {

    public static MainApp applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        //ActivityUtils.addActivityLifecycleCallbacks(new Utils.ActivityLifecycleCallbacks());
        ERouterInit.init(this);
    }
}
