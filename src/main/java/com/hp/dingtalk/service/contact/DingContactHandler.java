package com.hp.dingtalk.service.contact;

import com.aliyun.dingtalkcontact_1_0.Client;
import com.aliyun.dingtalkcontact_1_0.models.GetUserHeaders;
import com.aliyun.dingtalkcontact_1_0.models.GetUserResponse;
import com.aliyun.dingtalkcontact_1_0.models.GetUserResponseBody;
import com.aliyun.teautil.models.RuntimeOptions;
import com.hp.dingtalk.component.application.IDingApp;
import com.hp.dingtalk.component.exception.DingApiException;
import com.hp.dingtalk.service.AbstractDingNewApi;
import com.hp.dingtalk.service.IDingContactHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hp 2023/3/23
 */
@Slf4j
public class DingContactHandler extends AbstractDingNewApi implements IDingContactHandler {

    public DingContactHandler(IDingApp app) {
        super(app);
    }

    @Override
    public GetUserResponseBody personalContactInfo(String userToken, String unionId) {
        GetUserHeaders getUserHeaders = new GetUserHeaders();
        getUserHeaders.xAcsDingtalkAccessToken = userToken;
        final GetUserResponse response = execute(
                Client.class,
                client -> {
                    try {
                        return client.getUserWithOptions(unionId, getUserHeaders, new RuntimeOptions());
                    } catch (Exception e) {
                        log.error("获取个人通讯录信息异常", e);
                        throw new DingApiException("获取个人通讯录信息异常");
                    }
                },
                () -> "获取个人通讯录信息"
        );
        return response.getBody();
    }
}
