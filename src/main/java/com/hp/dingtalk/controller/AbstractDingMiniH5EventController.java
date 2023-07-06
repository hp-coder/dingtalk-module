package com.hp.dingtalk.controller;

import com.hp.dingtalk.component.application.IDingMiniH5;
import com.hp.dingtalk.component.configuration.IDingMiniH5EventCallbackConfig;
import com.hp.dingtalk.pojo.callback.DingMiniH5EventCallbackRequest;
import com.hp.dingtalk.utils.DingCallbackCrypto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @author hp
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractDingMiniH5EventController {

    protected final IDingMiniH5EventCallbackConfig callbackConfiguration;
    protected final ApplicationEventPublisher eventPublisher;
    protected DingCallbackCrypto callbackCrypto;


    protected String decrypt(DingMiniH5EventCallbackRequest request, EventPayload payload) throws DingCallbackCrypto.DingTalkEncryptException {
        final IDingMiniH5 app = callbackConfiguration.getApp();
        // 钉钉提供的加解密工具
        callbackCrypto = new DingCallbackCrypto(app.getEventToken(), app.getEventKey(), app.getAppKey());
        String encryptMsg = payload.getEncrypt();
        return callbackCrypto.getDecryptMsg(request.getSignature(), request.getTimestamp(), request.getNonce(), encryptMsg);
    }

    @Data
    public static class EventPayload {
        private String encrypt;
    }
}
