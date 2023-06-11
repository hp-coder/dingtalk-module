package com.hp.dingtalk.pojo.message.common;

import com.hp.dingtalk.pojo.message.AbstractDingMsg;
import com.hp.dingtalk.pojo.message.IDingBotWebhookMsg;
import lombok.Value;

import java.util.List;

/**
 * @author hp
 */
public interface DingFeedCardMsg {

    @Value
    class FeedCard extends AbstractDingMsg implements IDingBotWebhookMsg {
        List<FeedCardLink> links;
    }

    @Value
    class FeedCardLink {
        String title;
        String messageURL;
        String picURL;
    }
}
