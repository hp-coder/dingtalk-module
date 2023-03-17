package com.hp.dingding.pojo.message.interactive;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AbstractDingInteractiveMsg implements IDingInteractiveMsg {

    public String templateId;
    public String callbackRouteKey;
    public String outTrackId;
    public String sign;

    public AbstractDingInteractiveMsg(String callbackRouteKey, String outTrackId, String templateId) {
        this.callbackRouteKey = callbackRouteKey;
        this.outTrackId = outTrackId;
        this.templateId = templateId;
        this.sign = IDingInteractiveMsg.encryptSign(this.outTrackId);
    }
}
