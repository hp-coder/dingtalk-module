package com.hp.dingding.controller;


import com.google.gson.Gson;
import com.hp.dingding.component.application.IDingBot;
import com.hp.dingding.component.factory.DingAppFactory;
import com.hp.dingding.pojo.bot.BotInteractiveMsgPayload;
import com.hp.dingding.service.api.IDingBotMsgCallBackHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 钉钉机器人消息互动
 * 此接口并非为机器人发送互动卡片的回调家接口；
 * 该接口作为用户向机器人发送单聊/群聊等消息的接口，一般处理文本消息，具体可接收消息类型参考钉钉文档，
 * 该接口由开发人员手动在钉钉机器人应用配置-开发管理-消息接收地址配置
 *
 * @author HP
 */
@Slf4j
@RestController
@RequestMapping("/ding")
public class DingBotMsgCallbackController {

    @SneakyThrows
    @RequestMapping("/bot/msg/callback")
    public void msg(@RequestBody BotInteractiveMsgPayload payload) {
        log.info("content: {}", new Gson().toJson(payload));
        IDingBot bot = DingAppFactory.app(payload.getRobotCode());
        if (bot == null) {
            log.error("SpringContext中未找到对应的钉钉应用Bean: APP_KEY: {}", payload.getRobotCode());
            return;
        }
        IDingBotMsgCallBackHandler.handlers(bot, payload)
                .ifPresent(handlers ->
                        handlers.parallelStream()
                                .forEachOrdered(
                                        handler -> handler.handle(bot, payload)
                                )
                );
    }
}
