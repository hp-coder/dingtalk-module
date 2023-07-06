package com.hp.dingtalk.service.message;


import cn.hutool.core.collection.CollUtil;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.google.common.base.Preconditions;
import com.hp.dingtalk.component.application.IDingMiniH5;
import com.hp.dingtalk.constant.DingUrlConstant;
import com.hp.dingtalk.pojo.message.worknotify.IDingWorkNotifyMsg;
import com.hp.dingtalk.service.AbstractDingOldApi;
import com.hp.dingtalk.service.IDingWorkNotifyMessageHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.hp.common.base.utils.ParamUtils.Collections;

/**
 * @author hp
 */
@Slf4j
public class DingWorkNotifyMessageHandler extends AbstractDingOldApi implements IDingWorkNotifyMessageHandler {

    public DingWorkNotifyMessageHandler(IDingMiniH5 app) {
        super(app);
    }

    @Override
    public void sendWorkNotifyToUsers(List<String> userIds, IDingWorkNotifyMsg msg) {
        sendWorkNotify(userIds, null, false, msg);
    }

    @Override
    public void sendWorkNotify(List<String> userIds, List<String> deptIds, boolean toAllUser, IDingWorkNotifyMsg msg) {
        if (!toAllUser) {
            Preconditions.checkArgument(CollUtil.isNotEmpty(userIds) || CollUtil.isNotEmpty(deptIds),
                    "当toAllUser设置为false时必须指定userIds或deptIds其中一个参数的值。"
            );
        }
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setUseridList(String.join(",", userIds));
        request.setAgentId(app.getAppId());
        request.setToAllUser(toAllUser);
        request.setMsg(msg.getMsg());
        Collections.ofNullable(deptIds).ifPresent(ids -> request.setDeptIdList(String.join(",", ids)));

        execute(DingUrlConstant.SEND_WORK_MESSAGE, request, () -> "发送工作通知");
    }
}
