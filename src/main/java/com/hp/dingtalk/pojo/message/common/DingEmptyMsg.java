package com.hp.dingtalk.pojo.message.common;

import com.hp.dingtalk.pojo.message.AbstractDingMsg;
import com.hp.dingtalk.pojo.message.IDingBotWebhookMsg;
import lombok.Getter;

/**
 * @author hp
 */
@Getter
public class DingEmptyMsg extends AbstractDingMsg implements IDingBotWebhookMsg {
    public DingEmptyMsg() {
        this.msgType = "empty";
    }
}
