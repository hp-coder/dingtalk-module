package com.hp.dingtalk.pojo.message.webhook;

import com.google.gson.annotations.SerializedName;
import lombok.Value;

import java.util.List;

/**
 * @author hp
 */
public interface DingFeedCardMsg extends IDingBotWebhookMsg {

    @Value
    class FeedCard implements DingFeedCardMsg {
        List<FeedCardLink> links;
    }

    @Value
    class FeedCardLink {

        String title;

        @SerializedName("messageURL")
        String messageUrl;

        @SerializedName("picURL")
        String picUrl;
    }
}
