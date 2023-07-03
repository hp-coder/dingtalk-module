package com.hp.dingtalk;

import com.hp.dingtalk.component.DingBooter;
import com.hp.dingtalk.pojo.message.interactive.callback.IDingInteractiveCardCallBack;
import com.hp.dingtalk.component.configuration.IDingMiniH5EventCallbackConfig;
import com.hp.dingtalk.service.callback.bot.DefaultBotMsgCallbackHandler;
import com.hp.dingtalk.service.callback.bot.DefaultCapabilityBotMsgCallbackHandler;
import com.hp.dingtalk.service.callback.bot.DefaultFallbackBotMsgCallbackHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
    public @Bean IDingMiniH5EventCallbackConfig dingMiniH5EventCallbackConfig(){
        return () -> null;
    }
}
