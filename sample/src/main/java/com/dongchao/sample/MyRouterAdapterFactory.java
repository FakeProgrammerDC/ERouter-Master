package com.dongchao.sample;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.dongchao.erouter.DefaultAdaptedFactory;
import com.dongchao.erouter.IntentCall;
import com.dongchao.erouter.RouterAdapter;
import com.dongchao.erouter.utils.AppLog;
import com.dongchao.erouter.utils.Utils;

import java.lang.reflect.Type;
import java.util.Date;

public class MyRouterAdapterFactory extends RouterAdapter.Factory {

    @Override
    public RouterAdapter get(Type returnType) {
        if (getRawType(returnType) == Intent.class) {
            return (RouterAdapter<Object>) intentCall -> new MyRouterAdapterFactory.MyExecutorIntentCall(intentCall).getIntent();
        } else {
            return (RouterAdapter<Object>) intentCall -> new MyRouterAdapterFactory.MyExecutorIntentCall(intentCall).startIntent();
        }
    }

    static final class MyExecutorIntentCall implements IntentCall {

        private static final String TAG = "MyExecutorIntentCall";
        //静态代理对象
        private final IntentCall delegate;
        private static Handler mHandler;

        public MyExecutorIntentCall(IntentCall delegate) {
            this.delegate = delegate;
            this.mHandler = new Handler(Looper.getMainLooper());
        }

        @Override
        public Intent getIntent() {
            //执行代理对象
            AppLog.i(TAG, "my delegate getIntent start");
            Intent intent = delegate.getIntent();
            AppLog.i(TAG, "my delegate getIntent end");
            return intent;
        }

        @Override
        public boolean startIntent() {

            AppLog.i(TAG, "my delegate startIntent start");

            if (getIntent() == null) {
                return false;
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
