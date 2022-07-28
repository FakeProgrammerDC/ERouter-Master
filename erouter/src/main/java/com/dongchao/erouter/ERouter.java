package com.dongchao.erouter;


import com.dongchao.erouter.Annotations.CheckLogin;
import com.dongchao.erouter.utils.AppLog;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


public class ERouter {

    //private static final String TAG = "ERouter";

    private final Map<Method, StartActivityMethod<?>> startActivityMethodCache = new ConcurrentHashMap<>();
    private final Map<Class, TypeParameter> startActivityInterfaceCache = new ConcurrentHashMap<>();

    final List<RouterAdapter.Factory> routerAdapterFactories;
    final Class<?> loginActivityClass;
    final LoginLogic loginLogic;

    ERouter(Class<?> loginActivityClass, LoginLogic loginLogic, List<RouterAdapter.Factory> routerAdapterFactories) {
        this.loginActivityClass = loginActivityClass;
        this.loginLogic = loginLogic;
        this.routerAdapterFactories = routerAdapterFactories;
    }

    public <T> T create(final Class<T> service) {
        if (!service.isInterface()) {
            throw new IllegalArgumentException("API 声明必须是接口");
        }

        parseTypeAnnotations(service);

        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                (proxy, method, args) -> loadStartActivityMethod(method).invoke(getTypeParameter(proxy), args));
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

    public RouterAdapter<?> routerAdapter(Type returnType) {
        for (int i = 0; i < routerAdapterFactories.size(); i++) {
            RouterAdapter<?> adapter = routerAdapterFactories.get(i).get(returnType);
            if (adapter != null) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("没有合适的 routerAdapter");
    }

    void parseTypeAnnotations(final Class<?> service) {
        //解析接口注释
        Annotation[] annotations = service.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof CheckLogin) {
                startActivityInterfaceCache.put(service, new TypeParameter(true));
            }
        }
    }

    TypeParameter getTypeParameter(Object proxy) {
        Class service = proxy.getClass().getInterfaces()[0];
        TypeParameter typeParameter = startActivityInterfaceCache.get(service);
        //AppLog.i(TAG, "开启校验");
        return typeParameter;
    }

    public interface LoginLogic {
        boolean isLoginLogic();
    }

    public static final class Builder {
        private Class<?> loginActivityClass;
        private LoginLogic loginLogic;
        private final List<RouterAdapter.Factory> routerAdapterFactories = new ArrayList<>();

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

        public Builder addRouterAdapterFactory(RouterAdapter.Factory factory) {
            routerAdapterFactories.add(Objects.requireNonNull(factory, "factory == null"));
            return this;
        }

        public ERouter build() {
            routerAdapterFactories.add(new DefaultAdaptedFactory());
            return new ERouter(loginActivityClass, loginLogic, routerAdapterFactories);
        }
    }
}
