package com.hp.dingding.service.api;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiImChatScencegroupInteractivecardCallbackRegisterRequest;
import com.hp.dingding.component.IDingApi;
import com.hp.dingding.component.application.IDingBot;
import com.hp.dingding.component.factory.DingAccessTokenFactory;
import com.hp.dingding.constant.DingConstant;
import com.hp.dingding.pojo.message.interactive.IDingInteractiveMsg;
import com.hp.dingding.pojo.message.interactive.callback.IDingInteractiveCardCallBack;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 发送钉钉互动卡片，高级版，普通版暂不实现
 * 以下接口都是使用钉钉userId模式，不推荐unionId模式
 * <p>
 * unionid是员工在当前开发者企业账号范围内的唯一标识，由系统生成：
 * 同一个企业员工，在不同的开发者企业账号下，unionid是不相同的。
 * 在同一个开发者企业账号下，unionid是唯一且不变的，例如同一个服务商开发的多个应用，或者是扫码登录等场景的多个App账号。
 *
 * @author hp
 */
public interface IDingInteractiveMessageHandler extends IDingApi {
    Logger logger = LoggerFactory.getLogger(IDingInteractiveMessageHandler.class);

    /**
     * 发送互动卡片至单聊
     * 机器人对用户单聊
     * 这里不对是否转发，针对某个用户的私域数据，@用户做处理
     *
     * @param bot            调用应用
     * @param userIds        接收用户
     * @param interactiveMsg 卡片消息：包含必要配置信息：卡片id，callbackUrl等
     * @return outTrackId for additional usage
     */
    String sendInteractiveMsgToIndividual(IDingBot bot, List<String> userIds, IDingInteractiveMsg interactiveMsg);

    /**
     * 发送互动卡片至群聊
     *
     * @param bot                调用应用
     * @param userIds            为空则群内所有成员可见，不为空则指定人员可见
     * @param openConversationId 场景群id
     * @param interactiveMsg     卡片消息：包含必要配置信息：卡片id，callbackUrl等
     * @return outTrackId
     * @deprecated 还没仔细验证过群聊场景，验证后再逐渐增加业务常用参数，近期业务用不到群聊场景，暂时不更新
     */
    @Deprecated
    String sendInteractiveMsgToGroup(IDingBot bot, List<String> userIds, String openConversationId, IDingInteractiveMsg interactiveMsg);

    /**
     * 更新互动卡片
     *
     * @param bot                调用应用
     * @param openConversationId 场景群id
     * @param interactiveMsg     卡片消息：包含必要配置信息：卡片id，callbackUrl等
     * @return 互动卡片唯一id（outTrackId）
     */
    String updateInteractiveMsg(IDingBot bot, String openConversationId, IDingInteractiveMsg interactiveMsg);

    /**
     * 注册互动卡片回调api
     *
     * @param bot         调用应用
     * @param callback    回调url配置
     * @param forceUpdate 是否强制更新，在不区分环境的情况下，强制更新，容易出现开发或测试更新到上线的问题，项目启动时默认为true，期望客户端能正确将配置通过环境区分
     * @throws ApiException 调用钉钉API异常
     */
    static void registerCallBackUrl(IDingBot bot, IDingInteractiveCardCallBack callback, boolean forceUpdate) throws ApiException {
        if (bot == null || callback == null) {
            return;
        }
        logger.info("注册回调地址,应用:{},路由键:{},路由地址:{}", bot.getAppName(), callback.getCallbackRouteKey(), callback.getCallbackUrl());
        DingTalkClient client = new DefaultDingTalkClient(DingConstant.REGISTER_CALLBACK);
        OapiImChatScencegroupInteractivecardCallbackRegisterRequest requset = new OapiImChatScencegroupInteractivecardCallbackRegisterRequest();
        requset.setCallbackUrl(callback.getCallbackUrl());
        requset.setApiSecret(bot.getAppSecret());
        requset.setCallbackRouteKey(callback.getCallbackRouteKey());
        requset.setForceUpdate(forceUpdate);
        client.execute(requset, DingAccessTokenFactory.accessToken(bot));
    }
}
