package com.dongchao.sample;


import com.dongchao.erouter.ERouter;
import com.dongchao.sample.ui.LoginActivity;
import com.dongchao.sample.util.LoginStatus;

public class StartActivityUtil {

    private StartActivityUtil() {
        ERouter router = new ERouter.Builder()
                .setLoginActivityClass(LoginActivity.class)
                .setLoginLogic(() -> LoginStatus.getLoginStatus())
                //.addRouterAdapterFactory(new MyRouterAdapterFactory())
                .build();
        startActivity = router.create(StartActivityApi.class);
        checkLoginStartActivity = router.create(CheckLoginStartActivityApi.class);
    }

    private StartActivityApi startActivity;
    private CheckLoginStartActivityApi checkLoginStartActivity;

    public static StartActivityApi getInstance() {
        return Holder.INSTANCE.startActivity;
    }

    public static CheckLoginStartActivityApi getCheckLoginInstance() {
        return Holder.INSTANCE.checkLoginStartActivity;
    }

    static final class Holder {
        private static final StartActivityUtil INSTANCE = new StartActivityUtil();
    }
}
