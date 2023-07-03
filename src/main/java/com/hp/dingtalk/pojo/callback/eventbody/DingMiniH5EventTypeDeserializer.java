package com.hp.dingtalk.pojo.callback.eventbody;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.hp.dingtalk.constant.DingMiniH5EventType;

import java.lang.reflect.Type;

/**
 * @author hp
 */
public class DingMiniH5EventTypeDeserializer implements JsonDeserializer<DingMiniH5EventType> {
    @Override
    public DingMiniH5EventType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return DingMiniH5EventType.of(json.getAsString()).orElse(null);
    }
}
