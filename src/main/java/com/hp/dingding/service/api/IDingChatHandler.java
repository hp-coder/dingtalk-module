package com.hp.dingding.service.api;

import com.dingtalk.api.response.OapiChatGetResponse;
import com.hp.dingding.component.IDingApi;
import com.hp.dingding.component.application.IDingApp;

/**
 * 群组相关
 *
 * @author HP
 */
public interface IDingChatHandler extends IDingApi {

    /**
     * 根据群chatId获取openConversationId
     *
     * @param app    钉钉应用
     * @param chatId 会员id —> 后期钉钉将废弃该参数，使用openConversationId替代
     * @return openConversationId
     */
    String getOpenConversationIdByChatId(IDingApp app, String chatId);

    /**
     * 获取群会话信息
     *
     * @param app    钉钉应用
     * @param chatId 会员id —> 后期钉钉将废弃该参数，使用openConversationId替代
     * @return
     */
    OapiChatGetResponse.ChatInfo getChatInfo(IDingApp app, String chatId);

}
