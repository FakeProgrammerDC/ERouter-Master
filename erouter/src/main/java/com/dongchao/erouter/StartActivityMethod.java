package com.dongchao.erouter;

import androidx.annotation.Nullable;

import java.lang.reflect.Method;

abstract class StartActivityMethod<T> {

    static StartActivityMethod parseAnnotations(ERouter router, Method method) {

        RequestBody requestBody = RequestBody.parseAnnotations(router, method);

        return IntentStartActivityMethod.parseAnnotations(method, requestBody);
    }

    //传递方法参数值
    abstract T invoke(Object[] args);
}
