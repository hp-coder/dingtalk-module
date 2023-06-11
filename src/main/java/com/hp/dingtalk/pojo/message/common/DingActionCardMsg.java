package com.hp.dingtalk.pojo.message.common;

import com.hp.dingtalk.pojo.message.AbstractDingMsg;
import com.hp.dingtalk.pojo.message.IDingBotMsg;
import lombok.Value;

/**
 * @author hp
 */
public interface DingActionCardMsg {

    /**
     * 卡片消息：一个按钮
     */
    @Value
    class SampleActionCard extends AbstractDingMsg implements IDingBotMsg {
        String title;
        String text;
        String singleTitle;
        String singleURL;
    }

    /**
     * 卡片消息：竖向两个按钮
     */
    @Value
    class SampleActionCard2 extends AbstractDingMsg implements IDingBotMsg {
        String title;
        String text;
        String actionTitle;
        String actionURL1;
        String actionTitle2;
        String actionURL2;
    }

    /**
     * 卡片消息：竖向三个按钮
     */
    @Value
    class SampleActionCard3 extends AbstractDingMsg implements IDingBotMsg {
        String title;
        String text;
        String actionTitle;
        String actionURL1;
        String actionTitle2;
        String actionURL2;
        String actionTitle3;
        String actionURL3;
    }

    /**
     * 卡片消息：竖向四个按钮
     */
    @Value
    class SampleActionCard4 extends AbstractDingMsg implements IDingBotMsg {
        String title;
        String text;
        String actionTitle;
        String actionURL1;
        String actionTitle2;
        String actionURL2;
        String actionTitle3;
        String actionURL3;
        String actionTitle4;
        String actionURL4;
    }

    /**
     * 卡片消息：竖向五个按钮
     */
    @Value
    class SampleActionCard5 extends AbstractDingMsg implements IDingBotMsg {
        String title;
        String text;
        String actionTitle;
        String actionURL1;
        String actionTitle2;
        String actionURL2;
        String actionTitle3;
        String actionURL3;
        String actionTitle4;
        String actionURL4;
        String actionTitle5;
        String actionURL5;
    }

    /**
     * 卡片消息：横向二个按钮。
     */
    @Value
    class SampleActionCard6 extends AbstractDingMsg implements IDingBotMsg {
        String title;
        String text;
        String buttonTitle1;
        String buttonUrl1;
        String buttonTitle2;
        String buttonUrl2;
    }
}
