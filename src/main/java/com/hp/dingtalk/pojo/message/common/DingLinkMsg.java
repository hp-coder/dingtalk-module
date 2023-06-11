package com.hp.dingtalk.pojo.message.common;

import com.hp.dingtalk.pojo.message.AbstractDingMsg;
import com.hp.dingtalk.pojo.message.IDingBotMsg;
import com.hp.dingtalk.pojo.message.IDingBotWebhookMsg;
import lombok.Value;

/**
 * @author hp
 */
public interface DingLinkMsg {

    @Value
    class SampleLink extends AbstractDingMsg implements IDingBotMsg, IDingBotWebhookMsg {
        String messageUrl;
        String picUrl;
        String title;
        String text;
    }
}
