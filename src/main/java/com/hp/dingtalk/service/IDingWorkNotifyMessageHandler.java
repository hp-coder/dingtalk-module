package com.hp.dingtalk.service;

import com.hp.dingtalk.pojo.message.worknotify.IDingWorkNotifyMsg;
import lombok.NonNull;

import java.util.List;

/**
 * 工作通知发送处理器
 *
 * @author hp 2023/3/22
 */
public interface IDingWorkNotifyMessageHandler {

    /**
     * 相对常用一点
     *
     * @param userIds 接收者的userid列表，最大用户列表长度100
     * @param msg     工作通知，最长不超过2048个字节
     */
    void sendWorkNotifyToUsers(@NonNull List<String> userIds, @NonNull IDingWorkNotifyMsg msg);

    /**
     * 发送工作通知
     *
     * @param userIds   接收者的userid列表，最大用户列表长度100
     * @param deptIds   接收者的部门id列表，最大列表长度20。接收者是部门ID时，包括子部门下的所有用户。
     * @param toAllUser 是否发送给企业全部用户，当设置为false时必须指定userid_list或dept_id_list其中一个参数的值。
     * @param msg       工作通知，最长不超过2048个字节
     */
    void sendWorkNotify(@NonNull List<String> userIds, List<String> deptIds, boolean toAllUser, @NonNull IDingWorkNotifyMsg msg);
}
