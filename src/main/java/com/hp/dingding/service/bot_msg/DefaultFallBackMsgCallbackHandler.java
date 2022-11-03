package com.hp.dingding.service.bot_msg;

import com.hp.dingding.component.application.IDingBot;
import com.hp.dingding.pojo.bot.BotInteractiveMsgPayload;
import com.hp.dingding.pojo.message.DingMarkdownMsg;
import com.hp.dingding.pojo.message.IDingMsg;
import com.hp.dingding.service.api.IDingBotMsgCallBackHandler;
import com.hp.dingding.utils.DingMarkdown;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 兜底处理器
 * <p>
 * 如果没有一个正常处理器能处理，那么将使用该处理器返回信息
 *
 * @Author: HP
 */
@Component
public class DefaultFallBackMsgCallbackHandler implements IDingBotMsgCallBackHandler<String> {

    @Override
    public Predicate<BotInteractiveMsgPayload> predication() {
        return null;
    }

    @Override
    public IDingMsg message(IDingBot app, BotInteractiveMsgPayload payload, String data) {
        final String fallbackMsg = DingMarkdown.builder()
                .level2Title("说明")
                .text("未找到对应处理器，请检查发送内容（关键字，格式，长度等）或联系开发人员处理！")
                .newLine()
                .level3Title("请求原文")
                .textWithFont("'" + payload.getText().getContent() + "'").color("#67C23A").builder()
                .newLine()
                .level3Title("已有处理器")
                .orderedList(this.getHandlerDescriptions().toArray(new String[0]))
                .build();
        return new DingMarkdownMsg(new DingMarkdownMsg.OfficialMarkdownMsg("消息说明", fallbackMsg));
    }


    private List<String> getHandlerDescriptions() {
        final List<IDingBotMsgCallBackHandler> values = IDingBotMsgCallBackHandler.REGISTRY;
        if (CollectionUtils.isEmpty(values)) {
            return Collections.singletonList("未配置任何处理器");
        }
        return values.stream()
                .map(IDingBotMsgCallBackHandler::description)
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toList());
    }
}
