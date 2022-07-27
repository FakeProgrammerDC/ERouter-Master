package com.dongchao.sample;


import com.dongchao.erouter.ERouter;
import com.dongchao.sample.ui.LoginActivity;

public class StartActivityUtil {

    private StartActivityUtil() {
        ERouter router = new ERouter.Builder()
                .setLoginActivityClass(LoginActivity.class)
                .setLoginLogic(() -> false).build();
        startActivity = router.create(StartActivityApi.class);
    }

    private StartActivityApi startActivity;

    public static StartActivityApi getInstance() {
        return Holder.INSTANCE.startActivity;
    }

    static final class Holder {
        private static final StartActivityUtil INSTANCE = new StartActivityUtil();
    }
}
