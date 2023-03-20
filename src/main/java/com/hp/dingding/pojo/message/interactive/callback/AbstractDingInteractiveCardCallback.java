package com.hp.dingding.pojo.message.interactive.callback;

import com.hp.dingding.component.application.IDingBot;
import lombok.Getter;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.*;

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

    @PostConstruct
    private void validate(){
        Assert.hasText(callbackRouteKey,"回调路由key不能为空");
        Assert.hasText(callbackUrl,"回调接口不能为空");
        Assert.isTrue(!CollectionUtils.isEmpty(dingBots),"至少需要一个机器人应用");
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
