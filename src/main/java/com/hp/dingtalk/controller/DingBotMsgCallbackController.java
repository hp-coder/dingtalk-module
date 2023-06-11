package com.hp.dingtalk.controller;


import com.google.gson.Gson;
import com.hp.dingtalk.component.application.IDingBot;
import com.hp.dingtalk.component.factory.app.DingAppFactory;
import com.hp.dingtalk.pojo.callback.DingBotMsgCallbackRequest;
import com.hp.dingtalk.service.IDingBotMsgCallBackHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 钉钉机器人消息互动
 *
 * @author hp
 */
@Slf4j
@RestController
@RequestMapping("/ding")
public class DingBotMsgCallbackController extends AbstractDingBotMsgCallbackController{

    @SneakyThrows
    @RequestMapping("/bot/msg/callback")
    public void msg(
            @RequestHeader(value = "timestamp", required = false) String timeStamp,
            @RequestHeader("sign") String sign,
            @RequestBody DingBotMsgCallbackRequest payload
    ) {
        log.debug("timestamp:{},sign:{},content:{}", timeStamp, sign, new Gson().toJson(payload));
        IDingBot bot = DingAppFactory.app(payload.getRobotCode());
        Assert.notNull(bot, String.format("SpringContext中未找到对应的钉钉应用Bean: APP_KEY:%s", payload.getRobotCode()));
        validateRequest(timeStamp, sign, bot);
        IDingBotMsgCallBackHandler.handlers(bot, payload)
                .ifPresent(handlers -> handlers.parallelStream().forEachOrdered(handler -> handler.handle(bot, payload)));
    }
}
