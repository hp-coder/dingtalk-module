package com.hp.dingding.pojo.callback;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 通过配置机器人的消息链接,
 * 当在群里或单聊时向机器人@或发送消息
 * 钉钉发送的一个消息请求体
 * 更新后支持了更多类型的消息
 *
 * @author HP
 */
@Getter
@Setter
public class DingBotMsgCallbackRequest {

    /**
     * 会话ID
     */
    private String conversationId;
    /**
     * 1：单聊
     * 2：群聊
     */
    private String conversationType;
    /**
     * 群聊时才有的会话标题。
     */
    private String conversationTitle;
    /**
     * 加密的机器人所在的企业corpId。
     */
    private String chatbotCorpId;
    /**
     * 加密的机器人ID。
     */
    private String chatbotUserId;

    private String robotCode;
    /**
     * 是否在@列表中。
     */
    private Boolean isInAtList;
    /**
     * 被@人的信息。
     */
    private List<AtUser> atUsers;
    /**
     * 加密的消息ID。
     */
    private String msgId;
    /**
     * 是否为管理员。
     */
    private Boolean isAdmin;
    /**
     * 消息的时间戳，单位毫秒。
     */
    private Long createAt;
    /**
     * 使用senderStaffId，作为发送者userid值。
     */
    private String senderStaffId;
    /**
     * 发送者昵称。
     */
    private String senderNick;
    /**
     * 企业内部群有的发送者当前群的企业corpId
     */
    private String senderCorpId;
    /**
     * 加密的发送者ID
     */
    private String senderId;
    /**
     * 当前会话的Webhook地址。
     */
    private String sessionWebhook;
    /**
     * 当前会话的Webhook地址过期时间。
     */
    private Long sessionWebhookExpiredTime;
    /**
     * 消息文本
     */
    private Text text;
    /**
     * 消息体，支持其他媒体类型
     */
    private String content;
    /**
     * 消息类型。
     */
    private String msgtype;

    public <T> T getContent(Class<T> clazz){
        return new Gson().fromJson(this.content,clazz);
    }

    @Getter
    @Setter
    public static class Text {
        /**
         * 文字内容
         */
        private String content;
    }

    @Getter
    @Setter
    public static class Audio {
        /**
         * 语音的时长，单位是毫秒。
         */
        private Integer duration;
        /**
         * 语音文件的下载码，用于换取下载语音的二进制文件。
         */
        private String downloadCode;
        /**
         * 语音识别后的文本
         */
        private String recognition;
    }

    @Getter
    @Setter
    public static class Picture {
        /**
         * 图片文件的下载码，用于换取下载图片的二进制文件。
         */
        private String downloadCode;
    }

    @Getter
    @Setter
    public static class Video {
        /**
         * 视频的时长，单位是毫秒
         */
        private Integer duration;
        /**
         * 视频文件的下载码，用于换取下载视频的二进制文件。
         */
        private String downloadCode;
        /**
         * 视频文件类型。
         */
        private String videoType;
    }

    @Getter
    @Setter
    public static class File {
        /**
         * 文件的下载码，用于换取下载文件的二进制文件。
         */
        private String downloadCode;
        /**
         * 文件名。
         */
        private String fileName;
    }

    @Getter
    @Setter
    public static class RichText {
        private List<RichTextContent> richText;
    }

    @Getter
    @Setter
    public static class RichTextContent {
        //text：文本消息
        private RichTextContentText text;
        //picture：图片消息
        private RichTextContentPicture picture;
    }

    @Getter
    @Setter
    public static class RichTextContentText {
        private String text;
    }

    @Getter
    @Setter
    public static class RichTextContentPicture {
        private String downloadCode;
        private String type;
    }

    @Getter
    @Setter
    public static class AtUser {
        /**
         * 加密的发送者ID。
         */
        private String dingtalkId;
        /**
         * 企业内部群有的发送者在企业内的userid。
         */
        private String staffId;
    }
}
