package com.hp.dingding.pojo.message.interactive.callback;

import com.hp.dingding.component.application.IDingBot;
import org.springframework.beans.factory.SmartInitializingSingleton;

import java.util.List;

/**
 * 互动卡片回调地址
 */
public interface IDingInteractiveCardCallBack extends SmartInitializingSingleton {
    String getCallbackUrl();
    String getCallbackRouteKey();
    List<Class<? extends IDingBot>> getDingBots();
}
