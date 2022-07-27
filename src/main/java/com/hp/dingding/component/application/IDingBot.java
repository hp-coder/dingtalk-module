package com.hp.dingding.component.application;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 钉钉企业内部机器人应用
 *
 * @Author: HP
 */
public interface IDingBot extends IDingApp {

    ConcurrentHashMap<String,IDingBot> CACHE = new ConcurrentHashMap<>(16);
}
