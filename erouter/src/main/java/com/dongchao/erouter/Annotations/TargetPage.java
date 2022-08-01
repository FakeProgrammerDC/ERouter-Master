package com.dongchao.erouter.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetPage {

    //目标Activity
    Class<?> value();

    //如果使用了 CheckLogin 那么这个不生效
    boolean checkLogin() default false;
}
