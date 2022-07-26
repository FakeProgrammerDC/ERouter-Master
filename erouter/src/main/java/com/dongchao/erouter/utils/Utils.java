package com.dongchao.erouter.utils;


import android.app.Activity;
import android.app.Application;

public class Utils {

    public static void init(Application app) {
        UtilsActivityLifecycleImpl.INSTANCE.init(app);
    }

    public static void unInit(Application app) {
        UtilsActivityLifecycleImpl.INSTANCE.unInit(app);
    }

    public static Activity getTopActivity() {
        return UtilsActivityLifecycleImpl.INSTANCE.getTopActivity();
    }

    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 50) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
