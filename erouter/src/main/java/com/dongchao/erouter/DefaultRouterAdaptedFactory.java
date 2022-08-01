package com.dongchao.erouter;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.dongchao.erouter.utils.AppLog;

import java.lang.reflect.Type;

public class DefaultRouterAdaptedFactory extends RouterAdapter.Factory {

    @Override
    public RouterAdapter get(Type returnType) {
        if (getRawType(returnType) == IntentCall.class) {
            return (RouterAdapter<Object>) intentCall -> new ExecutorIntentCall(intentCall);
        }
        return null;
    }

    static final class ExecutorIntentCall implements IntentCall {

        private static final String TAG = "ExecutorIntentCall";
        private final IntentCall delegate;
        private static Handler mHandler;

        public ExecutorIntentCall(IntentCall delegate) {
            this.delegate = delegate;
            this.mHandler = new Handler(Looper.getMainLooper());
        }

        @Override
        public Intent getIntent() {
            AppLog.i(TAG, "delegate getIntent start");
            Intent intent = delegate.getIntent();
            AppLog.i(TAG, "delegate getIntent end");
            return intent;
        }

        @Override
        public boolean startIntent() {
            AppLog.i(TAG, "delegate startIntent start");
            runInMainThread(() -> delegate.startIntent());
            AppLog.i(TAG, "delegate startIntent end");
            return true;
        }

        private void runInMainThread(Runnable runnable) {
            if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
                mHandler.post(runnable);
            } else {
                runnable.run();
            }
        }

    }
}
