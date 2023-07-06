package com.hp.dingtalk.pojo.message;

import com.google.gson.Gson;
import com.hp.common.base.annotations.MethodDesc;
import com.taobao.api.internal.util.StringUtils;

/**
 * @author hp
 */
public interface IDingMsg {

    @MethodDesc("获取以类名直接定义的消息类型")
    default String msgType(Object msg) {
        return StringUtils.toCamelStyle(msg.getClass().getSimpleName());
    }

    @MethodDesc("消息类型")
    String getMsgType();

    @MethodDesc("消息内容")
    default String getMsgParam() {
        return new Gson().toJson(this);
    }
}
