package com.hp.dingding.pojo.message.common;

import com.hp.dingding.pojo.message.AbstractDingMsg;
import com.hp.dingding.pojo.message.IDingBotMsg;
import com.hp.dingding.pojo.message.IDingBotWebhookMsg;
import com.hp.dingding.utils.DingMarkdown;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * @author hp
 */
@Getter
@Setter
public class DingMarkdownMsg {

    private DingMarkdownMsg(){
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class SampleMarkdown extends AbstractDingMsg implements IDingBotMsg, IDingBotWebhookMsg {
        private String title;
        private String text;

        public SampleMarkdown(String title, DingMarkdown.Builder markdownBuilder) {
            this.title = title;
            this.text = markdownBuilder.build();
        }
    }
}
