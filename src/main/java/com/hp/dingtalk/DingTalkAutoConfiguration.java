package com.hp.dingtalk;

import com.hp.dingtalk.component.DingBooter;
import com.hp.dingtalk.pojo.message.interactive.callback.IDingInteractiveCardCallBack;
import com.hp.dingtalk.service.botcallback.DefaultBotMsgCallbackHandler;
import com.hp.dingtalk.service.botcallback.DefaultCapabilityBotMsgCallbackHandler;
import com.hp.dingtalk.service.botcallback.DefaultFallbackBotMsgCallbackHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author hp 2023/4/11
 */
@Configuration
public class DingTalkAutoConfiguration {

    @Bean
    public DefaultBotMsgCallbackHandler defaultBotMsgCallbackHandler(){
        return new DefaultBotMsgCallbackHandler();
    }
    @Bean
    public DefaultCapabilityBotMsgCallbackHandler defaultCapabilityBotMsgCallbackHandler(){
        return new DefaultCapabilityBotMsgCallbackHandler();
    }
    @Bean
    public DefaultFallbackBotMsgCallbackHandler defaultFallbackBotMsgCallbackHandler(){
        return new DefaultFallbackBotMsgCallbackHandler();
    }

    @Bean
    public DingBooter dingBooter(List<IDingInteractiveCardCallBack> callBacks){
        return new DingBooter(callBacks);
    }
}
