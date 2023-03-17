package com.hp.dingding.pojo.message.common;

import com.google.gson.Gson;
import com.hp.dingding.constant.DingMsgType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DingLinkMsg implements IDingCommonMsg {

    private String msgtype;

    private Link link;

    public DingLinkMsg(Link link) {
        this.msgtype = type(link);
        this.link = link;
    }

    public DingLinkMsg(DingMsgType msgType , Link link) {
        this.msgtype = msgType.name();
        this.link = link;
    }

    public interface Link {
    }

    @Data
    @AllArgsConstructor
    public static class OfficialImageMsg implements Link {
        private String photoURL;
    }

    @Data
    @AllArgsConstructor
    public static class SampleLink implements Link {
        private String messageUrl;
        private String picUrl;
        private String title;
        private String text;
    }

    @Override
    public String toBotJsonString() {
        return new Gson().toJson(this.link);
    }
}
