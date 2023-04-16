package com.hp.dingtalk.service.login;

import com.aliyun.dingtalkoauth2_1_0.models.GetUserTokenRequest;
import com.aliyun.dingtalkoauth2_1_0.models.GetUserTokenResponseBody;
import com.aliyun.tea.TeaException;
import com.hp.dingtalk.component.application.IDingMiniH5;
import com.hp.dingtalk.component.exception.DingApiException;
import com.hp.dingtalk.service.IDingLoginHandler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

/**
 * @author hp 2023/3/23
 */
@Slf4j
public class DingLoginHandler implements IDingLoginHandler {

    @Override
    public GetUserTokenResponseBody getUserToken(IDingMiniH5 app, @NotNull String authCode, @NonNull GrantType grantType) {
        log.debug("用户token,应用:{},authCode:{}", app.getAppName(), authCode);
        GetUserTokenRequest getUserTokenRequest = new GetUserTokenRequest()
                .setClientId(app.getAppKey())
                .setClientSecret(app.getAppSecret())
                .setCode(authCode)
                .setGrantType(grantType.name());
        try {
            return client().getUserToken(getUserTokenRequest).getBody();
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                log.error("用户token异常,应用:{},authCode:{},异常:{},{}", app.getAppName(), authCode, err.getCode(), err.getMessage());
            }
            throw new DingApiException("用户token异常", err);
        } catch (Exception e) {
            TeaException err = new TeaException(e.getMessage(), e);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                log.error("用户token异常,应用:{},authCode:{},异常:{},{}", app.getAppName(), authCode, err.getCode(), err.getMessage());
            }
            throw new DingApiException("用户token异常", e);
        }
    }
}
