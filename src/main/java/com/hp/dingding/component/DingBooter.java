package com.hp.dingding.component;

import com.hp.dingding.component.application.IDingBot;
import com.hp.dingding.component.factory.app.DingAppFactory;
import com.hp.dingding.pojo.message.interactive.callback.IDingInteractiveCardCallBack;
import com.hp.dingding.service.IDingInteractiveMessageHandler;
import com.taobao.api.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * springboot ready后 调用注册
 *
 * @author hp
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DingBooter implements ApplicationListener<ApplicationReadyEvent> {

    private final List<IDingInteractiveCardCallBack> callBacks;

    @Override
    public void onApplicationEvent(@NotNull ApplicationReadyEvent applicationReadyEvent) {
        if (CollectionUtils.isEmpty(callBacks)) {
            return;
        }
        callBacks.forEach(callBack ->
                callBack.getDingBots().forEach(clazz -> {
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
