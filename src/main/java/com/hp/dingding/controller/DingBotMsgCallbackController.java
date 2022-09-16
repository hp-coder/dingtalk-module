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
 *
 * @Author: HP
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
            log.error("未找到对应的钉钉应用: APP_KEY: {}", payload.getRobotCode());
        }
        IDingBotMsgCallBackHandler.handlers(bot, payload)
                .ifPresent(handlers -> {
                    handlers.parallelStream().forEach(handler -> handler.handle(bot, payload));
                });
    }
}
