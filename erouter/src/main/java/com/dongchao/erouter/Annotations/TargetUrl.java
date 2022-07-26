package com.dongchao.erouter.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetUrl {

    //目标Activity
    Class<?> value();

    //默认未登录 -> 跳转到登录页面
    boolean isLogin() default true;
}
