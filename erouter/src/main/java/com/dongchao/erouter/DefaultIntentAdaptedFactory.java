package com.dongchao.erouter;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.dongchao.erouter.utils.AppLog;

import java.lang.reflect.Type;

public class DefaultIntentAdaptedFactory extends IntentAdapter.Factory {

    @Override
    public IntentAdapter<?> get(Type returnType) {
        if (getRawType(returnType) != Intent.class) {
            return null;
        }
        return (IntentAdapter<Object>) intentCall -> new ExecutorIntentCall(intentCall).getIntent();
    }

    static final class ExecutorIntentCall implements IntentCall {

        //静态代理对象
        private final IntentCall delegate;

        public ExecutorIntentCall(IntentCall delegate) {
            this.delegate = delegate;
        }

        @Override
        public Intent getIntent() {
//            执行代理对象
            AppLog.i("ExecutorIntentCall", "delegate start");
            Intent intent = delegate.getIntent();
            AppLog.i("ExecutorIntentCall", "delegate end");
            return intent;
        }
    }
}
