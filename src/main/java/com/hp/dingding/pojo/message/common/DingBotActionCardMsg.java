package com.hp.dingding.pojo.message.common;

import com.google.gson.Gson;
import com.hp.dingding.constant.DingMsgType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DingBotActionCardMsg implements IDingCommonMsg {

    private String msgtype;

    private ActionCard action_card;

    public DingBotActionCardMsg(DingMsgType msgType, ActionCard action_card) {
        this.msgtype = msgType.name();
        this.action_card = action_card;
    }
    public DingBotActionCardMsg(ActionCard action_card) {
        this.msgtype = type(action_card);
        this.action_card = action_card;
    }

    public interface ActionCard {

    }

    @Data
    @AllArgsConstructor
    public static class OfficialActionCardMsg implements ActionCard {
        private String title;
        private String text;
        private String singleTitle;
        private String singleURL;
    }

    @Data
    @AllArgsConstructor
    public static class OfficialActionCardMsg1 implements ActionCard {
        private String title;
        private String text;
        private String buttonTitle1;
        private String buttonUrl1;
        private String buttonTitle2;
        private String buttonUrl2;
    }

    @Data
    @AllArgsConstructor
    public static class OfficialActionCardMsg2 implements ActionCard {
        private String title;
        private String text;
        private String buttonTitle1;
        private String buttonUrl1;
        private String buttonTitle2;
        private String buttonUrl2;
    }

    @Data
    @AllArgsConstructor
    public static class SampleActionCard implements ActionCard {
        private String title;
        private String text;
        private String singleTitle;
        private String singleURL;
    }


    @Data
    @AllArgsConstructor
    public static class SampleActionCard2 implements ActionCard {
        private String title;
        private String text;
        private String actionTitle;
        private String actionURL1;
        private String actionTitle2;
        private String actionURL2;
    }

    @Data
    @AllArgsConstructor
    public static class SampleActionCard3 implements ActionCard {
        private String title;
        private String text;
        private String actionTitle;
        private String actionURL1;
        private String actionTitle2;
        private String actionURL2;
        private String actionTitle3;
        private String actionURL3;
    }

    @Override
    public String toBotJsonString() {
        return new Gson().toJson(this.action_card);
    }
}
