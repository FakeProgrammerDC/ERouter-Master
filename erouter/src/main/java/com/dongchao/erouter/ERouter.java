package com.dongchao.erouter;


import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.dongchao.erouter.Annotations.Extra;
import com.dongchao.erouter.Annotations.TargetUrl;
import com.dongchao.erouter.utils.AppLog;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;


public class ERouter {

    private static final String TAG = "ERouter";

    private final Class<?> loginActivityClass;
    private final LoginLogic loginLogic;

    ERouter(Class<?> loginActivityClass, LoginLogic loginLogic) {
        this.loginActivityClass = loginActivityClass;
        this.loginLogic = loginLogic;
    }

    public <T> T create(final Class<T> service) {
        if (!service.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        }
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service}, (proxy, method, args) -> loadStartActivityMethod(method, args));
    }

    Boolean loadStartActivityMethod(Method method, Object[] args) {
            return null;
    }

    public interface LoginLogic {
        boolean isLoginLogic();
    }

    public static final class Builder {
        private Class<?> loginActivityClass;
        private LoginLogic loginLogic;

        public Builder() {
        }

        public Builder setLoginActivityClass(Class<?> loginActivityClass) {
            this.loginActivityClass = loginActivityClass;
            return this;
        }

        public Builder setLoginLogic(LoginLogic loginLogic) {
            this.loginLogic = loginLogic;
            return this;
        }

        public ERouter build() {
            return new ERouter(loginActivityClass, loginLogic);
        }
    }
}
