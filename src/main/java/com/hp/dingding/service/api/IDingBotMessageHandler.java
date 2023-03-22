package com.hp.dingding.service.api;


import com.hp.dingding.component.IDingApi;
import com.hp.dingding.component.application.IDingBot;
import com.hp.dingding.pojo.message.IDingBotMsg;

import java.util.List;

/**
 * 钉钉机器人消息处理器
 * @author hp
 */
public interface IDingBotMessageHandler extends IDingApi {

    /**
     * 通过手机号查找钉钉用户并发送消息
     *
     * @param bot       钉钉应用
     * @param userIds   钉钉用户id
     * @param msg       IDingBotMsg
     */
    void sendToUserByUserIds(IDingBot bot, List<String> userIds, IDingBotMsg msg);

    /**
     * 通过手机号查找钉钉用户并发送消息
     *
     * @param bot       钉钉应用
     * @param mobiles   手机号 无前缀+86等
     * @param msg       IDingBotMsg
     */
    void sendToUserByPhones(IDingBot bot, List<String> mobiles, IDingBotMsg msg);

}
