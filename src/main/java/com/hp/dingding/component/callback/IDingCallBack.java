package com.hp.dingding.component.callback;

import com.hp.dingding.component.application.IDingApp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 互动卡片回调地址
 *
 * @Author: HP
 */
public interface IDingCallBack {

    Set<IDingCallBack> registry = new HashSet<>();

    String getCallbackUrl();

    String getCallbackRouteKey();

    List<Class<? extends IDingApp>> getDingApps();

    static void register(IDingCallBack callBack) {
        registry.add(callBack);
    }
}
