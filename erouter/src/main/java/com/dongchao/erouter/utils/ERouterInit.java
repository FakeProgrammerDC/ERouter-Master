package com.dongchao.erouter.utils;

import android.app.Application;
import android.content.Context;

public class ERouterInit {

    private static boolean initialized = false;

    public static void init(Context ctx) {
        if (ctx == null) {
            return;
        }

        if (ERouterInit.initialized) {
            return;
        }

        synchronized (ERouterInit.class) {
            if (ERouterInit.initialized) {
                return;
            }
            ERouterInit.initialized = true;
        }

        init((Application) ctx.getApplicationContext());
    }

    public static void init(Application app) {
        UtilsActivityLifecycleImpl.INSTANCE.init(app);
    }

    public static void unInit(Application app) {
        UtilsActivityLifecycleImpl.INSTANCE.unInit(app);
    }

}
