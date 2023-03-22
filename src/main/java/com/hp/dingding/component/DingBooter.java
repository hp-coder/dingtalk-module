package com.hp.dingding.component;

import com.hp.dingding.component.application.IDingBot;
import com.hp.dingding.component.factory.DingAppFactory;
import com.hp.dingding.pojo.message.interactive.callback.AbstractDingInteractiveCardCallback;
import com.hp.dingding.service.api.IDingInteractiveMessageHandler;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * springboot ready后 调用注册
 * @attention 这里不做双重检查，直接forceUpdate，必须保证注册的key+URL生产和开发环境要完全隔离，避免注册信息交叉污染线上
 * @author hp
 */
@Slf4j
@Component
public class DingBooter implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(@NotNull ApplicationReadyEvent applicationReadyEvent) {
        AbstractDingInteractiveCardCallback
                .callbacks()
                .forEach(callBack ->
                        callBack.getDingBots()
                                .forEach(clazz -> {
                                    final IDingBot app = DingAppFactory.app(clazz);
                                    try {
                                        IDingInteractiveMessageHandler.registerCallBackUrl(app, callBack, true);
                                    } catch (ApiException e) {
                                        log.error("注册回调地址失败：应用：{}, 回调地址：{}, 路由键：{}", app.getAppName(), callBack.getCallbackUrl(), callBack.getCallbackRouteKey());
                                        log.error("注册回调地址失败：异常：{}", e.getCause(), e);
                                    }
                                })
                );
    }
}
