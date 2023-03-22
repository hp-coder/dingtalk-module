package com.hp.dingding.pojo.message.common;

import com.hp.dingding.pojo.message.AbstractDingMsg;
import com.hp.dingding.pojo.message.IDingBotMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hp
 */
@Getter
@Setter
public class DingImageMsg {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class SampleImageMsg extends AbstractDingMsg implements IDingBotMsg {
        private final String photoURL;
    }
}
