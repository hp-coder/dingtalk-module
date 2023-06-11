package com.hp.dingtalk.pojo.message.common;

import com.hp.dingtalk.pojo.message.AbstractDingMsg;
import com.hp.dingtalk.pojo.message.IDingBotWebhookMsg;

/**
 * @author hp
 */
public interface DingEmptyMsg {

    class EmptyMsg extends AbstractDingMsg implements IDingBotWebhookMsg {
        private final String msgType = "empty";
    }
}

