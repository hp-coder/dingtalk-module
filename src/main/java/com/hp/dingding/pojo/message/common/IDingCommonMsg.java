package com.hp.dingding.pojo.message.common;

import com.google.gson.Gson;
import com.hp.dingding.pojo.message.IDingMsg;

/**
 * 钉钉的文档在消息类型篇描述的很乱，例如上文消息类型msgtype=sampleActionCard，下文用例就成了msgtype=actioncard
 * 暂时只能按照其文档描述的实现 getMsgtype()，后期使用上有问题再迭代
 *
 * @author hp 2023/3/17
 */
public interface IDingCommonMsg extends IDingMsg {
    String getMsgtype();

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
