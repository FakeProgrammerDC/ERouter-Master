package com.dongchao.erouter;

import android.content.Intent;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class IntentStartActivityMethod extends StartActivityMethod {

    private final Method method;
    private final RequestBody requestBody;

    IntentStartActivityMethod(Method method, RequestBody requestBody) {
        this.method = method;
        this.requestBody = requestBody;
    }

    static IntentStartActivityMethod parseAnnotations(Method method,
                                                      RequestBody requestBody) {
        return new IntentStartActivityMethod(method, requestBody);
    }

    @Override
    Object invoke(Object[] args) {
        JumpActivity jumpActivity = requestBody.createJumpActivity(args);
        Type returnType = method.getReturnType();
        String typeName = returnType.toString();
        if (typeName.contains("Intent")) {
            return jumpActivity.getIntent();
        } else {
            return jumpActivity.start();
        }
    }
}
