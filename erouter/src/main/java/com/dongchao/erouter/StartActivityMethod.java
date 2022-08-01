package com.dongchao.erouter;


import java.lang.reflect.Method;

abstract class StartActivityMethod<T> {

    static StartActivityMethod parseAnnotations(ERouter router, Method method) {

        RequestParameterFactory requestBody = RequestParameterFactory.parseAnnotations(router, method);

        return RouterStartActivityMethod.parseAnnotations(router, method, requestBody);
    }

    abstract T invoke(TypeParameter typeParameter, Object[] args);
}
