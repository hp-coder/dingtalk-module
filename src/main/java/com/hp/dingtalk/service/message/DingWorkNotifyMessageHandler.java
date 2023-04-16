package com.hp.dingtalk.service.message;


import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.hp.dingtalk.component.application.IDingMiniH5;
import com.hp.dingtalk.component.exception.DingApiException;
import com.hp.dingtalk.component.factory.token.DingAccessTokenFactory;
import com.hp.dingtalk.constant.DingConstant;
import com.hp.dingtalk.pojo.message.worknotify.IDingWorkNotifyMsg;
import com.hp.dingtalk.service.IDingWorkNotifyMessageHandler;
import com.hp.dingtalk.utils.DingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author hp
 */
@Slf4j
public class DingWorkNotifyMessageHandler implements IDingWorkNotifyMessageHandler {


    @Override
    public void sendWorkNotifyToUsers(IDingMiniH5 app, List<String> userIds, IDingWorkNotifyMsg msg) {
        sendWorkNotify(app, userIds, null, false, msg);
    }

    @Override
    public void sendWorkNotify(IDingMiniH5 app, List<String> userIds, List<String> deptIds, boolean toAllUser, IDingWorkNotifyMsg msg) {
        Assert.notNull(app,"钉钉应用不能为空");
        Assert.notNull(msg,"钉钉工作通知不能为空");
        if (!toAllUser) {
            Assert.isTrue(!CollectionUtils.isEmpty(userIds) || CollectionUtils.isEmpty(deptIds), "当toAllUser设置为false时必须指定userIds或deptIds其中一个参数的值。");
        }
        log.debug("app:{},userIds:{},deptIds:{},toAllUser:{},msgContent:{}", app.getAppName(), userIds, deptIds, toAllUser, msg);
        try {
            DingTalkClient client = new DefaultDingTalkClient(DingConstant.SEND_WORK_MESSAGE);
            OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
            request.setUseridList(String.join(",", userIds));
            request.setAgentId(app.getAppId());
            if (!CollectionUtils.isEmpty(deptIds)) {
                request.setDeptIdList(String.join(",", deptIds));
            }
            request.setToAllUser(toAllUser);
            request.setMsg(msg.getMsg());
            final OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, DingAccessTokenFactory.accessToken(app));
            DingUtils.isSuccess(response);
        } catch (Exception e) {
            log.error("app:{},userIds:{},deptIds:{},toAllUser:{},msgContent:{},err:{}", app.getAppName(), userIds, deptIds, toAllUser, msg, e.getMessage());
            throw new DingApiException("发送钉钉消息异常");
        }
    }
}
