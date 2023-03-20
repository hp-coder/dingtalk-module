package com.hp.dingding.pojo.message.interactive;

import com.hp.dingding.pojo.message.IDingMsg;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public interface IDingInteractiveMsg extends IDingMsg {

    /**
     * 互动卡片全局密码盐，用于对消息对sign值加密
     */
    String GLOBAL_SALT = "dfaen23djf461nJ51FHDowie17hf1";

    /**
     * 统一用这个方法转map，或者改hutool也行
     */
    default Map<String, String> getMap() {
        final BeanMap beanMap = BeanMap.create(this);
        final Map<String, String> map = new HashMap<>();
        beanMap.forEach((k, v) -> {
            if (v != null) {
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

    /**
     * 字段加密方法，简单加密
     */
    static String encryptSign(String outTrackId) {
        Assert.notNull(outTrackId, "参数异常：outTrackId缺失");
        final String combine = outTrackId + GLOBAL_SALT;
        return DigestUtils.md5DigestAsHex(combine.getBytes(StandardCharsets.UTF_8));
    }
}
