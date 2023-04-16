package com.hp.dingtalk.pojo.message.interactive;

import com.hp.dingtalk.pojo.message.interactive.callback.IDingInteractiveCardCallBack;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hp
 */
@Setter
@Getter
public abstract class AbstractDingInteractiveMsg implements IDingInteractiveMsg {

    public String templateId;
    public String callbackRouteKey;
    public String outTrackId;
    public String sign;

    public AbstractDingInteractiveMsg(IDingInteractiveCardCallBack callBack, String outTrackId, String templateId) {
        this.callbackRouteKey = callBack.getCallbackRouteKey();
        this.outTrackId = outTrackId;
        this.templateId = templateId;
        this.sign = IDingInteractiveMsg.encryptSign(this.outTrackId);
    }
}
