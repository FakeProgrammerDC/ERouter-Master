package com.dongchao.erouter;

import java.lang.reflect.Method;

abstract class RouterStartActivityMethod<ReturnT> extends StartActivityMethod<ReturnT> {


    private final RequestParameterFactory requestBody;

    RouterStartActivityMethod(RequestParameterFactory requestBody) {
        this.requestBody = requestBody;
    }

    static RouterStartActivityMethod parseAnnotations(ERouter router, Method method,
                                                      RequestParameterFactory requestBody) {


        RouterAdapter intentAdapter = router.routerAdapter(method.getReturnType());
        return new IntentAdapted(requestBody, intentAdapter);
    }

    @Override
    final ReturnT invoke(TypeParameter typeParameter, Object[] args) {
        JumpActivity jumpActivity = requestBody.createJumpActivity(typeParameter, args);
        return adapt(jumpActivity);
    }

    protected abstract ReturnT adapt(IntentCall intentCall);

    static final class IntentAdapted<ReturnT> extends RouterStartActivityMethod<ReturnT> {

        private RouterAdapter intentAdapter;

        IntentAdapted(RequestParameterFactory requestBody, RouterAdapter intentAdapter) {
            super(requestBody);
            this.intentAdapter = intentAdapter;
        }

        @Override
        protected ReturnT adapt(IntentCall intentCall) {
            return (ReturnT) intentAdapter.adapt(intentCall);
        }
    }
}
