package com.hp.dingding.pojo.message.interactive;

import com.hp.dingding.pojo.message.interactive.callback.IDingInteractiveCardCallBack;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

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

    @PostConstruct
    public void validate(){
        Assert.hasText(callbackRouteKey,"回调路由key不能为空");
        Assert.hasText(outTrackId,"为方便业务处理，消息的唯一id不能为空");
        Assert.hasText(templateId,"互动卡片的模版Id不能为空");
    }
}
