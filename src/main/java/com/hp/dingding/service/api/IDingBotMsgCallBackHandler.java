package com.hp.dingding.service.api;

import com.hp.dingding.component.application.IDingBot;
import com.hp.dingding.pojo.bot.BotInteractiveMsgPayload;
import com.hp.dingding.pojo.message.IDingMsg;
import com.hp.dingding.service.message.DingBotMessageHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
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

    LinkedMultiValueMap<Pattern, IDingBotMsgCallBackHandler> REGISTRY = new LinkedMultiValueMap<>(16);
    Map<Pattern, Integer> PATTERN_ORDER = new HashMap<>(16);

    /**
     * 词根
     * <p>
     * 当返回null时作为fallback处理器使用
     *
     * @return 正则匹配
     */
    Pattern keyWord();

    /**
     * 自动回复的消息
     *
     * @param app     机器人应用
     * @param payload 请求体
     * @param data    前置处理结果
     * @return 钉钉消息
     */
    IDingMsg message(IDingBot app, BotInteractiveMsgPayload payload, T data);

    /**
     * 处理器功能说明
     *
     * @return string
     */
    default String description() {
        return StringUtils.EMPTY;
    }

    /**
     * 表达式遍历顺序
     * <p>
     * 注意：是pattern在遍历匹配正则的顺序，而非对应正则下处理器执行顺序
     * <p>
     * 该值仅在Pattern不为空时作为匹配顺序进行排序
     *
     * @return 排序值
     */
    default Integer order() {
        return 1;
    }

    /**
     * 获取处理器
     *
     * @param app     机器人应用
     * @param payload 机器人消息回调请求体
     * @return 处理器集合
     */
    static Optional<List<IDingBotMsgCallBackHandler>> handlers(IDingBot app, BotInteractiveMsgPayload payload) {
        if (payload == null || StringUtils.isEmpty(payload.getText().getContent())) {
            return Optional.empty();
        }
        final String content = payload.getText().getContent();
        payload.getText().setContent(StringUtils.strip(content, StringUtils.SPACE));
        final List<IDingBotMsgCallBackHandler> handlers = PATTERN_ORDER.entrySet()
                .stream()
                .filter(i -> i.getKey() != null)
                .sorted(Map.Entry.comparingByValue())
                .filter(i -> i.getKey().asPredicate().test(payload.getText().getContent()))
                .map(i -> REGISTRY.getOrDefault(i.getKey(), Collections.emptyList()))
                .flatMap(Collection::stream)
                .filter(handler -> !handler.ignoredApps().contains(app.getClass()))
                .collect(Collectors.toList());
        return CollectionUtils.isEmpty(handlers) ? Optional.ofNullable(REGISTRY.get(null)) : Optional.of(handlers);
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
     * 忽略该功能的App集合
     * 默认匹配就执行
     *
     * @return 忽略该功能的App集合
     */
    default Set<Class<? extends IDingBot>> ignoredApps() {
        return Collections.emptySet();
    }


    /**
     * 自动注册
     */
    @PostConstruct
    default void postConstruct() {
        REGISTRY.add(keyWord(), this);
        if (PATTERN_ORDER.containsKey(keyWord())) {
            PATTERN_ORDER.computeIfPresent(keyWord(), (k, v) -> order() < v ? order() : v);
        } else {
            PATTERN_ORDER.put(keyWord(), order());
        }
    }

}
