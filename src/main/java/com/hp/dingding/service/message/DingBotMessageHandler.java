package com.hp.dingding.service.message;


import com.aliyun.dingtalkim_1_0.models.SendInteractiveCardHeaders;
import com.aliyun.dingtalkim_1_0.models.SendInteractiveCardRequest;
import com.aliyun.dingtalkim_1_0.models.UpdateInteractiveCardHeaders;
import com.aliyun.dingtalkim_1_0.models.UpdateInteractiveCardRequest;
import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTOHeaders;
import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTORequest;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.hp.dingding.component.application.IDingApp;
import com.hp.dingding.component.application.IDingBot;
import com.hp.dingding.component.exception.DingApiException;
import com.hp.dingding.component.factory.token.DingAccessTokenFactory;
import com.hp.dingding.pojo.message.IDingBotMsg;
import com.hp.dingding.pojo.message.interactive.IDingInteractiveMsg;
import com.hp.dingding.service.IDingBotMessageHandler;
import com.hp.dingding.service.IDingInteractiveMessageHandler;
import com.hp.dingding.service.user.DingUserHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hp
 */
@Slf4j
public class DingBotMessageHandler implements IDingBotMessageHandler, IDingInteractiveMessageHandler {

    protected void argsValidation(IDingApp app, List<String> userIds, IDingBotMsg msg) {
        Assert.notNull(app, "请传入有效的钉钉应用");
        Assert.notNull(msg, "请传入有效的钉钉消息对象");
        Assert.isTrue(!CollectionUtils.isEmpty(userIds), "请传入有效的钉钉userId集合");
        Assert.isTrue(userIds.size() <= 20, "钉钉userId集合元素不能超过20个");
    }

    @Override
    public void sendToUserByUserIds(IDingBot bot, List<String> userIds, IDingBotMsg msg) {
        log.debug("app:{},userIds:{},msgContent:{}", bot.getAppName(), userIds, msg);
        argsValidation(bot, userIds, msg);
        try {
            com.aliyun.dingtalkrobot_1_0.Client client = new com.aliyun.dingtalkrobot_1_0.Client(this.config());
            BatchSendOTOHeaders headers = new BatchSendOTOHeaders();
            headers.xAcsDingtalkAccessToken = DingAccessTokenFactory.accessToken(bot);
            BatchSendOTORequest request = new BatchSendOTORequest().setRobotCode(bot.getAppKey()).setUserIds(userIds).setMsgKey(msg.getMsgType()).setMsgParam(msg.getMsgParam());
            client.batchSendOTOWithOptions(request, headers, new RuntimeOptions());
        } catch (TeaException err) {
            if (StringUtils.hasText(err.code) && StringUtils.hasText(err.message)) {
                log.error("机器人向用户发送普通消息异常,机器人:{},异常信息:{},{}", bot.getAppName(), err.code, err.message);
            }
            throw new DingApiException("机器人向用户发送普通消息异常", err);
        } catch (Exception e) {
            TeaException err = new TeaException(e.getMessage(), e);
            if (StringUtils.hasText(err.code) && StringUtils.hasText(err.message)) {
                log.error("机器人向用户发送普通消息异常,机器人:{},异常信息:{},{}", bot.getAppName(), err.code, err.message);
            }
            throw new DingApiException("机器人向用户发送普通消息异常", e);
        }
    }

