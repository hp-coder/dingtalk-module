package com.hp.dingding.service;

import com.hp.dingding.component.IDingApi;
import com.hp.dingding.component.application.IDingMiniH5;
import com.hp.dingding.pojo.message.worknotify.IDingWorkNotifyMsg;

import java.util.List;

/**
 * 工作通知发送处理器
 *
 * @author hp 2023/3/22
 */
public interface IDingWorkNotifyMessageHandler extends IDingApi {
    /**
     * 相对常用一点
     *
     * @param app h5微应用
     * @param userIds 接收者的userid列表，最大用户列表长度100
     * @param msg 工作通知，最长不超过2048个字节
     */
    void sendWorkNotifyToUsers(IDingMiniH5 app, List<String> userIds, IDingWorkNotifyMsg msg);

    /**
     * 发送工作通知
     * @param app h5微应用
     * @param userIds 接收者的userid列表，最大用户列表长度100
     * @param deptIds 接收者的部门id列表，最大列表长度20。接收者是部门ID时，包括子部门下的所有用户。
     * @param toAllUser 是否发送给企业全部用户，当设置为false时必须指定userid_list或dept_id_list其中一个参数的值。
     * @param msg 工作通知，最长不超过2048个字节
     */
    void sendWorkNotify(IDingMiniH5 app, List<String> userIds, List<String> deptIds, boolean toAllUser, IDingWorkNotifyMsg msg);
}
