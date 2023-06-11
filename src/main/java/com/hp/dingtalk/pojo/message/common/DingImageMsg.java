package com.hp.dingtalk.pojo.message.common;

import com.hp.dingtalk.pojo.message.AbstractDingMsg;
import com.hp.dingtalk.pojo.message.IDingBotMsg;
import lombok.Value;

/**
 * @author hp
 */
public interface DingImageMsg {


    @Value
    class SampleImageMsg extends AbstractDingMsg implements IDingBotMsg {
        String photoURL;
    }
}
