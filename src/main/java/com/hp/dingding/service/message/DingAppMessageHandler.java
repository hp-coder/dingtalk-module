package com.hp.dingding.service.message;


import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.hp.dingding.component.application.IDingApp;
import com.hp.dingding.component.factory.DingAccessTokenFactory;
import com.hp.dingding.constant.DingConstant;
import com.hp.dingding.pojo.message.common.IDingCommonMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
public class DingAppMessageHandler extends DingAbstractMessageHandler {

    @Override
    public void sendMsg(IDingApp app, List<String> userIds, IDingCommonMsg msgContent) {
        sendMsg(app, userIds, false, msgContent);
    }

    @Override
    public void sendMsg(IDingApp app, List<String> userIds, boolean toAllUser, IDingCommonMsg msgContent) {
        sendMsg(app, userIds, null, toAllUser, msgContent);
    }

    @Override
    public void sendMsg(IDingApp app, List<String> userIds, List<String> deptIds, boolean toAllUser, IDingCommonMsg msgContent) {
        try {
            argsValidation(app, userIds, msgContent);
            DingTalkClient client = new DefaultDingTalkClient(DingConstant.SEND_WORK_MESSAGE);
            OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
            request.setUseridList(String.join(",", userIds));
            request.setAgentId(app.getAppId());
            if (!CollectionUtils.isEmpty(deptIds)) {
                request.setDeptIdList(String.join(",", deptIds));
            }
            request.setToAllUser(toAllUser);
            OapiMessageCorpconversationAsyncsendV2Request.Msg msg = buildMsg(msgContent);
            request.setMsg(msg);
            client.execute(request, DingAccessTokenFactory.accessToken(app));
            log.info("应用: {} :请求钉钉根据用户id发送通知消息: 类型: {}, 内容: {}", app.getAppName(), msgContent.getMsgtype(), msgContent.toFullJsonString());
        } catch (Exception e) {
            log.error("应用: {} :请求钉钉根据用户id发送通知消息: 类型: {}, 内容: {}, 异常: {}", app.getAppName(), msgContent.getMsgtype(), msgContent.toFullJsonString(), e.getMessage());
            throw new RuntimeException("请求钉钉根据手机获取用户id出现异常");
        }
    }

    private OapiMessageCorpconversationAsyncsendV2Request.Msg buildMsg(IDingCommonMsg msgContent) {
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype(msgContent.getMsgtype());
        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
        final String content = msgContent.toBotJsonString();
        log.info("消息payload: {}", content);
        msg.getText().setContent(content);
        return msg;
    }
}
