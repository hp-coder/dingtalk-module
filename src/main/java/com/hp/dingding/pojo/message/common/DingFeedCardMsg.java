package com.hp.dingding.pojo.message.common;

import com.hp.dingding.pojo.message.AbstractDingMsg;
import com.hp.dingding.pojo.message.IDingBotWebhookMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author hp
 */
@Getter
@Setter
public class DingFeedCardMsg extends AbstractDingMsg {

    private DingFeedCardMsg(){}

    @Getter
    @Setter
    @AllArgsConstructor
    public static class FeedCard extends AbstractDingMsg implements IDingBotWebhookMsg {
        private List<Link> links;
    }

    @Getter
    @AllArgsConstructor
    public static class Link {
        private final String title;
        private final String messageURL;
        private final String picURL;
    }
}
