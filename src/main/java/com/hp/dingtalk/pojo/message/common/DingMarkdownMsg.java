package com.hp.dingtalk.pojo.message.common;

import com.hp.dingtalk.pojo.message.AbstractDingMsg;
import com.hp.dingtalk.pojo.message.IDingBotMsg;
import com.hp.dingtalk.pojo.message.IDingBotWebhookMsg;
import com.hp.dingtalk.utils.DingMarkdown;
import lombok.Value;


/**
 * @author hp
 */
public interface DingMarkdownMsg {

    @Value
    class SampleMarkdown extends AbstractDingMsg implements IDingBotMsg, IDingBotWebhookMsg {
        String title;
        String text;

        public SampleMarkdown(String title, DingMarkdown.Builder markdownBuilder) {
            this.title = title;
            this.text = markdownBuilder.build();
        }

        public SampleMarkdown(String title, String markdown) {
            this.title = title;
            this.text = markdown;
        }
    }
}
