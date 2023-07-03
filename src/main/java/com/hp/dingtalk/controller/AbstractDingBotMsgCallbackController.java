package com.hp.dingtalk.controller;

import com.hp.dingtalk.pojo.callback.DingBotMsgCallbackRequest;
import com.hp.dingtalk.component.application.IDingBot;
import com.hp.dingtalk.service.IDingBotMsgCallBackHandler;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 主要是统一验证请求
 *
 * @author hp 2023/3/22
 */
@RequiredArgsConstructor
public abstract class AbstractDingBotMsgCallbackController {

    protected final List<IDingBotMsgCallBackHandler<?>> dingBotMsgCallBackHandlers;

    protected static void validateRequest(String timeStamp, String sign, IDingBot bot) throws NoSuchAlgorithmException, InvalidKeyException {
        Assert.hasText(timeStamp, "非法钉钉机器人回调请求");
        Assert.isTrue(System.currentTimeMillis() - Long.parseLong(timeStamp) <= 60 * 60 * 1000, "非法钉钉机器人回调请求");
        Assert.isTrue(Objects.equals(getSign(timeStamp, bot), sign), "非法钉钉机器人回调请求");
    }

    @NotNull
    private static String getSign(String timeStamp, IDingBot bot) throws NoSuchAlgorithmException, InvalidKeyException {
        String calculatedSign = timeStamp + "\n" + bot.getAppSecret();
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(bot.getAppSecret().getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(calculatedSign.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.encodeBase64(signData));
    }


    /**
     * 获取处理器
     *
     * @param app     机器人应用
     * @param payload 机器人消息回调请求体
     * @return 处理器集合
     */
    protected Optional<List<IDingBotMsgCallBackHandler<?>>> handlers(IDingBot app, DingBotMsgCallbackRequest payload) {
        if (payload == null || (payload.getText() == null && payload.emptyContent())) {
            return Optional.empty();
        }
        Optional.ofNullable(payload.getText())
                .ifPresent(i -> payload.getText().setContent(i.getContent().trim()));

        final Optional<IDingBotMsgCallBackHandler<?>> first = dingBotMsgCallBackHandlers.stream()
                .sorted(Comparator.comparing(IDingBotMsgCallBackHandler::order))
                .filter(handler -> !handler.ignoredApps().contains(app.getClass()))
                .filter(handler ->
                        handler.predication() != null &&
                                handler.predication().test(payload))
                .findFirst();
        return first.
                <Optional<List<IDingBotMsgCallBackHandler<?>>>>
                map(iDingBotMsgCallBackHandler -> Optional.of(Collections.singletonList(iDingBotMsgCallBackHandler)))
                .orElseGet(() -> Optional.of(dingBotMsgCallBackHandlers.stream().filter(handler -> handler.predication() == null).collect(Collectors.toList())));
    }
}
