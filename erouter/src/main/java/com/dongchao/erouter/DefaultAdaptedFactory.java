package com.dongchao.erouter;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.dongchao.erouter.utils.AppLog;
import com.dongchao.erouter.utils.Utils;

import java.lang.reflect.Type;
import java.util.Date;

public class DefaultAdaptedFactory extends RouterAdapter.Factory {

    @Override
    public RouterAdapter get(Type returnType) {
        if (getRawType(returnType) == Intent.class) {
            return (RouterAdapter<Object>) intentCall -> new ExecutorIntentCall(intentCall).getIntent();
        } else {
            return (RouterAdapter<Object>) intentCall -> new ExecutorIntentCall(intentCall).startIntent();
        }
    }

    static final class ExecutorIntentCall implements IntentCall {

        private static final String TAG = "ExecutorIntentCall";
        //静态代理对象
        private final IntentCall delegate;
        private static Handler mHandler;

        public ExecutorIntentCall(IntentCall delegate) {
            this.delegate = delegate;
            this.mHandler = new Handler(Looper.getMainLooper());
        }

        @Override
        public Intent getIntent() {
            AppLog.i(TAG, "delegate getIntent start");

            if (Utils.isFastDoubleClick()) {
                return null;
            }

            synchronized (ExecutorIntentCall.class) {
                if (Utils.isFastDoubleClick()) {
                    AppLog.i(TAG, "isFastDoubleClick");
                    return null;
                }
            }

            Intent intent = delegate.getIntent();
            AppLog.i(TAG, "delegate getIntent end");
            return intent;
        }

        @Override
        public boolean startIntent() {

            AppLog.i(TAG, "delegate startIntent start");

            if (getIntent() == null) {
                return false;
            }

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
