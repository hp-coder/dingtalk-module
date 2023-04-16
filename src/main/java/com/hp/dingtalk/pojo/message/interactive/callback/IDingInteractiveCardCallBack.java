package com.hp.dingtalk.pojo.message.interactive.callback;

import com.hp.dingtalk.component.application.IDingBot;

import java.util.List;

/**
 * 互动卡片回调地址
 * @author hp
 */
public interface IDingInteractiveCardCallBack {
    /**
     * 互动卡片回调接口的URL
     * @return URL
     */
    String getCallbackUrl();

    /**
     * 钉钉注册卡片和发送发卡片时对应URL的路由Key
     * @return 路由key
     */
    String getCallbackRouteKey();

    /**
     * 需要注册的应用
     * @return 机器人应用
     */
    List<Class<? extends IDingBot>> getDingBots();
}
