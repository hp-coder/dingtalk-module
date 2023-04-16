package com.hp.dingtalk.service;

import com.hp.dingtalk.component.IDingApi;
import com.hp.dingtalk.component.application.IDingBot;

/**
 * 下载机器人接收消息的文件内容
 * @author hp 2023/3/22
 */
public interface IDingBotFileDownloadHandler extends IDingApi {


    String downloadUrl(IDingBot bot, String downloadCode) throws Exception;
}
