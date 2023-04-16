package com.hp.dingtalk.service.botcallback;

import com.hp.dingtalk.component.application.IDingBot;
import com.hp.dingtalk.pojo.callback.DingBotMsgCallbackRequest;
import com.hp.dingtalk.pojo.message.IDingBotMsg;
import com.hp.dingtalk.pojo.message.common.DingMarkdownMsg;
import com.hp.dingtalk.service.IDingBotMsgCallBackHandler;
import com.hp.dingtalk.utils.DingMarkdown;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author hp 2023/3/29
 */
public class DefaultCapabilityBotMsgCallbackHandler implements IDingBotMsgCallBackHandler<String> {

    private static final Pattern PATTERN = Pattern.compile("^(?i)capability$|^能力$");

    @Override
    public Predicate<DingBotMsgCallbackRequest> predication() {
        return request -> PATTERN.asPredicate().test(request.getText().getContent());
    }

    @Override
    public IDingBotMsg message(IDingBot app, DingBotMsgCallbackRequest payload, String data) {
        final String fallbackMsg = DingMarkdown.builder()
                .level2Title("机器人能力")
                .text("该机器人使用能力通过以下处理器提供")
                .level3Title("请求原文")
                .textWithFont("'" + payload.getText().getContent() + "'").color("#67C23A").builder()
                .level3Title("处理器")
                .disorderedList(this.getHandlerDescriptions().toArray(new String[0]))
                .build();
        return new DingMarkdownMsg.SampleMarkdown(String.format("%s能力", app.getAppName()), fallbackMsg);
    }

    @Override
    public String beforeMessageSend(IDingBot app, DingBotMsgCallbackRequest payload) {
        return null;
    }


    private List<String> getHandlerDescriptions() {
        return IDingBotMsgCallBackHandler.REGISTRY.stream()
                .map(IDingBotMsgCallBackHandler::description)
                .filter(StringUtils::hasText)
                .collect(Collectors.toList());
    }

    @Override
    public Integer order() {
        return Integer.MAX_VALUE;
    }
}