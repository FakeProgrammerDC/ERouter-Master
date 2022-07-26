package com.dongchao.erouter;

import com.dongchao.erouter.Annotations.TargetUrl;
import com.dongchao.erouter.utils.AppLog;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class StartMethodAnnotation {

    private Class<?> targetClass;
    private boolean isLogin = false;

    void parseAnnotations(Method method) {

    }
}


