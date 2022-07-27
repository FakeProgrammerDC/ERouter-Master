package com.dongchao.erouter;


import com.dongchao.erouter.utils.Utils;

import java.lang.reflect.Type;

public interface IntentAdapter<T> {

    T adapt(IntentCall intentCall);

    abstract class Factory {
        public abstract IntentAdapter get(Type returnType);

        /**
         * 提取原始类型
         * Intent @return {@code Intent.class}
         */
        protected static Class<?> getRawType(Type type) {
            return Utils.getRawType(type);
        }
    }
}
