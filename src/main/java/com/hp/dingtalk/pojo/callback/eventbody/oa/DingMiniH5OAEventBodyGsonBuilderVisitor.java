package com.hp.dingtalk.pojo.callback.eventbody.oa;

import com.google.gson.GsonBuilder;
import com.hp.dingtalk.constant.DingMiniH5EventType;
import com.hp.dingtalk.pojo.GsonBuilderVisitor;
import com.hp.dingtalk.pojo.callback.eventbody.DingMiniH5EventTypeDeserializer;

/**
 * @author hp
 */
public class DingMiniH5OAEventBodyGsonBuilderVisitor implements GsonBuilderVisitor {
    @Override
    public void visit(GsonBuilder builder) {
        builder.registerTypeAdapter(OAEventType.class, new OAEventTypeDeserializer());
        builder.registerTypeAdapter(OAEventResult.class, new OAEventResultDeserializer());
        builder.registerTypeAdapter(DingMiniH5EventType.class, new DingMiniH5EventTypeDeserializer());
    }
}
