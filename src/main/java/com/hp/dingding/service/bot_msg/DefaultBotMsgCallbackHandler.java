package com.hp.dingding.service.bot_msg;


import com.hp.dingding.component.application.IDingBot;
import com.hp.dingding.pojo.bot.BotInteractiveMsgPayload;
import com.hp.dingding.pojo.message.DingMarkdownMsg;
import com.hp.dingding.pojo.message.IDingMsg;
import com.hp.dingding.service.api.IDingBotMsgCallBackHandler;
import com.hp.dingding.utils.DingMarkdown;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * 默认测试处理器，用于调试是否能正常收到消息*
 * @Author: HP
 */
@Component
public class DefaultBotMsgCallbackHandler implements IDingBotMsgCallBackHandler<String> {

    private static final Pattern PATTERN = Pattern.compile("^(?i)test$|^测试$");

    @Override
    public String beforeMessageSend(IDingBot app, BotInteractiveMsgPayload payload) {
        return "前置处理完成：返回测试数据";
    }

    @Override
    public Predicate<BotInteractiveMsgPayload> predication() {
        return payload-> PATTERN.asPredicate().test(payload.getText().getContent());
    }

    @Override
    public IDingMsg message(IDingBot bot, BotInteractiveMsgPayload payload, String data) {
        final String content = DingMarkdown.builder()
                .level3Title("测试回复")
                .text("AppName：" + bot.getAppName())
                .text("AppId：" + bot.getAppId())
                .text("Data：" + data)
                .reference(LocalDateTime.now().atZone(ZoneId.of("Asia/Shanghai")).format(DateTimeFormatter.ofPattern("MM-dd HH:mm")))
                .build();
        return new DingMarkdownMsg(new DingMarkdownMsg.OfficialMarkdownMsg("测试", content));
    }
}
