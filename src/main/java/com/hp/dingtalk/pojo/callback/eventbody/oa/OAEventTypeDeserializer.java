package com.hp.dingtalk.pojo.callback.eventbody.oa;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * @author hp
 */
public class OAEventTypeDeserializer implements JsonDeserializer<OAEventType> {
    @Override
    public OAEventType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final String asString = json.getAsString();
        return OAEventType.of(asString).orElse(null);
    }
}
