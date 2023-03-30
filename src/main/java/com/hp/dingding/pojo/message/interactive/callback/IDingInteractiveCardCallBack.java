package com.hp.dingding.pojo.message.interactive.callback;

import com.hp.dingding.component.application.IDingBot;

import java.util.List;

/**
 * 互动卡片回调地址
 * @author hp
 */
public interface IDingInteractiveCardCallBack{
    String getCallbackUrl();
    String getCallbackRouteKey();
    List<Class<? extends IDingBot>> getDingBots();
}
