package com.dongchao.sample;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.dongchao.erouter.IntentCall;
import com.dongchao.erouter.RouterAdapter;
import com.dongchao.erouter.utils.AppLog;
import com.dongchao.sample.util.Utils;

import java.lang.reflect.Type;

public class MyRouterAdapterFactory extends RouterAdapter.Factory {

    @Override
    public RouterAdapter get(Type returnType) {
        if (getRawType(returnType) != IntentCall.class) {
            return null;
        }
        return (RouterAdapter<Object>) intentCall -> new MyRouterAdapterFactory.MyExecutorIntentCall(intentCall);
    }

    static final class MyExecutorIntentCall implements IntentCall {

        private static final String TAG = "MyExecutorIntentCall";
        private final IntentCall delegate;
        private static Handler mHandler;

        public MyExecutorIntentCall(IntentCall delegate) {
            this.delegate = delegate;
            this.mHandler = new Handler(Looper.getMainLooper());
        }

        @Override
        public Intent getIntent() {
            AppLog.i(TAG, "my delegate getIntent start");
            Intent intent = delegate.getIntent();
            AppLog.i(TAG, "my delegate getIntent end");
            return intent;
        }

        @Override
        public boolean startIntent() {

            AppLog.i(TAG, "my delegate startIntent start");

            if (Utils.isFastDoubleClick()) {
                return false;
            }

            synchronized (MyExecutorIntentCall.class) {
                if (Utils.isFastDoubleClick()) {
                    return false;
                }
            }

            runInMainThread(() -> delegate.startIntent());
            AppLog.i(TAG, "my delegate startIntent end");
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
