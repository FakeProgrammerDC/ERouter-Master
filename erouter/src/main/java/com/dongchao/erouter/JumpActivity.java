package com.dongchao.erouter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;

import com.dongchao.erouter.utils.AppLog;
import com.dongchao.erouter.utils.Utils;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class JumpActivity implements IntentCall{

    private static final String TAG = "JumpActivity";

    private final Context currentContext;
    private final Class<?> loginActivityClass;
    private final Class<?> targetClass;
    private final boolean isLogin;
    private final ERouter.LoginLogic loginLogic;
    private final Map<String, Object> parameterMap;

    private static Handler mHandler;

    JumpActivity(Class<?> loginActivityClass, Class<?> targetClass, boolean isLogin, ERouter.LoginLogic loginLogic, Map<String, Object> parameterMap) {
        Objects.requireNonNull(Utils.getTopActivity(), "TopActivity == null");
        this.currentContext = Utils.getTopActivity();
        this.loginActivityClass = loginActivityClass;
        this.targetClass = targetClass;
        this.isLogin = isLogin;
        this.loginLogic = loginLogic;
        this.parameterMap = parameterMap;
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    public Intent getIntent() {
        AppLog.i(TAG, "in getIntent----- %s", "start");
        //synchronized (currentContext) {
            if (Utils.isFastDoubleClick()) {
                AppLog.i(TAG, "触发了%s", "Utils.isFastDoubleClick()");
                return null;
            }
        //}

        Intent intent;

        if (loginActivityClass != null && isLogin) {
            //AppLog.i(TAG, "根据用户状态判断是否需要跳转登录页面");
            intent = getIntentLoginOrTarget();
        } else {
            intent = new Intent(currentContext, targetClass);
        }

        //设置参数
        putExtra(intent);
        AppLog.i(TAG, "in getIntent----- %s", "end");
        return intent;
    }

    public boolean start() {
        AppLog.i(TAG, "启动了-----%s", "start");

        final Intent intent = getIntent();
        if (intent == null) {
            return false;
        }

        //主线程跳转
        runInMainThread(() -> {
            AppLog.i(TAG, "启动成功-----%s", "end");
            currentContext.startActivity(intent);
        });

        return true;
    }

    private void putExtra(Intent intent) {
        for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
            String entryKey = entry.getKey();
            Object entryValue = entry.getValue();
            if (entryValue instanceof String) {
                intent.putExtra(entryKey, entryValue.toString());
            } else if (entryValue instanceof Integer) {
                intent.putExtra(entry.getKey(), (int) entry.getValue());
            } else if (entryValue instanceof Double) {
                intent.putExtra(entry.getKey(), (Double) entry.getValue());
            } else if (entryValue instanceof Boolean) {
                intent.putExtra(entry.getKey(), (Boolean) entry.getValue());
            } else if (entryValue instanceof Byte) {
                intent.putExtra(entry.getKey(), (Byte) entry.getValue());
            } else if (entryValue instanceof Character) {
                intent.putExtra(entry.getKey(), (Character) entry.getValue());
            } else if (entryValue instanceof Short) {
                intent.putExtra(entry.getKey(), (Short) entry.getValue());
            } else if (entryValue instanceof Long) {
                intent.putExtra(entry.getKey(), (Long) entry.getValue());
            } else if (entryValue instanceof Float) {
                intent.putExtra(entry.getKey(), (Float) entry.getValue());
            } else if (entryValue instanceof Serializable) {
                intent.putExtra(entry.getKey(), (Serializable) entry.getValue());
            } else if (entryValue instanceof Parcelable) {
                intent.putExtra(entry.getKey(), (Parcelable) entry.getValue());
            } else {
                intent.putExtra(entryKey, entryValue.toString());
            }
        }
    }

    private void runInMainThread(Runnable runnable) {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            mHandler.post(runnable);
        } else {
            runnable.run();
        }
    }

    private Intent getIntentLoginOrTarget() {
        Objects.requireNonNull(loginLogic, "loginLogic == null");
        return new Intent(currentContext, loginLogic.isLoginLogic() ? targetClass : loginActivityClass);
    }

    public static final class Builder {
        private Class<?> loginActivityClass;
        private Class<?> targetClass;
        private boolean isLogin;
        private ERouter.LoginLogic loginLogic;
        private Map<String, Object> parameterMap;

        public Builder() {
        }

        public Builder setLoginActivityClass(Class<?> loginActivityClass) {
            this.loginActivityClass = loginActivityClass;
            return this;
        }

        public Builder setTargetClass(Class<?> targetClass) {
            this.targetClass = targetClass;
            return this;
        }

        public Builder setLogin(boolean isLogin) {
            this.isLogin = isLogin;
            return this;
        }

        public Builder setLoginLogic(ERouter.LoginLogic loginLogic) {
            this.loginLogic = loginLogic;
            return this;
        }

        public Builder setParameterMap(Map<String, Object> parameterMap) {
            this.parameterMap = parameterMap;
            return this;
        }

        public JumpActivity build() {
            return new JumpActivity(loginActivityClass, targetClass, isLogin, loginLogic, parameterMap);
        }
    }
}
