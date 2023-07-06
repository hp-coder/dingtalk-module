package com.hp.dingtalk.pojo.message.interactive;

import com.google.common.base.Preconditions;
import com.hp.common.base.annotations.FieldDesc;
import com.hp.dingtalk.pojo.message.IDingMsg;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public interface IDingInteractiveMsg extends IDingMsg {
    @FieldDesc("互动卡片全局密码盐，用于对消息对sign值加密")
    String GLOBAL_SALT = "dfaen23djf461nJ51FHDowie17hf1";

    default Map<String, String> toMap() {
        final BeanMap beanMap = BeanMap.create(this);
        final Map<String, String> map = new HashMap<>(beanMap.size());
        beanMap.forEach((k, v) -> {
            if (Objects.nonNull(v)) {
                map.put(String.valueOf(k), String.valueOf(v));
            }
        });
        return map;
    }

    /**
     * 主要接口，看作消息唯一id
     */
    String getOutTrackId();

    /**
     * 主要参数
     * 卡片的回调路由key，接口也只能配置一个key，多按钮通过接口参数区分
     */
    String getCallbackRouteKey();

    /**
     * 业务历史遗留，简单的通过该加密字段对回调参数做校验
     */
    String getSign();

    /**
     * 主要参数
     * 互动卡片模版Id，卡片后台配置后获取（不需要发布）
     */
    String getTemplateId();


    static String encryptSign(String outTrackId) {
        Preconditions.checkNotNull(outTrackId, "参数异常：outTrackId缺失");
        final String combine = outTrackId + GLOBAL_SALT;
        return DigestUtils.md5DigestAsHex(combine.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 空实现
     *
     * @return
     */
    @Override
    default String getMsgType() {
        return null;
    }
}
