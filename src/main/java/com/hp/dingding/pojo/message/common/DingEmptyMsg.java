package com.hp.dingding.pojo.message.common;

import com.hp.dingding.pojo.message.AbstractDingMsg;
import com.hp.dingding.pojo.message.IDingBotWebhookMsg;
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
