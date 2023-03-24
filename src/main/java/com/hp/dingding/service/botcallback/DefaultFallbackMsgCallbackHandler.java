package com.hp.dingding.service.botcallback;

import com.hp.dingding.component.application.IDingBot;
import com.hp.dingding.pojo.callback.DingBotMsgCallbackRequest;
import com.hp.dingding.pojo.message.IDingBotMsg;
import com.hp.dingding.pojo.message.common.DingMarkdownMsg;
import com.hp.dingding.service.IDingBotMsgCallBackHandler;
import com.hp.dingding.utils.DingMarkdown;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 兜底处理器
 * <p>
 * 如果没有一个正常处理器能处理，那么将使用该处理器返回信息
 *
 * @author hp
 */
@Component
public class DefaultFallbackMsgCallbackHandler implements IDingBotMsgCallBackHandler<String> {

    @Override
    public Predicate<DingBotMsgCallbackRequest> predication() {
        return null;
    }

    @Override
    public IDingBotMsg message(IDingBot app, DingBotMsgCallbackRequest payload, String data) {
        final String fallbackMsg = DingMarkdown.builder()
                .contentTitle("默认处理器")
                .level2Title("说明")
                .text("未找到对应处理器，请检查发送内容（关键字，格式，长度等）或联系开发人员处理！")
                .newLine()
                .level3Title("请求原文")
                .textWithFont("'" + payload.getText().getContent() + "'").color("#67C23A").builder()
                .newLine()
                .level3Title("已有处理器")
                .disorderedList(this.getHandlerDescriptions().toArray(new String[0]))
                .build();
        return new DingMarkdownMsg.SampleMarkdown("消息说明", fallbackMsg);
    }


    private List<String> getHandlerDescriptions() {
        final List<IDingBotMsgCallBackHandler<?>> values = IDingBotMsgCallBackHandler.REGISTRY;
        if (CollectionUtils.isEmpty(values)) {
            return Collections.singletonList("未配置任何处理器");
        }
        return values.stream()
                .map(IDingBotMsgCallBackHandler::description)
                .filter(StringUtils::hasText)
                .collect(Collectors.toList());
    }
}
