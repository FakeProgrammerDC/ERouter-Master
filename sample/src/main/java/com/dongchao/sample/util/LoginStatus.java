package com.dongchao.sample.util;

public class LoginStatus {

    private static boolean isLogin = false;

    public static void updateLoginStatus(boolean isLogin) {
        LoginStatus.isLogin = isLogin;
    }

    public static boolean getLoginStatus() {
        return isLogin;
    }
}
