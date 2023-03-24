package com.hp.dingding.service;

import com.aliyun.dingtalkoauth2_1_0.models.GetUserTokenResponseBody;
import com.hp.dingding.component.IDingApi;
import com.hp.dingding.component.application.IDingMiniH5;
import lombok.NonNull;

/**
 * @author hp 2023/3/23
 */
public interface IDingLoginHandler extends IDingApi {

    default com.aliyun.dingtalkoauth2_1_0.Client client() throws Exception {
        return new com.aliyun.dingtalkoauth2_1_0.Client(config());
    }

    enum GrantType{
        /**如果使用授权码换token，传authorization_code*/
        authorization_code,
        /**如果使用刷新token换用户token，传refresh_token*/
        refresh_token
    }

    /**
     * 登录时通过钉钉回调获取的授权码换取用户token，常用在登录
     *
     * @param app      微应用
     * @param authCode 授权码
     * @return userToken
     * @throws Exception 客户端实例化异常
     */
    GetUserTokenResponseBody getUserToken(IDingMiniH5 app, @NonNull String authCode, @NonNull GrantType grantType) throws Exception;
}
