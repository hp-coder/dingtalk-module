package com.hp.dingding.service.api;

import com.hp.dingding.component.application.IDingBot;
import com.hp.dingding.pojo.bot.BotInteractiveMsgPayload;
import com.hp.dingding.pojo.message.common.IDingCommonMsg;
import com.hp.dingding.service.message.DingBotMessageHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 钉钉机器人单聊自动回复定义
 *
 * @author HP
 */
public interface IDingBotMsgCallBackHandler<T> {
    /**
     * 注册容器*
     */
    List<IDingBotMsgCallBackHandler<?>> REGISTRY = new ArrayList<>(16);

    /**
     * 将判断是否合法的方法继续抽象为一个Predicate方便多种校验方式
     * 通过可继续执行发送消息流程
     *
     * @return 校验是否通过
     */
    Predicate<BotInteractiveMsgPayload> predication();

    /**
     * 自动回复的消息
     *
     * @param bot     机器人应用
     * @param payload 请求体
     * @param data    前置处理结果
     * @return 钉钉消息
     */
    IDingCommonMsg message(IDingBot bot, BotInteractiveMsgPayload payload, T data);

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
        return 0;
    }

    /**
     * 获取处理器
     *
     * @param bot     机器人应用
     * @param payload 机器人消息回调请求体
     * @return 处理器集合
     */
    static Optional<List<IDingBotMsgCallBackHandler<?>>> handlers(IDingBot bot, BotInteractiveMsgPayload payload) {
        if (payload == null || StringUtils.isEmpty(payload.getText().getContent())) {
            return Optional.empty();
        }
        final String content = payload.getText().getContent();
        payload.getText().setContent(StringUtils.strip(content, StringUtils.SPACE));
        final List<IDingBotMsgCallBackHandler<?>> handlers = REGISTRY.stream()
                .filter(handler -> !handler.ignoredApps().contains(bot.getClass()))
                .filter(handler ->
                        handler.predication() != null && handler.predication().test(payload)
                )
                .collect(Collectors.toList());
        return CollectionUtils.isEmpty(handlers) ?
                Optional.of(REGISTRY.stream().filter(handler -> handler.predication() == null).collect(Collectors.toList())) :
                Optional.of(handlers);
    }

    /**
     * 处理入口
     *
     * @param bot     机器人应用
     * @param payload 机器人消息回调请求体
     */
    default void handle(IDingBot bot, BotInteractiveMsgPayload payload) {
        notifyBeforeSend(bot, payload);
        final T data = beforeMessageSend(bot, payload);
        send(bot, payload, data);
        afterMessageSent(bot, payload);
    }

    /**
     * 消息回复前
     *
     * @param bot     机器人应用
     * @param payload 机器人消息回调请求体
     * @return 返回
     */
    default T beforeMessageSend(IDingBot bot, BotInteractiveMsgPayload payload) {
        return null;
    }

    /**
     * 消息回复前
     *
     * @param bot     机器人应用
     * @param payload 机器人消息回调请求体
     */
    default void notifyBeforeSend(IDingBot bot, BotInteractiveMsgPayload payload) {
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
        final IDingCommonMsg message = message(app, payload, data);
        if (message == null) {
            return;
        }
        new DingBotMessageHandler().sendMsg(app,
                Collections.singletonList(payload.getSenderStaffId()), message);
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
        REGISTRY.add(order(), this);
    }

}
