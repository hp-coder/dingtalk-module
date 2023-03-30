package com.hp.dingding.pojo.message.interactive.callback;

import com.hp.dingding.component.application.IDingBot;
import lombok.Getter;

import java.util.List;

/**
 * @author hp 2023/3/17
 */
@Getter
public abstract class AbstractDingInteractiveCardCallback implements IDingInteractiveCardCallBack {
    private final String callbackRouteKey;
    private final String callbackUrl;
    private final List<Class<? extends IDingBot>> dingBots;

    public AbstractDingInteractiveCardCallback(String callbackRouteKey, String callbackUrl, List<Class<? extends IDingBot>> dingBots) {
        this.callbackRouteKey = callbackRouteKey;
        this.callbackUrl = callbackUrl;
        this.dingBots = dingBots;
    }
}
