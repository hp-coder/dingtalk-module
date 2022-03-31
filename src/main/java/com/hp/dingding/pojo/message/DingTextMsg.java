
package com.hp.dingding.pojo.message;


import com.google.gson.Gson;
import com.hp.dingding.constant.DingMsgType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DingTextMsg implements IDingMsg {

    private String msgtype;

    private TextCat text;

    public DingTextMsg(DingMsgType msgType, TextCat text) {
        this.msgtype = msgType.name();
        this.text = text;
    }

    public DingTextMsg(TextCat text) {
        this.msgtype = type(text);
        this.text = text;
    }

    public interface TextCat{

    }
    @Data
    @AllArgsConstructor
    public static class Text implements TextCat{
        private String content;
    }
    @Data
    @AllArgsConstructor
    public static class OfficialTextMsg implements TextCat{
        private String content;
    }
    @Data
    @AllArgsConstructor
    public static class SampleText implements TextCat{
        private String content;
    }

    @Override
    public String toBotJsonString() {
        return new Gson().toJson(this.text);
    }
}