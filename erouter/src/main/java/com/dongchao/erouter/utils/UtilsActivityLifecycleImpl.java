package com.dongchao.erouter.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

final class UtilsActivityLifecycleImpl implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = "UtilsActivityLifecycleImpl";

    static final UtilsActivityLifecycleImpl INSTANCE = new UtilsActivityLifecycleImpl();

    private static Application app;

    private final LinkedList<Activity> mActivityList = new LinkedList<>();

    void init(Application context) {
        app = context;
        app.registerActivityLifecycleCallbacks(this);
    }

    void unInit(Application app) {
        mActivityList.clear();
        app.unregisterActivityLifecycleCallbacks(this);
    }

    Activity getTopActivity() {
        List<Activity> activityList = getActivityList();
        for (Activity activity : activityList) {
            if (!isActivityAlive(activity)) {
                continue;
            }
            return activity;
        }
        return null;
    }

    List<Activity> getActivityList() {
        if (!mActivityList.isEmpty()) {
            return new LinkedList<>(mActivityList);
        }
        List<Activity> reflectActivities = getActivitiesByReflect();
        mActivityList.addAll(reflectActivities);
        return new LinkedList<>(mActivityList);
    }

    private List<Activity> getActivitiesByReflect() {
        LinkedList<Activity> list = new LinkedList<>();
        Activity topActivity = null;
        try {
            Object activityThread = getActivityThread();
            Field mActivitiesField = activityThread.getClass().getDeclaredField("mActivities");
            mActivitiesField.setAccessible(true);
            Object mActivities = mActivitiesField.get(activityThread);
            if (!(mActivities instanceof Map)) {
                return list;
            }
            Map<Object, Object> binder_activityClientRecord_map = (Map<Object, Object>) mActivities;
            for (Object activityRecord : binder_activityClientRecord_map.values()) {
                Class activityClientRecordClass = activityRecord.getClass();
                Field activityField = activityClientRecordClass.getDeclaredField("activity");
                activityField.setAccessible(true);
                Activity activity = (Activity) activityField.get(activityRecord);
                if (topActivity == null) {
                    Field pausedField = activityClientRecordClass.getDeclaredField("paused");
                    pausedField.setAccessible(true);
                    if (!pausedField.getBoolean(activityRecord)) {
                        topActivity = activity;
                    } else {
                        list.add(activity);
                    }
                } else {
                    list.add(activity);
                }
            }
        } catch (Exception e) {
            AppLog.e(TAG, "getActivitiesByReflect: %s" , e.getMessage());
        }
        if (topActivity != null) {
            list.addFirst(topActivity);
        }
        return list;
    }

    private Object getActivityThread() {
        Object activityThread = getActivityThreadInActivityThreadStaticField();
        if (activityThread != null) return activityThread;
        return getActivityThreadInActivityThreadStaticMethod();
    }

    private Object getActivityThreadInActivityThreadStaticMethod() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            return activityThreadClass.getMethod("currentActivityThread").invoke(null);
        } catch (Exception e) {
            AppLog.e(TAG,"getActivityThreadInActivityThreadStaticMethod: %s" , e.getMessage());
            return null;
        }
    }

    private Object getActivityThreadInActivityThreadStaticField() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Field sCurrentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            return sCurrentActivityThreadField.get(null);
        } catch (Exception e) {
            AppLog.e(TAG, "getActivityThreadInActivityThreadStaticField: %s" , e.getMessage());
            return null;
        }
    }

    //活动是否在活动
    public static boolean isActivityAlive(final Activity activity) {
        return activity != null && !activity.isFinishing()
                && (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1 || !activity.isDestroyed());
    }

    private void setTopActivity(final Activity activity) {
        if (mActivityList.contains(activity)) {
            if (!mActivityList.getFirst().equals(activity)) {
                mActivityList.remove(activity);
                mActivityList.addFirst(activity);
            }
        } else {
            mActivityList.addFirst(activity);
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        mActivityList.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        setTopActivity(activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mActivityList.remove(activity);
        fixSoftInputLeaks(activity.getWindow());
    }

    public static void fixSoftInputLeaks(final Window window) {
        InputMethodManager imm =
                (InputMethodManager) app.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        String[] leakViews = new String[]{"mLastSrvView", "mCurRootView", "mServedView", "mNextServedView"};
        for (String leakView : leakViews) {
            try {
                Field leakViewField = InputMethodManager.class.getDeclaredField(leakView);
                if (!leakViewField.isAccessible()) {
                    leakViewField.setAccessible(true);
                }
                Object obj = leakViewField.get(imm);
                if (!(obj instanceof View)) continue;
                View view = (View) obj;
                if (view.getRootView() == window.getDecorView().getRootView()) {
                    leakViewField.set(imm, null);
                }
            } catch (Throwable ignore) {/**/}
        }
    }
}
