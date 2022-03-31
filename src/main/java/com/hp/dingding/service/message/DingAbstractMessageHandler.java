package com.hp.dingding.service.message;

import com.hp.dingding.component.application.IDingApp;
import com.hp.dingding.pojo.message.IDingMsg;
import com.hp.dingding.service.api.IDingMessageHandler;
import com.hp.dingding.service.user.DingUserHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class DingAbstractMessageHandler implements IDingMessageHandler{

    @Override
    public void sendMsgThroughMobile(IDingApp app, List<String> mobiles, List<String> deptIds, boolean toAllUser, IDingMsg msg) {
        Assert.isTrue(!CollectionUtils.isEmpty(mobiles), "请传入有效的手机号集合");
        final DingUserHandler handler = new DingUserHandler();
        final List<String> userIds = mobiles.stream().map(i -> handler.findUserIdByMobile(app, i)).collect(Collectors.toList());
        sendMsg(app, userIds, deptIds, toAllUser, msg);
    }


    /**
     * sendMsg的参数校验
     *
     * @param app     钉钉应用
     * @param userIds 钉钉用户ids
     * @param msg     消息体
     */
    protected void argsValidation(IDingApp app, List<String> userIds, IDingMsg msg) {
        Assert.notNull(app, "请传入有效的钉钉应用");
        Assert.notNull(msg, "请传入有效的钉钉消息对象");
        Assert.isTrue(!CollectionUtils.isEmpty(userIds), "请传入有效的钉钉userId集合");
        Assert.isTrue(userIds.size() <= 20, "钉钉userId集合元素不能超过20个");
    }
}
