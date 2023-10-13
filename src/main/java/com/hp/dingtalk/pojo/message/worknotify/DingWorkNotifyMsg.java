package com.hp.dingtalk.pojo.message.worknotify;

import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.annotations.SerializedName;
import com.hp.dingtalk.pojo.GsonBuilderVisitor;
import lombok.Getter;
import lombok.Setter;

/**
 * 简化调用，原SDK提供的Msg对象容易将msgType和msg的对应关系搞乱
 *
 * @author hp 2023/3/22
 */
@Getter
public class DingWorkNotifyMsg implements IDingWorkNotifyMsg {

    @SerializedName("msgtype")
    private final String msgType;

    @Setter
    private GsonBuilderVisitor gsonBuilderVisitor = null;

    public DingWorkNotifyMsg(OapiMessageCorpconversationAsyncsendV2Request.ActionCard actionCard) {
        this.actionCard = actionCard;
        this.msgType = msgType(actionCard);
    }

    public DingWorkNotifyMsg(OapiMessageCorpconversationAsyncsendV2Request.File file) {
        this.file = file;
        this.msgType = msgType(file);
    }

    public DingWorkNotifyMsg(OapiMessageCorpconversationAsyncsendV2Request.Image image) {
        this.image = image;
        this.msgType = msgType(image);
    }

    public DingWorkNotifyMsg(OapiMessageCorpconversationAsyncsendV2Request.Link link) {
        this.link = link;
        this.msgType = msgType(link);
        setGsonBuilderVisitor(
                gsonBuilder ->
                        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                                .disableHtmlEscaping()
        );
    }

    public DingWorkNotifyMsg(OapiMessageCorpconversationAsyncsendV2Request.Markdown markdown) {
        this.markdown = markdown;
        this.msgType = msgType(markdown);
    }

    public DingWorkNotifyMsg(OapiMessageCorpconversationAsyncsendV2Request.OA oa) {
        this.oa = oa;
        this.msgType = "oa";
    }

    public DingWorkNotifyMsg(OapiMessageCorpconversationAsyncsendV2Request.Text text) {
        this.text = text;
        this.msgType = msgType(text);
    }

    public DingWorkNotifyMsg(OapiMessageCorpconversationAsyncsendV2Request.Voice voice) {
        this.voice = voice;
        this.msgType = msgType(voice);
    }

    /**
     * 卡片消息
     */
    @SerializedName("action_card")
    private OapiMessageCorpconversationAsyncsendV2Request.ActionCard actionCard;
    /**
     * 文件消息
     */
    private OapiMessageCorpconversationAsyncsendV2Request.File file;
    /**
     * 图片消息
     */
    private OapiMessageCorpconversationAsyncsendV2Request.Image image;
    /**
     * 链接消息
     */
    private OapiMessageCorpconversationAsyncsendV2Request.Link link;
    /**
     * markdown消息
     */
    private OapiMessageCorpconversationAsyncsendV2Request.Markdown markdown;
    /**
     * OA消息
     */
    private OapiMessageCorpconversationAsyncsendV2Request.OA oa;
    /**
     * 文本消息
     */
    private OapiMessageCorpconversationAsyncsendV2Request.Text text;
    /**
     * 语音消息
     */
    private OapiMessageCorpconversationAsyncsendV2Request.Voice voice;


}
