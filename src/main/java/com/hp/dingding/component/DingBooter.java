package com.hp.dingding.component;

import com.hp.dingding.component.application.IDingApp;
import com.hp.dingding.pojo.message.interactive.callback.AbstractDingInteractiveCardCallback;
import com.hp.dingding.service.api.IDingInteractiveMessageHandler;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 启动完成后加载
 */
@Slf4j
@Component
public class DingBooter implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        final ConfigurableApplicationContext applicationContext = applicationReadyEvent.getApplicationContext();
        AbstractDingInteractiveCardCallback
                .callbacks()
                .forEach(callBack -> callBack.getDingBots()
                        .forEach(clazz -> {
                            final IDingApp app = applicationContext.getBean(clazz);
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
