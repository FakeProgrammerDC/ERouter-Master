package com.dongchao.erouter;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ERouter {
    //private static final String TAG = "ERouter";

    private final Map<Method, StartActivityMethod<?>> startActivityMethodCache = new ConcurrentHashMap<>();

    final Class<?> loginActivityClass;
    final LoginLogic loginLogic;

    ERouter(Class<?> loginActivityClass, LoginLogic loginLogic) {
        this.loginActivityClass = loginActivityClass;
        this.loginLogic = loginLogic;
    }

    public <T> T create(final Class<T> service) {
        if (!service.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        }
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                (proxy, method, args) -> loadStartActivityMethod(method).invoke(args));
    }

    StartActivityMethod loadStartActivityMethod(Method method) {

        StartActivityMethod result = startActivityMethodCache.get(method);
        if (result != null) {
            return result;
        }

        synchronized (startActivityMethodCache) {
            result = startActivityMethodCache.get(method);
            if (result == null) {
                result = StartActivityMethod.parseAnnotations(this, method);
                startActivityMethodCache.put(method, result);
            }
        }

        return result;
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
