package com.hp.dingtalk.pojo.message.bot;

import lombok.Value;

/**
 * @author hp
 */
public interface DingLinkMsg extends IDingBotMsg {

    @Value
    class SampleLink implements DingLinkMsg {
        String messageUrl;
        String picUrl;
        String title;
        String text;
    }
}
