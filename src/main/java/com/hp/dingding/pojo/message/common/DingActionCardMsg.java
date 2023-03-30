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
public class DingActionCardMsg {

    private DingActionCardMsg(){}


    /**
     * 卡片消息：一个按钮
     */
    @Getter
    @Setter
    @AllArgsConstructor
    public static class SampleActionCard extends AbstractDingMsg implements IDingBotMsg {
        private String title;
        private String text;
        private String singleTitle;
        private String singleURL;
    }

    /**
     * 卡片消息：竖向两个按钮
     */

    @Getter
    @Setter
    @AllArgsConstructor
    public static class SampleActionCard2 extends AbstractDingMsg implements IDingBotMsg {
        private String title;
        private String text;
        private String actionTitle;
        private String actionURL1;
        private String actionTitle2;
        private String actionURL2;
    }

    /**
     * 卡片消息：竖向三个按钮
     */
    @Getter
    @Setter
    @AllArgsConstructor
    public static class SampleActionCard3 extends AbstractDingMsg implements IDingBotMsg {
        private String title;
        private String text;
        private String actionTitle;
        private String actionURL1;
        private String actionTitle2;
        private String actionURL2;
        private String actionTitle3;
        private String actionURL3;
    }

    /**
     * 卡片消息：竖向四个按钮
     */
    @Getter
    @Setter
    @AllArgsConstructor
    public static class SampleActionCard4 extends AbstractDingMsg implements IDingBotMsg {
        private String title;
        private String text;
        private String actionTitle;
        private String actionURL1;
        private String actionTitle2;
        private String actionURL2;
        private String actionTitle3;
        private String actionURL3;
        private String actionTitle4;
        private String actionURL4;
    }

    /**
     * 卡片消息：竖向五个按钮
     */
    @Getter
    @Setter
    @AllArgsConstructor
    public static class SampleActionCard5 extends AbstractDingMsg implements IDingBotMsg {
        private String title;
        private String text;
        private String actionTitle;
        private String actionURL1;
        private String actionTitle2;
        private String actionURL2;
        private String actionTitle3;
        private String actionURL3;
        private String actionTitle4;
        private String actionURL4;
        private String actionTitle5;
        private String actionURL5;
    }

    /**
     * 卡片消息：横向二个按钮。
     */
    @Getter
    @Setter
    @AllArgsConstructor
    public static class SampleActionCard6 extends AbstractDingMsg implements IDingBotMsg {
        private String title;
        private String text;
        private String buttonTitle1;
        private String buttonUrl1;
        private String buttonTitle2;
        private String buttonUrl2;
    }
}
