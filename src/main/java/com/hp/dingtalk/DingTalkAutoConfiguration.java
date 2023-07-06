package com.hp.dingtalk;

import com.hp.dingtalk.component.DingBooter;
import com.hp.dingtalk.component.configuration.IDingMiniH5EventCallbackConfig;
import com.hp.dingtalk.listener.DefaultDingMiniH5EventListener;
import com.hp.dingtalk.pojo.message.interactive.callback.IDingInteractiveCardCallBack;
import com.hp.dingtalk.service.callback.bot.DefaultBotMsgCallbackHandler;
import com.hp.dingtalk.service.callback.bot.DefaultCapabilityBotMsgCallbackHandler;
import com.hp.dingtalk.service.callback.bot.DefaultFallbackBotMsgCallbackHandler;
import com.hp.dingtalk.service.callback.minih5.IDingMiniH5EventCallbackHandler;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hp 2023/4/11
 */
public @Configuration class DingTalkAutoConfiguration {

    public @Bean DefaultBotMsgCallbackHandler defaultBotMsgCallbackHandler() {
        return new DefaultBotMsgCallbackHandler();
    }

    public @Bean DefaultCapabilityBotMsgCallbackHandler defaultCapabilityBotMsgCallbackHandler(ApplicationContext applicationContext) {
        return new DefaultCapabilityBotMsgCallbackHandler(applicationContext);
    }

    public @Bean DefaultFallbackBotMsgCallbackHandler defaultFallbackBotMsgCallbackHandler(ApplicationContext applicationContext) {
        return new DefaultFallbackBotMsgCallbackHandler(applicationContext);
    }

    public @Bean DingBooter dingBooter(List<IDingInteractiveCardCallBack> callBacks) {
        return new DingBooter(callBacks);
    }

    @ConditionalOnProperty(prefix = "dingtalk.miniH5.event", name = "enabled", havingValue = "true")
    @ConditionalOnMissingBean
    public @Bean IDingMiniH5EventCallbackConfig dingMiniH5EventCallbackConfig() {
        return () -> null;
    }

    @ConditionalOnProperty(prefix = "dingtalk.miniH5.event.listener", name = "enabled", havingValue = "true")
    @ConditionalOnMissingBean
    public @Bean DefaultDingMiniH5EventListener defaultDingMiniH5EventListener(
            List<IDingMiniH5EventCallbackHandler> miniH5EventCallbackHandlers,
            ExecutorService defaultDingTalkExecutor
    ) {
        return new DefaultDingMiniH5EventListener(miniH5EventCallbackHandlers, defaultDingTalkExecutor);
    }

    @Bean
    @ConditionalOnMissingBean
    public ExecutorService defaultDingTalkExecutor() {
        BasicThreadFactory basicThreadFactory = new BasicThreadFactory.Builder()
                .namingPattern("DingTalk-Thread-%d")
                .daemon(true)
                .build();
        int maxSize = Runtime.getRuntime().availableProcessors();
        return new ThreadPoolExecutor(0, maxSize,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                basicThreadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
