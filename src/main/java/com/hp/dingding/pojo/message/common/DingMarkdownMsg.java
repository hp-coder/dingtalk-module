
package com.hp.dingding.pojo.message.common;

import com.google.gson.Gson;
import com.hp.dingding.constant.DingMsgType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DingMarkdownMsg implements IDingCommonMsg {

    private String msgtype;

    private MarkdownCat markdown;

    public DingMarkdownMsg(DingMsgType msgType, MarkdownCat markdown) {
        this.msgtype = msgType.name();
        this.markdown = markdown;
    }

    public DingMarkdownMsg(MarkdownCat markdown) {
        this.msgtype = type(markdown);
        this.markdown = markdown;
    }

    public interface MarkdownCat{

    }

    @Data
    @Deprecated
    @AllArgsConstructor
    public static class Markdown implements MarkdownCat{
        private String title;
        private String text;
    }
    @Data
    @AllArgsConstructor
    public static class SampleMarkdown implements MarkdownCat{
        private String title;
        private String text;
    }
    @Data
    @AllArgsConstructor
    public static class OfficialMarkdownMsg implements MarkdownCat{
        private String title;
        private String text;
    }
    @Override
    public String toBotJsonString() {
        return new Gson().toJson(this.markdown);
    }
}
