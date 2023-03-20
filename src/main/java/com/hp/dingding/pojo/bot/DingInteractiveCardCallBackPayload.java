package com.hp.dingding.pojo.bot;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 用于封装钉钉互动卡片的回调请求体对象
 * 由于请求获得的json数据中，自定义卡片数据基本在最里层，直接通过嵌套的对象使用Gson序列化，
 * 无法将最内层的自定义卡片数据反序列化成卡片对象（拿到的是一个Map集合），这里使用先拿json字符串然后在getParams中反序列化的方式获得模版对象
 *
 * @author HP
 */
@Getter
@Setter
public class DingInteractiveCardCallBackPayload {

    private String outTrackId;
    private String corpId;
    private String userId;
    private String value;
    private String content;

    public Value getValue() {
        return new Gson().fromJson(value, new TypeToken<Value>() {
        }.getType());
    }

    public Content getContent() {
        return new Gson().fromJson(content, new TypeToken<Content>() {
        }.getType());
    }

    @Getter
    @Setter
    public static class Content {
        private CardPrivateData cardPrivateData;
    }

    @Getter
    @Setter
    public static class Value {
        private CardPrivateData cardPrivateData;
    }


    @Setter
    public static class CardPrivateData {
        private List<String> actionIds;
        private Object params;

        public List<String> getActionIds() {
            return actionIds;
        }

        public <T> T getParams(Class<T> tClass) {
            final Gson gson = new Gson();
            return gson.fromJson(gson.toJson(params), tClass);
        }
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DingInteractiveCardCallBackPayload{");
        sb.append("outTrackId='").append(outTrackId).append('\'');
        sb.append(", corpId='").append(corpId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
