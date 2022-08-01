package com.dongchao.erouter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.dongchao.erouter.utils.AppLog;
import com.dongchao.erouter.utils.Utils;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class JumpIntent implements IntentCall {

    private static final String TAG = "JumpActivity";

    private final Context currentContext;
    private final Class<?> loginActivityClass;
    private final Class<?> targetClass;
    private final boolean isCheckLogin;
    private final ERouter.LoginLogic loginLogic;
    private final Map<String, Object> parameterMap;
    private Intent intent;

    JumpIntent(Class<?> loginActivityClass, Class<?> targetClass, boolean isCheckLogin, ERouter.LoginLogic loginLogic, Map<String, Object> parameterMap) {
        this.currentContext = Utils.getTopActivity();
        this.loginActivityClass = loginActivityClass;
        this.targetClass = targetClass;
        this.isCheckLogin = isCheckLogin;
        this.loginLogic = loginLogic;
        this.parameterMap = parameterMap;
    }

    public Intent getIntent() {
        AppLog.i(TAG, "getIntent start");

        if (currentContext == null) {
            AppLog.e(TAG, "currentContext == null");
            return null;
        }

        if (intent == null) {
            if (isCheckLogin) {
                intent = getIntentLoginOrTarget();
            } else {
                intent = new Intent(currentContext, targetClass);
            }
            //设置参数
            putExtra(intent);
        }
        AppLog.i(TAG, "getIntent end");
        return intent;
    }

    @Override
    public boolean startIntent() {
        AppLog.i(TAG, "startIntent start");

        Intent intent = getIntent();

        if (intent == null) {
            AppLog.e(TAG, "启动异常 intent == null");
            return false;
        }

        currentContext.startActivity(intent);
        AppLog.i(TAG, "startIntent end");
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

    private Intent getIntentLoginOrTarget() {
        Objects.requireNonNull(loginLogic, "loginLogic == null");
        Objects.requireNonNull(loginActivityClass, "loginActivityClass == null");
        return new Intent(currentContext, loginLogic.isLoginLogic() ? targetClass : loginActivityClass);
    }

    public static final class Builder {
        private Class<?> loginActivityClass;
        private Class<?> targetClass;
        private boolean isCheckLogin;
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

        public Builder setCheckLogin(boolean isCheckLogin) {
            this.isCheckLogin = isCheckLogin;
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

        public JumpIntent build() {
            return new JumpIntent(loginActivityClass, targetClass, isCheckLogin, loginLogic, parameterMap);
        }
    }
}
