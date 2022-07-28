package com.dongchao.sample;

import android.content.Intent;

import com.dongchao.erouter.Annotations.Extra;
import com.dongchao.erouter.Annotations.CheckLogin;
import com.dongchao.erouter.Annotations.TargetUrl;
import com.dongchao.sample.ui.PayActivity;
import com.dongchao.sample.ui.SettingActivity;

@CheckLogin
public interface CheckLoginStartActivityApi {
    @TargetUrl(PayActivity.class)
    void startPayActivity(@Extra("key") String key);

    @TargetUrl(SettingActivity.class)
    Intent getSettingActivityIntent(@Extra("key") String key);
}
