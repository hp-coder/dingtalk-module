package com.hp.dingding.controller;

import com.hp.dingding.component.application.IDingBot;
import org.apache.commons.codec.binary.Base64;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * 主要是统一验证请求
 *
 * @author hp 2023/3/22
 */
public abstract class AbstractDingBotMsgCallbackController {
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
}
