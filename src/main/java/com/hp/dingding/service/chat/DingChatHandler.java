package com.hp.dingding.service.chat;

import com.aliyun.dingtalkim_1_0.models.ChatIdToOpenConversationIdHeaders;
import com.aliyun.dingtalkim_1_0.models.ChatIdToOpenConversationIdResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiChatGetRequest;
import com.dingtalk.api.response.OapiChatGetResponse;
import com.hp.dingding.component.application.IDingApp;
import com.hp.dingding.component.exception.DingApiException;
import com.hp.dingding.component.factory.DingAccessTokenFactory;
import com.hp.dingding.constant.DingConstant;
import com.hp.dingding.service.api.IDingChatHandler;
import com.taobao.api.ApiException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 群组相关
 *
 * @author hp
 */
public class DingChatHandler implements IDingChatHandler {

    @Override
    public String getOpenConversationIdByChatId(IDingApp app, String chatId) {
        Assert.hasText(chatId, "chatId不能为空");
        com.aliyun.dingtalkim_1_0.Client client;
        try {
            client = new com.aliyun.dingtalkim_1_0.Client(this.config());
        } catch (Exception e) {
            throw new DingApiException("创建钉钉请求客户端失败", e);
        }
        ChatIdToOpenConversationIdHeaders chatIdToOpenConversationIdHeaders = new ChatIdToOpenConversationIdHeaders();
        chatIdToOpenConversationIdHeaders.xAcsDingtalkAccessToken = DingAccessTokenFactory.accessToken(app);
        try {
            final ChatIdToOpenConversationIdResponse resp =
                    client.chatIdToOpenConversationIdWithOptions(chatId, chatIdToOpenConversationIdHeaders, new RuntimeOptions());
            return resp.getBody().getOpenConversationId();
        } catch (TeaException err) {
            if (StringUtils.hasText(err.code) && StringUtils.hasText(err.message)) {
                throw new DingApiException("getOpenConversationIdByChatId Ding异常 TeaException: " + err.message);
            }
        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (StringUtils.hasText(err.code) && StringUtils.hasText(err.message)) {
                throw new DingApiException("getOpenConversationIdByChatId Ding异常 TeaException: " + err.message);
            }
        }
        return null;
    }

    @Override
    public OapiChatGetResponse.ChatInfo getChatInfo(IDingApp app, String chatId) {
        DingTalkClient client = new DefaultDingTalkClient(DingConstant.GET_CHAT_INFO);
        OapiChatGetRequest req = new OapiChatGetRequest();
        req.setChatid(chatId);
        req.setHttpMethod("GET");
        OapiChatGetResponse rsp;
        try {
            rsp = client.execute(req, DingAccessTokenFactory.accessToken(app));
        } catch (ApiException e) {
            throw new DingApiException("获取群会话信息失败", e);
        }
        return rsp.getChatInfo();
    }
}
