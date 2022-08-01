package com.dongchao.erouter;

import java.lang.reflect.Method;

abstract class RouterStartActivityMethod<ReturnT> extends StartActivityMethod<ReturnT> {


    private final RequestParameterFactory requestBody;

    RouterStartActivityMethod(RequestParameterFactory requestBody) {
        this.requestBody = requestBody;
    }

    static RouterStartActivityMethod parseAnnotations(ERouter router, Method method,
                                                      RequestParameterFactory requestBody) {

        RouterAdapter routerAdapter = router.routerAdapter(method.getReturnType());
        return new IntentAdapted(requestBody, routerAdapter);
    }

    @Override
    final ReturnT invoke(TypeParameter typeParameter, Object[] args) {
        JumpIntent jumpIntent = requestBody.createJumpIntent(typeParameter, args);
        return adapt(jumpIntent);
    }

    protected abstract ReturnT adapt(IntentCall intentCall);

    static final class IntentAdapted<ReturnT> extends RouterStartActivityMethod<ReturnT> {

        private RouterAdapter routerAdapter;

        IntentAdapted(RequestParameterFactory requestBody, RouterAdapter routerAdapter) {
            super(requestBody);
            this.routerAdapter = routerAdapter;
        }

        @Override
        protected ReturnT adapt(IntentCall intentCall) {
            return (ReturnT) routerAdapter.adapt(intentCall);
        }
    }
}
