package com.hp.dingtalk.pojo.callback.event;

import com.google.gson.GsonBuilder;
import com.hp.dingtalk.pojo.GsonBuilderVisitor;
import com.hp.dingtalk.pojo.callback.eventbody.IDingMiniH5EventBody;
import com.hp.common.base.annotations.FieldDesc;
import com.hp.common.base.annotations.MethodDesc;
import com.hp.dingtalk.constant.DingMiniH5EventType;
import lombok.Value;

import java.util.Optional;

/**
 * @author hp
 */
public interface DingMiniH5CallbackEvents {

    @Value
    class DingMiniH5EventDecryptedPayload {

        @FieldDesc(
                "消息负载的特殊性, 其中包含的字段更具体的事件有关, 在获取时由客户端决定"
        )
        String payload;
        @FieldDesc(
                "为了方便客户端调用, 增加一个系统内使用到的钉钉事件"
        )
        DingMiniH5EventType eventType;

        @MethodDesc("客户端决定转什么类型")
        public <T extends IDingMiniH5EventBody> T getPayload(Class<T> clz, GsonBuilderVisitor visitor) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Optional.ofNullable(visitor).ifPresent(v -> v.visit(gsonBuilder));
            return gsonBuilder.create().fromJson(payload, clz);
        }
    }
}