    @Override
    public void sendToUserByPhones(IDingBot bot, List<String> mobiles, IDingBotMsg msg) {
        Assert.isTrue(!CollectionUtils.isEmpty(mobiles), "请传入有效的手机号集合");
        final DingUserHandler handler = new DingUserHandler();
        final List<String> userIds = mobiles.stream().map(i -> handler.findUserIdByMobile(bot, i)).filter(Objects::nonNull).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(userIds)) {
            return;
        }
        sendToUserByUserIds(bot, userIds, msg);
    }

    @Override
    public String sendInteractiveMsgToIndividual(IDingBot bot, List<String> userIds, IDingInteractiveMsg interactiveMsg) {
        log.debug("机器人发送互动卡片至用户,机器人:{},用户id:{},内容:{}", bot.getAppName(), userIds, interactiveMsg.getMap());
        try {
            com.aliyun.dingtalkim_1_0.Client client = new com.aliyun.dingtalkim_1_0.Client(this.config());
            SendInteractiveCardHeaders sendInteractiveCardHeaders = new SendInteractiveCardHeaders();
            sendInteractiveCardHeaders.xAcsDingtalkAccessToken = DingAccessTokenFactory.accessToken(bot);
            SendInteractiveCardRequest.SendInteractiveCardRequestCardData cardData = new SendInteractiveCardRequest.SendInteractiveCardRequestCardData();
            cardData.setCardParamMap(interactiveMsg.getMap());
            SendInteractiveCardRequest sendInteractiveCardRequest = new SendInteractiveCardRequest()
                    .setCardTemplateId(interactiveMsg.getTemplateId())
                    .setReceiverUserIdList(userIds)
                    .setConversationType(0)
                    .setCallbackRouteKey(interactiveMsg.getCallbackRouteKey())
                    .setCardData(cardData)
                    .setOutTrackId(interactiveMsg.getOutTrackId());

            client.sendInteractiveCardWithOptions(sendInteractiveCardRequest, sendInteractiveCardHeaders, new RuntimeOptions());
            return interactiveMsg.getOutTrackId();
        } catch (TeaException err) {
            if (StringUtils.hasText(err.code) && StringUtils.hasText(err.message)) {
                log.error("机器人发送互动卡片至用户异常,机器人:{},异常信息:{},{}", bot.getAppName(), err.code, err.message);
            }
            throw new DingApiException("机器人发送互动卡片至用户异常", err);
        } catch (Exception e) {
            TeaException err = new TeaException(e.getMessage(), e);
            if (StringUtils.hasText(err.code) && StringUtils.hasText(err.message)) {
                log.error("机器人发送互动卡片至用户异常,机器人:{},异常信息:{},{}", bot.getAppName(), err.code, err.message);
            }
            throw new DingApiException("机器人发送互动卡片至用户异常", e);
        }
    }

    @SneakyThrows
    @Override
    public String sendInteractiveMsgToGroup(IDingBot bot, List<String> userIds, String openConversationId, IDingInteractiveMsg interactiveMsg) {
        log.debug("机器人发送互动卡片至群聊,机器人:{},用户id:{},群会话id:{},内容:{}", bot.getAppName(), userIds, openConversationId, interactiveMsg.getMap());
        try {
            com.aliyun.dingtalkim_1_0.Client client = new com.aliyun.dingtalkim_1_0.Client(this.config());
            SendInteractiveCardHeaders sendInteractiveCardHeaders = new SendInteractiveCardHeaders();
            sendInteractiveCardHeaders.xAcsDingtalkAccessToken = DingAccessTokenFactory.accessToken(bot);
            SendInteractiveCardRequest.SendInteractiveCardRequestCardData cardData = new SendInteractiveCardRequest.SendInteractiveCardRequestCardData();
            cardData.setCardParamMap(interactiveMsg.getMap());
            SendInteractiveCardRequest sendInteractiveCardRequest = new SendInteractiveCardRequest()
                    .setRobotCode(bot.getAppKey())
                    .setOpenConversationId(openConversationId)
                    .setCardTemplateId(interactiveMsg.getTemplateId())
                    .setReceiverUserIdList(userIds)
                    .setConversationType(1)
                    .setCallbackRouteKey(interactiveMsg.getCallbackRouteKey())
                    .setCardData(cardData)
                    .setOutTrackId(interactiveMsg.getOutTrackId());

            client.sendInteractiveCardWithOptions(sendInteractiveCardRequest, sendInteractiveCardHeaders, new RuntimeOptions());
            return interactiveMsg.getOutTrackId();
        } catch (TeaException err) {
            if (StringUtils.hasText(err.code) && StringUtils.hasText(err.message)) {
                log.error("机器人发送互动卡片至群聊异常,机器人:{},异常信息:{},{}", bot.getAppName(), err.code, err.message);
            }
            throw new DingApiException("机器人发送互动卡片至群聊异常", err);
        } catch (Exception e) {
            TeaException err = new TeaException(e.getMessage(), e);
            if (StringUtils.hasText(err.code) && StringUtils.hasText(err.message)) {
                log.error("机器人发送互动卡片至群聊异常,机器人:{},异常信息:{},{}", bot.getAppName(), err.code, err.message);
            }
            throw new DingApiException("机器人发送互动卡片至群聊异常", e);
        }
    }

    @SneakyThrows
    @Override
    public String updateInteractiveMsg(IDingBot bot, String openConversationId, IDingInteractiveMsg interactiveMsg) {
        log.debug("机器人更新互动卡片至用户,机器人:{},会话Id:{},内容:{}", bot.getAppName(), openConversationId, interactiveMsg.getMap());
        com.aliyun.dingtalkim_1_0.Client client = new com.aliyun.dingtalkim_1_0.Client(this.config());
        UpdateInteractiveCardHeaders updateInteractiveCardHeaders = new UpdateInteractiveCardHeaders();
        updateInteractiveCardHeaders.xAcsDingtalkAccessToken = DingAccessTokenFactory.accessToken(bot);
        UpdateInteractiveCardRequest.UpdateInteractiveCardRequestCardOptions cardOptions = new UpdateInteractiveCardRequest.UpdateInteractiveCardRequestCardOptions().setUpdateCardDataByKey(true).setUpdatePrivateDataByKey(true);
        UpdateInteractiveCardRequest.UpdateInteractiveCardRequestCardData cardData = new UpdateInteractiveCardRequest.UpdateInteractiveCardRequestCardData().setCardParamMap(interactiveMsg.getMap());
        UpdateInteractiveCardRequest updateInteractiveCardRequest = new UpdateInteractiveCardRequest().setOutTrackId(interactiveMsg.getOutTrackId()).setCardData(cardData).setUserIdType(1).setCardOptions(cardOptions);
        try {
            client.updateInteractiveCardWithOptions(updateInteractiveCardRequest, updateInteractiveCardHeaders, new RuntimeOptions());
            return interactiveMsg.getOutTrackId();
        } catch (TeaException err) {
            if (StringUtils.hasText(err.code) && StringUtils.hasText(err.message)) {
                log.error("机器人更新互动卡片至用户异常,机器人:{},异常信息:{},{}", bot.getAppName(), err.code, err.message);
            }
            throw new DingApiException("机器人更新互动卡片至用户异常", err);
        } catch (Exception e) {
            TeaException err = new TeaException(e.getMessage(), e);
            if (StringUtils.hasText(err.code) && StringUtils.hasText(err.message)) {
                log.error("机器人更新互动卡片至用户异常,机器人:{},异常信息:{},{}", bot.getAppName(), err.code, err.message);
            }
            throw new DingApiException("机器人更新互动卡片至用户异常", e);
        }
    }

}
