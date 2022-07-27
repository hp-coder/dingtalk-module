package com.hp.dingding.controller;


import com.google.gson.Gson;
import com.hp.dingding.component.application.IDingBot;
import com.hp.dingding.pojo.bot.BotInteractiveMsgPayload;
import com.hp.dingding.service.api.IDingBotMsgCallBackHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.lang.reflect.Constructor;
import java.util.Set;

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
        final String robotCode = payload.getRobotCode();
        log.info("cache: {}", new Gson().toJson(IDingBot.CACHE));
        if (IDingBot.CACHE.containsKey(robotCode)) {
            final IDingBot bot = IDingBot.CACHE.get(robotCode);
            IDingBotMsgCallBackHandler.handlers(payload)
                    .ifPresent(handlers -> {
                        handlers.forEach(handler -> handler.handle(bot, payload));
                    });
        }
    }

    @SneakyThrows
    @PostConstruct
    public void loadDingBotAppCache() {
        final Reflections reflections = new Reflections();
        final Set<Class<? extends IDingBot>> subTypesOf = reflections.getSubTypesOf(IDingBot.class);
        for (Class<? extends IDingBot> aClass : subTypesOf) {
            final Constructor<? extends IDingBot> constructor = aClass.getConstructor();
            constructor.setAccessible(true);
            final IDingBot bot = constructor.newInstance();
            IDingBot.CACHE.putIfAbsent(bot.getAppKey(), bot);
        }
    }
}
