package com.hp.dingding.pojo.bot;

import lombok.Getter;
import lombok.Setter;

/**
 * 通过配置机器人的消息链接,
 * 当在群里或单聊时向机器人@或发送消息
 * 钉钉发送的一个消息请求体
 *
 * @Author: HP
 */
@Getter
@Setter
public class BotInteractiveMsgPayload {

    /**
     * {
     * "conversationId": "cidhdK4gfcl6JP2NxCiLBtV20C+X03n9ZoKlj9VpO1Ee98=",
     * "chatbotCorpId": "ding81a031835ed7c67e35c2f4657eb6378f",
     * "chatbotUserId": "$:LWCP_v1:$scTjLKVaoLkKuVwYcYg7Of22iHaHy7/p",
     * "msgId": "msgAlqNMY/TiHI6m60O3cDRzQ==",
     * "senderNick": "胡鹏",
     * "isAdmin": true,
     * "senderStaffId": "2xxxx3310",
     * "sessionWebhookExpiredTime": 1653986476492,
     * "createAt": 1653981076349,
     * "senderCorpId": "ding81a031835ed7c67e35c2f4657eb6378f",
     * "conversationType": "1",
     * "senderId": "$:LWCP_v1:$vCYZh6jKZHduXHRsv1MTE0tgZlmGzT8o",
     * "sessionWebhook": "https://oapi.dingtalk.com/robot/sendBySession?session=37a9ad9d8fb2948a26ce71e60a9f6c29",
     * "text": {
     * "content": "出事"
     * },
     * "robotCode": "dingo6kmyohtwtc0unef",
     * "msgtype": "text"
     * }
     */
    private String conversationId;
    private String chatbotCorpId;
    private String chatbotUserId;
    private String msgId;
    private String senderNick;
    private Boolean isAdmin;
    private String senderStaffId;
    private Long sessionWebhookExpiredTime;
    private Long createAt;
    private String senderCorpId;
    private String conversationType;
    private String senderId;
    private String sessionWebhook;
    private Content text;
    private String robotCode;
    private String msgtype;

    @Getter
    @Setter
    public static class Content {
        private String content;
    }

}
