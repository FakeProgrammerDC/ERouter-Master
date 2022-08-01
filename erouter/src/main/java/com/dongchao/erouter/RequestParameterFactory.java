package com.dongchao.erouter;

import android.text.TextUtils;
import android.util.ArrayMap;

import com.dongchao.erouter.Annotations.Extra;
import com.dongchao.erouter.Annotations.TargetPage;
import com.dongchao.erouter.utils.AppLog;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

final class RequestParameterFactory {

    private static final String TAG = "RequestBody";

    static RequestParameterFactory parseAnnotations(ERouter router, Method method) {
        return new Builder(router, method).build();
    }

    private final ERouter.LoginLogic loginLogic;
    private final Class<?> loginActivityClass;
    //private final Method method;
    private final Class<?> targetClass;
    private final boolean checkLogin;
    private final String[] parameterKeys;

    private RequestParameterFactory(Builder builder) {
        loginLogic = builder.loginLogic;
        loginActivityClass = builder.loginActivityClass;
        //method = builder.method;
        targetClass = builder.targetClass;
        checkLogin = builder.checkLogin;
        parameterKeys = builder.parameterKeys;
    }

    JumpIntent createJumpIntent(TypeParameter typeParameter, Object[] args) {

        boolean isCheckLogin;

        if (typeParameter != null) {
            isCheckLogin = typeParameter.isCheckLogin ? true : checkLogin;
        } else {
            isCheckLogin = checkLogin;
        }

        int argumentCount = args == null ? 0 : args.length;

        if (parameterKeys.length != argumentCount) {
            throw new IllegalArgumentException("注解和方法参数不匹配");
        }
        Map<String, Object> parameterMap = new ArrayMap(argumentCount);

        for (int i = 0; i < argumentCount; i++) {
            parameterMap.put(parameterKeys[i], args[i]);
            AppLog.i(TAG, "参数注解 key = %s , 参数 value = %s", parameterKeys[i], args[i]);
        }

        return new JumpIntent.Builder()
                .setLoginActivityClass(loginActivityClass)
                .setTargetClass(targetClass)
                .setCheckLogin(isCheckLogin)
                .setLoginLogic(loginLogic)
                .setParameterMap(parameterMap)
                .build();
    }

    static final class Builder {
        ERouter router;
        ERouter.LoginLogic loginLogic;
        Class<?> loginActivityClass;
        Method method;
        Annotation[] methodAnnotations;
        Annotation[][] parameterAnnotationsArray;
        Class<?> targetClass;
        boolean checkLogin;
        String[] parameterKeys;
        int parameterCount;

        private Builder(ERouter router, Method method) {
            this.router = router;
            this.loginLogic = router.loginLogic;
            this.loginActivityClass = router.loginActivityClass;
            this.method = method;
            this.methodAnnotations = method.getAnnotations();
            this.parameterAnnotationsArray = method.getParameterAnnotations();
            this.parameterKeys = new String[parameterAnnotationsArray.length];
            this.parameterCount = parameterAnnotationsArray.length;
        }

        RequestParameterFactory build() {
            //方法注解解析
            for (Annotation annotation : methodAnnotations) {
                if (annotation instanceof TargetPage) {
                    TargetPage targetPage = (TargetPage) annotation;
                    targetClass = targetPage.value();
                    checkLogin = targetPage.checkLogin();
                    AppLog.i(TAG, "方法注解 = %s 目标页面 = %s", method.getName(), targetClass.getName());
                }
            }

            //参数注解解析
            for (int p = 0; p < parameterCount; p++) {
                parameterKeys[p] = parseParameter(parameterAnnotationsArray[p]);
            }

            return new RequestParameterFactory(this);
        }

        String parseParameter(Annotation[] annotations) {
            String result = null;
            if (annotations != null) {
                for (Annotation annotation : annotations) {
                    String key = parseParameterAnnotation(annotation);
                    if (TextUtils.isEmpty(key)) {
                        continue;
                    }

                    if (!TextUtils.isEmpty(result)) {
                        throw new RuntimeException("当前参数有多个注解, 只允许存在一个");
                    }
                    result = key;
                }
            }

            if (TextUtils.isEmpty(result)) {
                throw new RuntimeException("当前参数不能无注解或注解内容为空");
            }
            return result;
        }

        String parseParameterAnnotation(Annotation annotation) {
            if (annotation instanceof Extra) {
                Extra extra = (Extra) annotation;
                AppLog.i(TAG, "key = %s ", extra.value());
                return extra.value();
            }
            return null;
        }
    }
}
