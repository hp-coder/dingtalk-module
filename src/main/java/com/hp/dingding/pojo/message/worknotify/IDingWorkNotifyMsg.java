package com.hp.dingding.pojo.message.worknotify;

import com.google.gson.Gson;
import com.hp.dingding.pojo.message.IDingMsg;

/**
 * 消息字段需要消息类型和以消息类型为key的消息内容组成的值
 * { "msgtype": "text", "text": { "content": "请提交日报。" } }
 * {@link <a href="https://open.dingtalk.com/document/orgapp/asynchronous-sending-of-enterprise-session-messages">工作通知</a>}
 * 文档写得太偷懒了，可以从OapiMessageCorpconversationAsyncsendV2Request看到，
 * msg字段直接将OapiMessageCorpconversationAsyncsendV2Request.Msg无脑json序列化，
 * 但是Msg.msgType字段只是最后set的那个，其实没有强对应关系，如果传递的msgtype和实际的msg对象不同，消息将发送失败
 *
 * @author hp 2023/3/21
 */
public interface IDingWorkNotifyMsg extends IDingMsg {
    default String getMsg(){
        return new Gson().toJson(this);
    }
}
