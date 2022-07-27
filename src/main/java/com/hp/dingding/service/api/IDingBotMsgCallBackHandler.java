package com.hp.dingding.service.api;

import com.hp.dingding.component.application.IDingBot;
import com.hp.dingding.pojo.bot.BotInteractiveMsgPayload;
import com.hp.dingding.pojo.message.IDingMsg;
import com.hp.dingding.service.message.DingBotMessageHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 钉钉机器人单聊自动回复定义
 *
 * @Author: HP
 */
public interface IDingBotMsgCallBackHandler<T> {

    LinkedMultiValueMap<Pattern, IDingBotMsgCallBackHandler> REGISTRY = new LinkedMultiValueMap<>();

    /**
     * 词根
     *
     * @return 正则匹配
     */
    Pattern keyWord();

    /**
     * 自动回复的消息
     *
     * @param app     机器人应用
     * @param payload
     * @param data    前置处理结果
     * @return 钉钉消息
     */
    IDingMsg message(IDingBot app, BotInteractiveMsgPayload payload, T data);

    /**
     * 获取处理器
     *
     * @param payload 机器人消息回调请求体
     * @return 处理器集合
     */
    static Optional<List<IDingBotMsgCallBackHandler>> handlers(BotInteractiveMsgPayload payload) {
        if (payload == null || StringUtils.isEmpty(payload.getText().getContent())) {
            return Optional.empty();
        }
        return Optional.of(REGISTRY.entrySet().stream()
                .filter(i -> i.getKey().matcher(payload.getText().getContent()).matches())
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));
    }

    /**
     * 处理入口
     *
     * @param app     机器人应用
     * @param payload 机器人消息回调请求体
     */
    default void handle(IDingBot app, BotInteractiveMsgPayload payload) {
        notifyBeforeSend(app, payload);
        final T data = beforeMessageSend(app, payload);
        send(app, payload, data);
        afterMessageSent(app, payload);
    }

    /**
     * 消息回复前
     *
     * @param app     机器人应用
     * @param payload 机器人消息回调请求体
     * @return 返回
     */
    default T beforeMessageSend(IDingBot app, BotInteractiveMsgPayload payload) {
        return null;
    }

    /**
     * 消息回复前
     *
     * @param app     机器人应用
     * @param payload 机器人消息回调请求体
     * @return 返回
     */
    default void notifyBeforeSend(IDingBot app, BotInteractiveMsgPayload payload) {
        //do nothing
    }

    /**
     * 发送消息
     *
     * @param app     机器人应用
     * @param payload 机器人消息回调请求体
     * @param data    发消息前操作返回的数据
     */
    default void send(IDingBot app, BotInteractiveMsgPayload payload, T data) {
        final IDingMsg message = message(app, payload, data);
        if (message == null) {
            return;
        }
        new DingBotMessageHandler().sendMsg(app,
                Collections.singletonList(payload.getSenderStaffId()),
                message);
    }


    /**
     * 消息回复后
     *
     * @param app     机器人应用
     * @param payload 机器人消息回调请求体
     */
    default void afterMessageSent(IDingBot app, BotInteractiveMsgPayload payload) {
    }

    /**
     * 自动注册
     */
    @PostConstruct
    default void postConstruct() {
        final List<IDingBotMsgCallBackHandler> set = IDingBotMsgCallBackHandler.REGISTRY.getOrDefault(keyWord(), new ArrayList<>());
        set.add(this);
        REGISTRY.put(keyWord(), set);
    }

}
