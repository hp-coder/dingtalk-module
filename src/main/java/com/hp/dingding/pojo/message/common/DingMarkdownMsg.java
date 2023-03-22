package com.hp.dingding.pojo.message.common;

import com.hp.dingding.pojo.message.AbstractDingMsg;
import com.hp.dingding.pojo.message.IDingBotMsg;
import com.hp.dingding.pojo.message.IDingBotWebhookMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * @author hp
 */
@Getter
@Setter
public class DingMarkdownMsg {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class SampleMarkdown extends AbstractDingMsg implements IDingBotMsg, IDingBotWebhookMsg {
        private String title;
        private String text;
    }
}
