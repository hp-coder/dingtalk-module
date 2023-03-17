package com.hp.dingding.pojo.message.common;

import com.google.gson.Gson;
import com.hp.dingding.pojo.message.IDingMsg;

/**
 * @author hp 2023/3/17
 */
public interface IDingCommonMsg extends IDingMsg {
    String getMsgtype() ;

    String toBotJsonString();

    default String toFullJsonString() {
        return new Gson().toJson(this);
    }

    default String type(Object obj) {
        final char[] chars = obj.getClass().getSimpleName().toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
