package com.hp.dingding.pojo.message;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hp 2023/3/21
 */
@Setter
@Getter
public abstract class AbstractDingMsg implements IDingMsg{
    @Expose(serialize = false)
    protected String msgType;

    protected AbstractDingMsg(){
        setMsgType(msgType(this));
    }
}
