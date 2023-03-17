package com.hp.dingding.service.api;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiImChatScencegroupInteractivecardCallbackRegisterRequest;
import com.hp.dingding.component.factory.DingAccessTokenFactory;
import com.hp.dingding.constant.DingConstant;
import com.hp.dingding.component.application.IDingApp;
import com.hp.dingding.pojo.message.interactive.callback.IDingInteractiveCardCallBack;
import com.hp.dingding.pojo.message.interactive.IDingInteractiveMsg;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author hp
 */
public interface IDingInteractiveMessageHandler extends IDingMessageHandler {
    Logger logger = LoggerFactory.getLogger(IDingInteractiveMessageHandler.class);

    /**
     * 发送互动卡片至单聊
     *
     * @param app            调用应用
     * @param userIds        接收用户
     * @param interactiveMsg 卡片消息：包含必要配置信息：卡片id，callbackUrl等
     * @return outTrackId for additional usage
     */
    String sendInteractiveMsgToIndividual(IDingApp app, List<String> userIds, IDingInteractiveMsg interactiveMsg);

    /**
     * 发送互动卡片至群聊
     *
     * @param app                调用应用
     * @param userIds            接收用户
     * @param openConversationId 场景群id
     * @param interactiveMsg     卡片消息：包含必要配置信息：卡片id，callbackUrl等
     * @return
     */
    @Deprecated
    String sendInteractiveMsgToGroup(IDingApp app, List<String> userIds, String openConversationId, IDingInteractiveMsg interactiveMsg);

    /**
     * 更新互动卡片
     *
     * @param app                调用应用
     * @param openConversationId 场景群id
     * @param interactiveMsg     卡片消息：包含必要配置信息：卡片id，callbackUrl等
     * @return
     */
    String updateInteractiveMsg(IDingApp app, String openConversationId, IDingInteractiveMsg interactiveMsg);

    /**
     * 注册互动卡片回调api
     *
     * @param app      调用应用
     * @param callBack 回调url配置
     */
    static void registerCallBackUrl(IDingApp app, IDingInteractiveCardCallBack callBack, boolean forceUpdate) throws ApiException {
        if (app == null || callBack == null) {
            return;
        }
        DingTalkClient client = new DefaultDingTalkClient(DingConstant.REGISTER_CALLBACK);
        OapiImChatScencegroupInteractivecardCallbackRegisterRequest req = new OapiImChatScencegroupInteractivecardCallbackRegisterRequest();
        req.setCallbackUrl(callBack.getCallbackUrl());
        req.setApiSecret(app.getAppSecret());
        req.setCallbackRouteKey(callBack.getCallbackRouteKey());
        req.setForceUpdate(forceUpdate);
        logger.info("应用：{}, 注册回调地址：路由地址：{}， 路由键：{}", app.getAppName(), callBack.getCallbackUrl(), callBack.getCallbackRouteKey());
        client.execute(req, DingAccessTokenFactory.accessToken(app));
    }
}
