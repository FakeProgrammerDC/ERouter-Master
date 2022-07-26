package com.dongchao.erouter;

import androidx.annotation.Nullable;

import java.lang.reflect.Method;

abstract class StartActivityMethod<T> {

    static StartActivityMethod parseAnnotations(Method method) {

        RequestBody requestBody = RequestBody.parseAnnotations(method);

        return null;
    }

    //传递方法参数值
    abstract T invoke(Object[] args);
}
