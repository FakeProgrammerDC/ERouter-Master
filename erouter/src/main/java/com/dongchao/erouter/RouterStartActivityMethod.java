package com.dongchao.erouter;

import android.content.Intent;

import com.dongchao.erouter.utils.AppLog;
import com.dongchao.erouter.utils.Utils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

abstract class RouterStartActivityMethod<ReturnT> extends StartActivityMethod<ReturnT> {

    private final ERouter router;
    private final Method method;
    private final RequestParameterFactory requestBody;

    RouterStartActivityMethod(ERouter router, Method method, RequestParameterFactory requestBody) {
        this.router = router;
        this.method = method;
        this.requestBody = requestBody;
    }

    static RouterStartActivityMethod parseAnnotations(ERouter router, Method method,
                                                      RequestParameterFactory requestBody) {
        IntentAdapter intentAdapter = new DefaultIntentAdaptedFactory().get(method.getReturnType());
        return new IntentAdapted(router, method, requestBody, intentAdapter);
    }

    @Override
    final ReturnT invoke(Object[] args) {
        JumpActivity jumpActivity = requestBody.createJumpActivity(args);
//        Type returnType = method.getReturnType();
//        String typeName = returnType.toString();
//        return adapt(jumpActivity);
//        if (typeName.contains("Intent")) {
//            return jumpActivity.getIntent();
//        } else {
//            return jumpActivity.start();
//        }
        return adapt(jumpActivity);
    }

    protected abstract ReturnT adapt(IntentCall intentCall);

    static final class IntentAdapted<ReturnT> extends RouterStartActivityMethod<ReturnT> {

        private IntentAdapter intentAdapter;

        IntentAdapted(ERouter router, Method method, RequestParameterFactory requestBody, IntentAdapter intentAdapter) {
            super(router, method, requestBody);
            this.intentAdapter = intentAdapter;
        }

        @Override
        protected ReturnT adapt(IntentCall intentCall) {
            return (ReturnT) intentAdapter.adapt(intentCall);
        }
    }
}
