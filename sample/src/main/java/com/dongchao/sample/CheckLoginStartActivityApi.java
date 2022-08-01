package com.dongchao.sample;

import com.dongchao.erouter.Annotations.CheckLogin;
import com.dongchao.erouter.Annotations.Extra;
import com.dongchao.erouter.Annotations.TargetPage;
import com.dongchao.erouter.IntentCall;
import com.dongchao.sample.ui.PayActivity;
import com.dongchao.sample.ui.SettingActivity;

@CheckLogin
public interface CheckLoginStartActivityApi {

    @TargetPage(PayActivity.class)
    IntentCall startPayActivity(@Extra("key") String key);

    @TargetPage(SettingActivity.class)
    IntentCall startSettingActivity(@Extra("key") String key);
}
