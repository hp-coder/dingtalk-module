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
import com.hp.dingding.component.factory.DingAccessTokenFactory;
import com.hp.dingding.pojo.message.IDingMsg;
import com.hp.dingding.pojo.message.interactive.IDingInteractiveMsg;
import com.hp.dingding.service.api.IDingInteractiveMessageHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class DingBotMessageHandler extends DingAbstractMessageHandler implements IDingInteractiveMessageHandler {

    @Override
    public void sendMsg(IDingApp app, List<String> userIds, IDingMsg msgContent) {
        sendMsg(app, userIds, false, msgContent);
    }

    @Override
    public void sendMsg(IDingApp app, List<String> userIds, boolean toAllUser, IDingMsg msgContent) {
        sendMsg(app, userIds, null, toAllUser, msgContent);
    }

    @Override
    public void sendMsg(IDingApp app, List<String> userIds, List<String> deptIds, boolean toAllUser, IDingMsg msgContent) {
        argsValidation(app, userIds, msgContent);
        try {
            com.aliyun.dingtalkrobot_1_0.Client client = new com.aliyun.dingtalkrobot_1_0.Client(this.config());
            BatchSendOTOHeaders batchSendOTOHeaders = new BatchSendOTOHeaders();
            batchSendOTOHeaders.xAcsDingtalkAccessToken = DingAccessTokenFactory.accessToken(app);
            BatchSendOTORequest batchSendOTORequest = new BatchSendOTORequest()
                    .setRobotCode(app.getAppKey())
                    .setUserIds(userIds)
                    .setMsgKey(msgContent.getMsgtype())
                    .setMsgParam(msgContent.toBotJsonString());
            log.info("??????: {} batchSendOTO: ??????: {}, ??????: {}", app.getAppName(), msgContent.getMsgtype(), msgContent.toFullJsonString());
            client.batchSendOTOWithOptions(batchSendOTORequest, batchSendOTOHeaders, new RuntimeOptions());
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                log.error("??????: {} batchSendOTO Ding?????????: {},Ding????????????: {}", err.code, err.message);
                throw new RuntimeException("batchSendOTO Ding??????: " + err.message);
            }
        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                log.error("batchSendOTO Ding?????????: {},Ding????????????: {}", err.code, err.message);
                throw new RuntimeException("batchSendOTO Ding?????? TeaException: " + err.message);
            }
            throw new RuntimeException("batchSendOTO Ding??????: " + err.message);
        }
    }

    @Override
    public String sendInteractiveMsgToIndividual(IDingApp app, List<String> userIds, IDingInteractiveMsg interactiveMsg) {
        try {
            com.aliyun.dingtalkim_1_0.Client client = new com.aliyun.dingtalkim_1_0.Client(this.config());
            SendInteractiveCardHeaders sendInteractiveCardHeaders = new SendInteractiveCardHeaders();
            sendInteractiveCardHeaders.xAcsDingtalkAccessToken = DingAccessTokenFactory.accessToken(app);
            SendInteractiveCardRequest.SendInteractiveCardRequestCardData cardData = new SendInteractiveCardRequest.SendInteractiveCardRequestCardData();
            final Map<String, String> map = interactiveMsg.getMap();
            cardData.setCardParamMap(map);
            SendInteractiveCardRequest sendInteractiveCardRequest = new SendInteractiveCardRequest()
                    .setReceiverUserIdList(userIds)
                    .setConversationType(0)
                    .setCallbackRouteKey(interactiveMsg.getCallbackUrl())
                    .setCardTemplateId(interactiveMsg.getTemplateId())
                    .setCardData(cardData)
                    .setOutTrackId(interactiveMsg.getOutTrackId());
            client.sendInteractiveCardWithOptions(sendInteractiveCardRequest, sendInteractiveCardHeaders, new RuntimeOptions());
            log.info("??????: {}, sendInteractiveCardWithOptions: ??????: {}, ??????: {}", app.getAppName(), "interactiveCard", map);
            return interactiveMsg.getOutTrackId();
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                log.error("sendInteractiveMsgToIndividual Ding?????????: {},Ding????????????: {}", err.code, err.message);
                throw new RuntimeException("sendInteractiveMsgToIndividual Ding?????? TeaException: " + err.message);
            }
        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                log.error("sendInteractiveMsgToIndividual Ding?????????: {},Ding????????????: {}", err.code, err.message);
                throw new RuntimeException("sendInteractiveMsgToIndividual Ding?????? TeaException: " + err.message);
            }
            throw new RuntimeException("sendInteractiveMsgToIndividual ?????? TeaException: " + err.message);
        }
        return null;
    }

    @SneakyThrows
    @Override
    public String sendInteractiveMsgToGroup(IDingApp app, List<String> userIds, String openConversationId, IDingInteractiveMsg interactiveMsg) {
        com.aliyun.dingtalkim_1_0.Client client = new com.aliyun.dingtalkim_1_0.Client(this.config());
        SendInteractiveCardHeaders sendInteractiveCardHeaders = new SendInteractiveCardHeaders();
        sendInteractiveCardHeaders.xAcsDingtalkAccessToken = DingAccessTokenFactory.accessToken(app);
        SendInteractiveCardRequest.SendInteractiveCardRequestCardData cardData = new SendInteractiveCardRequest.SendInteractiveCardRequestCardData()
                .setCardParamMap(interactiveMsg.getMap());
        SendInteractiveCardRequest sendInteractiveCardRequest = new SendInteractiveCardRequest()
                .setCardTemplateId(interactiveMsg.getTemplateId())
                .setOpenConversationId(openConversationId)
                .setReceiverUserIdList(userIds)
                .setOutTrackId(UUID.randomUUID().toString())
                .setConversationType(1)
                .setCallbackRouteKey(interactiveMsg.getCallbackUrl())
                .setCardData(cardData)
//                .setPrivateData(privateData)
//                .setChatBotId("123")
                .setUserIdType(1);
        try {
            client.sendInteractiveCardWithOptions(sendInteractiveCardRequest, sendInteractiveCardHeaders, new RuntimeOptions());
        } catch (
                TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                log.error("sendInteractiveMsgToGroup Ding?????????: {},Ding????????????: {}", err.code, err.message);
                throw new RuntimeException("sendInteractiveMsgToGroup Ding?????? TeaException: " + err.message);
            }
        } catch (
                Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                log.error("sendInteractiveMsgToGroup Ding?????????: {},Ding????????????: {}", err.code, err.message);
                throw new RuntimeException("sendInteractiveMsgToGroup Ding?????? TeaException: " + err.message);
            }
            throw new RuntimeException("sendInteractiveMsgToGroup ?????? TeaException: " + err.message);
        }
        return null;
    }

    @SneakyThrows
    @Override
    public String updateInteractiveMsg(IDingApp app, String openConversationId, IDingInteractiveMsg interactiveMsg) {
        com.aliyun.dingtalkim_1_0.Client client = new com.aliyun.dingtalkim_1_0.Client(this.config());
        UpdateInteractiveCardHeaders updateInteractiveCardHeaders = new UpdateInteractiveCardHeaders();
        updateInteractiveCardHeaders.xAcsDingtalkAccessToken = DingAccessTokenFactory.accessToken(app);
        UpdateInteractiveCardRequest.UpdateInteractiveCardRequestCardOptions cardOptions = new UpdateInteractiveCardRequest.UpdateInteractiveCardRequestCardOptions()
                .setUpdateCardDataByKey(true)
                .setUpdatePrivateDataByKey(true);
        UpdateInteractiveCardRequest.UpdateInteractiveCardRequestCardData cardData = new UpdateInteractiveCardRequest.UpdateInteractiveCardRequestCardData()
                .setCardParamMap(interactiveMsg.getMap());
        UpdateInteractiveCardRequest updateInteractiveCardRequest = new UpdateInteractiveCardRequest()
                .setOutTrackId(interactiveMsg.getOutTrackId())
                .setCardData(cardData)
                .setUserIdType(1)
                .setCardOptions(cardOptions);
        try {
            client.updateInteractiveCardWithOptions(updateInteractiveCardRequest, updateInteractiveCardHeaders, new RuntimeOptions());
            return interactiveMsg.getOutTrackId();
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                log.error("updateInteractiveMsg Ding?????????: {},Ding????????????: {}", err.code, err.message);
                throw new RuntimeException("updateInteractiveMsg Ding?????? TeaException: " + err.message);
            }
        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                log.error("updateInteractiveMsg Ding?????????: {},Ding????????????: {}", err.code, err.message);
                throw new RuntimeException("updateInteractiveMsg Ding?????? TeaException: " + err.message);
            }
            throw new RuntimeException("updateInteractiveMsg ?????? TeaException: " + err.message);
        }
        return null;
    }

}
