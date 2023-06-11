package com.hp.dingtalk.pojo.message.common;


import com.hp.dingtalk.pojo.message.AbstractDingMsg;
import com.hp.dingtalk.pojo.message.IDingBotMsg;
import com.hp.dingtalk.pojo.message.IDingBotWebhookMsg;
import lombok.Value;


/**
 * @author hp
 */
public interface DingTextMsg {


    @Value
    class SampleText extends AbstractDingMsg implements IDingBotMsg, IDingBotWebhookMsg {
        String content;
    }
}
