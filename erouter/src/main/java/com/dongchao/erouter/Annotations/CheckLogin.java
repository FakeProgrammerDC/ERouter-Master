package com.dongchao.erouter.Annotations;

import com.dongchao.erouter.ERouter;
import com.dongchao.erouter.ERouter.LoginLogic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckLogin {
}
