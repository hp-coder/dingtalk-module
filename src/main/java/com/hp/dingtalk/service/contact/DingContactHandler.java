package com.hp.dingtalk.service.contact;

import com.aliyun.dingtalkcontact_1_0.models.GetUserHeaders;
import com.aliyun.dingtalkcontact_1_0.models.GetUserResponseBody;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.hp.dingtalk.component.exception.DingApiException;
import com.hp.dingtalk.service.IDingContactHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hp 2023/3/23
 */
@Slf4j
public class DingContactHandler implements IDingContactHandler {

    @Override
    public GetUserResponseBody personalContactInfo(String userToken, String unionId) {
        log.debug("获取个人通讯录信息,userToken:{},unionId:{}", userToken, unionId);
        GetUserHeaders getUserHeaders = new GetUserHeaders();
        getUserHeaders.xAcsDingtalkAccessToken = userToken;
        try {
            return client().getUserWithOptions(unionId, getUserHeaders, new RuntimeOptions()).getBody();
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                log.error("获取个人通讯录信息异常,userToken:{},unionId:{},异常:{},{}", userToken, unionId, err.getCode(), err.getMessage());
            }
            throw new DingApiException("获取个人通讯录信息异常", err);
        } catch (Exception e) {
            TeaException err = new TeaException(e.getMessage(), e);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                log.error("获取个人通讯录信息异常,userToken:{},unionId:{},异常:{},{}", userToken, unionId, err.getCode(), err.getMessage());
            }
            throw new DingApiException("获取个人通讯录信息异常", e);
        }
    }
}
