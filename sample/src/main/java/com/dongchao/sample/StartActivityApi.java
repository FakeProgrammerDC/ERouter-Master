package com.dongchao.sample;

import android.content.Intent;

import com.dongchao.erouter.Annotations.CheckLogin;
import com.dongchao.erouter.Annotations.Extra;
import com.dongchao.erouter.Annotations.TargetUrl;
import com.dongchao.sample.data.Person;
import com.dongchao.sample.data.User;
import com.dongchao.sample.ui.PayActivity;
import com.dongchao.sample.ui.SettingActivity;


public interface StartActivityApi {

    @TargetUrl(value = PayActivity.class)
    void startPayActivity(@Extra("key") String key);

    @TargetUrl(value = PayActivity.class)
    boolean startPayActivity(@Extra("key") String key, @Extra("key2") int key2,
                             @Extra("key3") double key3, @Extra("key4") boolean key4,
                             @Extra("key5") byte key5, @Extra("key6") char key6,
                             @Extra("key7") short key7, @Extra("key8") long key8,
                             @Extra("key9") float key9, @Extra("key10") User data,
                             @Extra("key11") Person data2);

    @TargetUrl(value = SettingActivity.class, checkLogin = true)
    Intent getSettingActivityIntent(@Extra("key") String key);


}
