package com.hp.dingtalk.pojo.message;

import com.google.gson.Gson;
import com.taobao.api.internal.util.StringUtils;

/**
 * @author hp
 */
public interface IDingMsg {
    /**
     * 获取以类名直接定义的消息类型
     *
     * @param msg 某个具体类型的消息
     * @return 消息类型（类名第一个字母小写）
     */
    default String msgType(Object msg) {
        return StringUtils.toCamelStyle(msg.getClass().getSimpleName());
    }

    /**
     * 消息类型
     *
     * @return 消息类型
     */
    String getMsgType();

    /**
     * 消息内容
     *
     * @return json消息内容
     */
    default String getMsgParam() {
        return new Gson().toJson(this);
    }
}
