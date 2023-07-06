package com.hp.dingtalk.service.callback.interactivemsg;

import com.dingtalk.api.request.OapiImChatScencegroupInteractivecardCallbackRegisterRequest;
import com.hp.dingtalk.component.application.IDingBot;
import com.hp.dingtalk.constant.DingUrlConstant;
import com.hp.dingtalk.pojo.message.interactive.callback.IDingInteractiveCardCallBack;
import com.hp.dingtalk.service.AbstractDingOldApi;
import com.hp.dingtalk.service.IDingInteractiveMessageCallbackRegisterHandler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hp
 */
@Slf4j
public class DingInteractiveMessageCallbackRegisterHandler extends AbstractDingOldApi implements IDingInteractiveMessageCallbackRegisterHandler {
    public DingInteractiveMessageCallbackRegisterHandler(@NonNull IDingBot app) {
        super(app);
    }

    @Override
    public void registerCallBackUrl(@NonNull IDingInteractiveCardCallBack callback, boolean forceUpdate) {
        log.info("注册回调地址,应用:{},路由键:{},路由地址:{}", app.getAppName(), callback.getCallbackRouteKey(), callback.getCallbackUrl());
        OapiImChatScencegroupInteractivecardCallbackRegisterRequest request = new OapiImChatScencegroupInteractivecardCallbackRegisterRequest();
        request.setCallbackUrl(callback.getCallbackUrl());
        request.setApiSecret(app.getAppSecret());
        request.setCallbackRouteKey(callback.getCallbackRouteKey());
        request.setForceUpdate(forceUpdate);
        execute(
                DingUrlConstant.REGISTER_CALLBACK,
                request,
                () -> "注册互动卡片高级版回调地址"
        );
    }
}
