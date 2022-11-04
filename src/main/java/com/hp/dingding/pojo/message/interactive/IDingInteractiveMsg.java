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

    default Map<String, String> getMap() {
        final BeanMap beanMap = BeanMap.create(this);
        final Map<String, String> map = new HashMap<>();
        beanMap.forEach((k, v) ->{
            if (v!=null){
                map.put(String.valueOf(k), String.valueOf(v));
            }
        });
        return map;
    }

    String getOutTrackId();

    String getCallbackRouteKey();

    String getSign();

    String getTemplateId();

    void setState(Integer state);

    static String encryptSign(String outTrackId){
        Assert.notNull(outTrackId,"参数异常：outTrackId缺失");
        final String combine = outTrackId + GLOBAL_SALT;
        return DigestUtils.md5DigestAsHex(combine.getBytes(StandardCharsets.UTF_8));
    }
}
