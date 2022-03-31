package com.hp.dingding.pojo.message;


import com.google.gson.Gson;

/**
 * action_card 类型 机器人和app的消息差异过大，目前选择实现机器人的
 * 大小写/驼峰问题：为了对应api文档字段
 */
public interface IDingMsg {
    String getMsgtype() ;

    String toBotJsonString();

    default String getOutTrackId() {
        return null;
    }

    default String toFullJsonString() {
        return new Gson().toJson(this);
    }

    default String type(Object obj) {
        final char[] chars = obj.getClass().getSimpleName().toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

}
