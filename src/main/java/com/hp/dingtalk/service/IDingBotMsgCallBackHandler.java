package com.hp.dingtalk.service;

import com.hp.dingtalk.component.application.IDingBot;
import com.hp.dingtalk.pojo.callback.DingBotMsgCallbackRequest;
import com.hp.dingtalk.pojo.message.IDingBotMsg;
import com.hp.dingtalk.pojo.message.common.DingMarkdownMsg;
import com.hp.dingtalk.service.message.DingBotMessageHandler;
import com.hp.dingtalk.utils.DingMarkdown;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 钉钉机器人单聊自动回复定义
 *
 * @author hp
 */
public interface IDingBotMsgCallBackHandler<T> {

    List<IDingBotMsgCallBackHandler<?>> REGISTRY = new ArrayList<>(16);

    /**
     * 是否可以执行
     *
     * @return Predicate
     */
    Predicate<DingBotMsgCallbackRequest> predication();

    /**
     * 自动回复的消息
     *
     * @param app     机器人应用
     * @param payload 请求体
     * @param data    前置处理结果
     * @return 钉钉消息
     */
    IDingBotMsg message(IDingBot app, DingBotMsgCallbackRequest payload, T data);

    /**
     * 处理器功能说明
     *
     * @return string
     */
    default String description() {
        return "";
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
     * @param app     机器人应用
     * @param payload 机器人消息回调请求体
     * @return 处理器集合
     */
    static Optional<List<IDingBotMsgCallBackHandler<?>>> handlers(IDingBot app, DingBotMsgCallbackRequest payload) {
        if (payload == null || (payload.getText() == null && payload.emptyContent())) {
            return Optional.empty();
        }
        Optional.ofNullable(payload.getText())
                .ifPresent(i -> payload.getText().setContent(i.getContent().trim()));

        final Optional<IDingBotMsgCallBackHandler<?>> first = REGISTRY.stream()
                .sorted(Comparator.comparing(IDingBotMsgCallBackHandler::order))
                .filter(handler -> !handler.ignoredApps().contains(app.getClass()))
                .filter(handler ->
                        handler.predication() != null &&
                                handler.predication().test(payload))
                .findFirst();
        return first.
                <Optional<List<IDingBotMsgCallBackHandler<?>>>>
                map(iDingBotMsgCallBackHandler -> Optional.of(Collections.singletonList(iDingBotMsgCallBackHandler)))
                .orElseGet(() -> Optional.of(REGISTRY.stream().filter(handler -> handler.predication() == null).collect(Collectors.toList())));
    }

    /**
     * 处理入口
     *
     * @param app     机器人应用
     * @param payload 机器人消息回调请求体
     */
    default void handle(IDingBot app, DingBotMsgCallbackRequest payload) {
        if (!authorized(app, payload)) {
            DingMarkdown.Builder builder = DingMarkdown.builder().text("无权使用该功能").reference(LocalDateTime.now().toString());
            new DingBotMessageHandler().sendToUserByUserIds(app, Collections.singletonList(payload.getSenderStaffId()), new DingMarkdownMsg.SampleMarkdown("权限", builder));
            return;
        }
        notifyBeforeSend(app, payload);
        final T data = beforeMessageSend(app, payload);
        send(app, payload, data);
        afterMessageSent(app, payload);
    }

    /**
     * 是否有权限使用该处理器功能
     *
     * @param app     机器人应用
     * @param payload 机器人消息回调请求体
     * @return 是否有权限
     */
    default boolean authorized(IDingBot app, DingBotMsgCallbackRequest payload) {
        return true;
    }


    /**
     * 消息回复前
     *
     * @param app     机器人应用
     * @param payload 机器人消息回调请求体
     * @return 返回
     */
    T beforeMessageSend(IDingBot app, DingBotMsgCallbackRequest payload);

    /**
     * 消息回复前
     *
     * @param app     机器人应用
     * @param payload 机器人消息回调请求体
     */
    default void notifyBeforeSend(IDingBot app, DingBotMsgCallbackRequest payload) {
        //do nothing
    }

    /**
     * 发送消息
     *
     * @param app     机器人应用
     * @param payload 机器人消息回调请求体
     * @param data    发消息前操作返回的数据
     */
    default void send(IDingBot app, DingBotMsgCallbackRequest payload, T data) {
        final IDingBotMsg message = message(app, payload, data);
        if (message == null) {
            return;
        }
        new DingBotMessageHandler().sendToUserByUserIds(app, Collections.singletonList(payload.getSenderStaffId()), message);
    }


    /**
     * 消息回复后
     *
     * @param app     机器人应用
     * @param payload 机器人消息回调请求体
     */
    default void afterMessageSent(IDingBot app, DingBotMsgCallbackRequest payload) {
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
        REGISTRY.add(this);
    }

}