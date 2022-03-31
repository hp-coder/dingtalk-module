package com.hp.dingding.service.api;


import com.hp.dingding.component.IDingApi;
import com.hp.dingding.component.application.IDingApp;
import com.hp.dingding.pojo.message.IDingMsg;

import java.util.List;

public interface IDingMessageHandler extends IDingApi {

    /**
     * 发送消息
     * @param app 钉钉应用
     * @param userIds 钉钉用户id集合
     * @param msg 消息体
     */
    default void sendMsg(IDingApp app, List<String> userIds, IDingMsg msg){};

    /**
     * 发送消息
     * @param app 钉钉应用
     * @param userIds 钉钉用户id集合
     * @param toAllUser 是否发送全部用户
     * @param msg 消息体
     */
    default void sendMsg(IDingApp app, List<String> userIds, boolean toAllUser, IDingMsg msg){};

    /**
     * 发送消息
     * @param app 钉钉应用
     * @param userIds 钉钉用户id集合
     * @param deptIds 钉钉部门id集合 nullable
     * @param toAllUser 是否发送全部用户
     * @param msg 消息体
     */
    default void sendMsg(IDingApp app, List<String> userIds, List<String> deptIds, boolean toAllUser, IDingMsg msg){};

    /**
     * 通过手机号查找钉钉用户并发送消息
     * @param app 钉钉应用
     * @param mobiles 手机号 无前缀+86等
     * @param deptIds 部门ids 未实现
     * @param toAllUser 是否发送全部用户
     * @param msg IDingMsg消息体
     */
    default void sendMsgThroughMobile(IDingApp app, List<String> mobiles, List<String> deptIds, boolean toAllUser, IDingMsg msg){};
}
