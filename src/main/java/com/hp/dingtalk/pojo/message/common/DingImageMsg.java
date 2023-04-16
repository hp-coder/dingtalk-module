package com.hp.dingtalk.pojo.message.common;

import com.hp.dingtalk.pojo.message.AbstractDingMsg;
import com.hp.dingtalk.pojo.message.IDingBotMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hp
 */
@Getter
@Setter
public class DingImageMsg {

    private DingImageMsg(){}
    @Getter
    @Setter
    @AllArgsConstructor
    public static class SampleImageMsg extends AbstractDingMsg implements IDingBotMsg {
        private final String photoURL;
    }
}
