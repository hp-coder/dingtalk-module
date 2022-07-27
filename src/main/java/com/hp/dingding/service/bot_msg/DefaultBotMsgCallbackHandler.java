package com.hp.dingding.service.bot_msg;


import com.hp.dingding.component.application.IDingBot;
import com.hp.dingding.pojo.bot.BotInteractiveMsgPayload;
import com.hp.dingding.pojo.message.DingMarkdownMsg;
import com.hp.dingding.pojo.message.IDingMsg;
import com.hp.dingding.service.api.IDingBotMsgCallBackHandler;
import com.hp.dingding.utils.DingMarkdown;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * @Author: HP
 */
@Component
public class DefaultBotMsgCallbackHandler implements IDingBotMsgCallBackHandler<String> {

    private static final Pattern pattern = Pattern.compile("^(?i)test$|^测试$");

    @Override
    public Pattern keyWord() {
        return pattern;
    }

    @Override
    public String beforeMessageSend(IDingBot app, BotInteractiveMsgPayload payload) {
        return "beforeMessageSend done";
    }

    @Override
    public IDingMsg message(IDingBot bot, BotInteractiveMsgPayload payload, String data) {
        final String content = DingMarkdown.builder()
                .level1Title("测试自动回复消息")
                .level2Title("机器人")
                .boldText(bot.getAppName())
                .level2Title("AppId")
                .italicText(bot.getAppId() + "")
                .level2Title("前置处理接口")
                .boldText(data)
                .build();
        return new DingMarkdownMsg(new DingMarkdownMsg.SampleMarkdown("测试", content));
    }
}
