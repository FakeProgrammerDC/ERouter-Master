package com.dongchao.sample;

import android.content.Intent;

import com.dongchao.erouter.Annotations.Extra;
import com.dongchao.erouter.Annotations.TargetUrl;
import com.dongchao.sample.data.Person;
import com.dongchao.sample.data.User;
import com.dongchao.sample.ui.PayActivity;
import com.dongchao.sample.ui.SettingActivity;

public interface StartActivityApi {

    @TargetUrl(value = SettingActivity.class, isLogin = false)
    Intent startSettingActivity(@Extra("test") String str);

    @TargetUrl(value = PayActivity.class, isLogin = false)
    boolean startPayActivity(@Extra("key") String key, @Extra("key2") int key2,
                             @Extra("key3") double key3, @Extra("key4") boolean key4,
                             @Extra("key5") byte key5, @Extra("key6") char key6,
                             @Extra("key7") short key7, @Extra("key8") long key8,
                             @Extra("key9") float key9, @Extra("key10") User data,
                             @Extra("key11") Person data2);

    @TargetUrl(value = SettingActivity.class)
    void startLoginActivity(@Extra("test") String str);
}
