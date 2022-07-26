package com.dongchao.erouter;

import android.text.TextUtils;
import android.util.ArrayMap;

import androidx.annotation.Nullable;

import com.dongchao.erouter.Annotations.Extra;
import com.dongchao.erouter.Annotations.TargetUrl;
import com.dongchao.erouter.utils.AppLog;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RequestBody {

    private static final String TAG = "RequestBody";

    static RequestBody parseAnnotations(ERouter router, Method method) {
        return new Builder(router, method).build();
    }

    private final ERouter.LoginLogic loginLogic;
    private final Class<?> loginActivityClass;
    private final Method method;
    private final Class<?> targetClass;
    private final boolean isLogin;
    private final String[] parameterKeys;

    private RequestBody(Builder builder) {
        loginLogic = builder.loginLogic;
        loginActivityClass = builder.loginActivityClass;
        method = builder.method;
        targetClass = builder.targetClass;
        isLogin = builder.isLogin;
        parameterKeys = builder.parameterKeys;
    }

    JumpActivity createJumpActivity(Object[] args) {

        int argumentCount = args == null ? 0 : args.length;

        if (parameterKeys.length != argumentCount) {
            throw new IllegalArgumentException("注解和方法参数不匹配");
        }
        Map<String, Object> parameterMap = new ArrayMap(argumentCount);

        for (int i = 0; i < argumentCount; i++) {
            parameterMap.put(parameterKeys[i], args[i]);
            AppLog.i(TAG, "参数注解 key = %s , 参数 value = %s1", parameterKeys[i], args[i]);
        }

        return new JumpActivity.Builder()
                .setLoginActivityClass(loginActivityClass)
                .setTargetClass(targetClass)
                .setLogin(isLogin)
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
        boolean isLogin = false;
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

        RequestBody build() {
            //方法注解
            for (Annotation annotation : methodAnnotations) {
                if (annotation instanceof TargetUrl) {
                    TargetUrl targetUrl = (TargetUrl) annotation;
                    targetClass = targetUrl.value();
                    isLogin = targetUrl.isLogin();
                    AppLog.i(TAG, "方法注解 = %s 目标页面 = %s1", method.getName(), targetClass.getName());
                }
            }

            //参数注解
            for (int p = 0; p < parameterCount; p++) {
                parameterKeys[p] = parseParameter(parameterAnnotationsArray[p]);
            }

            return new RequestBody(this);
        }

        String parseParameter(@Nullable Annotation[] annotations) {
            String result = null;
            if (annotations != null) {
                for (Annotation annotation : annotations) {
                    String key = parseParameterAnnotation(annotation);
                    if (TextUtils.isEmpty(key)) {
                        continue;
                    }

                    if (!TextUtils.isEmpty(result)) {
                        throw new RuntimeException("有多个注解, 只允许存在一个");
                    }
                    result = key;
                }
            }

            if (TextUtils.isEmpty(result)) {
                throw new RuntimeException("路由注解不能为空或者内容不能为空");
            }
            return result;
        }

        String parseParameterAnnotation(@Nullable Annotation annotation) {
            if (annotation instanceof Extra) {
                Extra extra = (Extra) annotation;
                AppLog.i(TAG, "key = %s ", extra.value());
                return extra.value();
            }
            return null;
        }
    }
}
