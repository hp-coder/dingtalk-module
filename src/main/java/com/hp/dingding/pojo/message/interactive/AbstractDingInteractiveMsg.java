package com.hp.dingding.pojo.message.interactive;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AbstractDingInteractiveMsg implements IDingInteractiveMsg {

    public String templateId;
    public String callbackUrl;
    public String outTrackId;
    public String sign;
    public Integer state = 0;

    AbstractDingInteractiveMsg(String callbackUrl,String outTrackId,String templateId){
        this.callbackUrl = callbackUrl;
        this.outTrackId = outTrackId;
        this.templateId = templateId;
        this.sign = IDingInteractiveMsg.encryptSign(this.outTrackId);
    }
}
