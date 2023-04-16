package com.hp.dingtalk.pojo.message.common;

import com.hp.dingtalk.pojo.message.AbstractDingMsg;
import com.hp.dingtalk.pojo.message.IDingBotMsg;
import com.hp.dingtalk.pojo.message.IDingBotWebhookMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hp
 */
@Getter
@Setter
public class DingLinkMsg {

    private DingLinkMsg(){}

    @Getter
    @Setter
    @AllArgsConstructor
    public static class SampleLink extends AbstractDingMsg implements IDingBotMsg, IDingBotWebhookMsg {
        private String messageUrl;
        private String picUrl;
        private String title;
        private String text;
    }
}
