package com.hp.dingding.pojo.message.interactive.callback;

import com.hp.dingding.component.application.IDingBot;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author hp 2023/3/17
 */
@Getter
public abstract class AbstractDingInteractiveCardCallback implements IDingInteractiveCardCallBack {
    private static final Set<IDingInteractiveCardCallBack> CALLBACK_HOLDER = new HashSet<>();

    private final String callbackRouteKey;
    private final String callbackUrl;
    private final List<Class<? extends IDingBot>> dingBots;

    public AbstractDingInteractiveCardCallback(String callbackRouteKey, String callbackUrl, List<Class<? extends IDingBot>> dingBots) {
        this.callbackRouteKey = callbackRouteKey;
        this.callbackUrl = callbackUrl;
        this.dingBots = dingBots;
    }

    public static List<IDingInteractiveCardCallBack> callbacks() {
        return new ArrayList<>(CALLBACK_HOLDER);
    }

    protected void register(IDingInteractiveCardCallBack callBack) {
        CALLBACK_HOLDER.add(callBack);
    }

    @Override
    public void afterSingletonsInstantiated() {
        register(this);
    }
}
