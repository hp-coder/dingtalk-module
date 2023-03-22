package com.hp.dingding.pojo.message;

/**
 * action_card 类型 机器人和app的消息差异过大，目前选择实现机器人的
 * 大小写/驼峰问题：为了对应api文档字段
 * 人与机器人会话中机器人消息：支持图片、语音、文件收发能力。
 * 群聊会话中机器人消息：图片、语音、视频、文件发送能力，但群聊中用户无法@机器人发送语音、视频、文件给机器人。
 *
 * @author hp 2023/3/17
 */
public interface IDingBotWebhookMsg extends IDingMsg {

}
