package com.dongchao.sample;

import com.dongchao.erouter.Annotations.Extra;
import com.dongchao.erouter.Annotations.TargetPage;
import com.dongchao.erouter.IntentCall;
import com.dongchao.sample.ui.PayActivity;


public interface StartActivityApi {

    @TargetPage(value = PayActivity.class)
    IntentCall startPayActivity(@Extra("key") String key);

    @TargetPage(value = PayActivity.class)
    IntentCall startSettingActivity(@Extra("key") String key);

//    @TargetUrl(value = PayActivity.class)
//    IntentCall startPayActivity(@Extra("key") String key, @Extra("key2") int key2,
//                             @Extra("key3") double key3, @Extra("key4") boolean key4,
//                             @Extra("key5") byte key5, @Extra("key6") char key6,
//                             @Extra("key7") short key7, @Extra("key8") long key8,
//                             @Extra("key9") float key9, @Extra("key10") User data,
//                             @Extra("key11") Person data2);

}
