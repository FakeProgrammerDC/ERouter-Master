package com.dongchao.erouter;

import android.text.TextUtils;

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

    static RequestBody parseAnnotations(Method method) {
        return new Builder(method).build();
    }

    private RequestBody() {
    }

    static final class Builder {

        Method method;
        Annotation[] methodAnnotations;
        Annotation[][] parameterAnnotationsArray;
        Class<?> targetClass;
        boolean isLogin = false;
        String[] parameterKeys;
        int parameterCount;

        private Builder(Method method) {
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
                parseParameter(parameterAnnotationsArray[p]);
                //添加数据
//                parameterMap.put(key, args[p]);
//                AppLog.i(TAG, "参数注解 key = %s , 参数 value = %s1", key, args[p]);
            }

            return new RequestBody();
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
