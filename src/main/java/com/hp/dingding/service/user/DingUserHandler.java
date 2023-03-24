package com.hp.dingding.service.user;


import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.hp.dingding.component.application.IDingApp;
import com.hp.dingding.component.application.IDingMiniH5;
import com.hp.dingding.component.exception.DingApiException;
import com.hp.dingding.component.factory.token.DingAccessTokenFactory;
import com.hp.dingding.constant.DingConstant;
import com.hp.dingding.service.IDingUserHandler;
import com.hp.dingding.service.role.DingRoleHandler;
import com.hp.dingding.utils.DingUtils;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author hp
 */
@Slf4j
public class DingUserHandler implements IDingUserHandler {

    public static final String MOBILE_REGEX = "^1[3456789]\\d{9}$";
    public static final Pattern PHONE_PATTERN = Pattern.compile(MOBILE_REGEX);

    @Override
    public String findUserIdByMobile(IDingApp app, String mobile) {
        log.info("根据手机获取用户id,app:{},mobile:{}", app.getAppName(), mobile);
        try {
            Assert.notNull(Collections.singleton(mobile), "请传入手机号码");
            Assert.isTrue(PHONE_PATTERN.matcher(mobile).matches(), "非法手机号");
            DingTalkClient client = new DefaultDingTalkClient(DingConstant.GET_USERID_BY_MOBILE);
            OapiV2UserGetbymobileRequest request = new OapiV2UserGetbymobileRequest();
            request.setMobile(mobile);
            request.setSupportExclusiveAccountSearch(false);
            OapiV2UserGetbymobileResponse response = client.execute(request, DingAccessTokenFactory.accessToken(app));
            log.debug("根据手机获取用户id,app:{},mobile:{},response-body:{}", app.getAppName(), mobile, response.getBody());
            DingUtils.UserResponse.isOk(response);
            return response.getResult().getUserid();
        } catch (ApiException e) {
            log.error("请求钉钉根据手机获取用户id,app:{},mobile:{},异常:{}", app.getAppName(), mobile, e.getMessage());
            throw new DingApiException("请求钉钉根据手机获取用户id异常", e);
        }
    }

    @Override
    public String unionIdByCode(IDingApp app, String code) {
        log.info("根据临时码获取unionId,app:{},code:{}", app.getAppName(), code);
        try {
            // 通过临时授权码获取授权用户的个人信息
            DefaultDingTalkClient client2 = new DefaultDingTalkClient(DingConstant.GET_USER_INFO_BY_CODE);
            OapiSnsGetuserinfoBycodeRequest request = new OapiSnsGetuserinfoBycodeRequest();
            // 通过扫描二维码，跳转指定的redirect_uri后，向url中追加的code临时授权码
            request.setTmpAuthCode(code);
            // 修改appid和appSecret为步骤三创建扫码登录时创建的appid和appSecret
            OapiSnsGetuserinfoBycodeResponse response = client2.execute(request, app.getAppKey(), app.getAppSecret());
            log.debug("根据临时码获取unionId,app:{},code:{},response-body:{}", app.getAppName(), code, response.getBody());
            DingUtils.UserResponse.isOk(response);
            // 根据unionId获取userId
            return response.getUserInfo().getUnionid();
        } catch (ApiException e) {
            log.error("根据临时码获取unionId,app:{},code:{},err:{}", app.getAppName(), code, e.getLocalizedMessage(), e);
            throw new DingApiException("根据临时码获取unionId异常", e);
        }
    }

    @Override
    public String userIdByUnionId(IDingApp app, String unionId) {
        log.info("根据unionId获取userId,app:{},unionId:{}", app.getAppName(), unionId);
        try {
            DingTalkClient clientDingTalkClient = new DefaultDingTalkClient(DingConstant.GET_USER_ID_BY_UNION_ID);
            OapiUserGetbyunionidRequest request = new OapiUserGetbyunionidRequest();
            request.setUnionid(unionId);
            OapiUserGetbyunionidResponse response = clientDingTalkClient.execute(request, DingAccessTokenFactory.accessToken(app));
            log.debug("根据unionId获取userId,app:{},unionId:{},response-body:{}", app.getAppName(), unionId, response.getBody());
            DingUtils.UserResponse.isOk(response);
            // 后续根据userId获取用户信息
            return response.getResult().getUserid();
        } catch (ApiException e) {
            log.error("根据unionId获取userId,app:{},unionId:{},err:{}", app.getAppName(), unionId, e.getLocalizedMessage(), e);
            throw new DingApiException("根据unionId获取钉钉userId异常", e);
        }
    }

    @Override
    public OapiV2UserGetResponse.UserGetResponse userByUserId(IDingApp app, String userId) {
        log.info("根据userId获取钉钉用户信息,app:{},userId:{}", app.getAppName(), userId);
        final String language = "zh_CN";
        try {
            DingTalkClient clientDingTalkClient2 = new DefaultDingTalkClient(DingConstant.GET_USER_BY_USER_ID);
            OapiV2UserGetRequest request = new OapiV2UserGetRequest();
            request.setUserid(userId);
            request.setLanguage(language);
            OapiV2UserGetResponse response = clientDingTalkClient2.execute(request, DingAccessTokenFactory.accessToken(app));
            log.info("根据userId获取钉钉用户信息,app:{},userId:{},response-body:{}", app.getAppName(), userId, response.getBody());
            DingUtils.UserResponse.isOk(response);
            return response.getResult();
        } catch (ApiException e) {
            log.error("根据userId获取钉钉用户信息,,app:{},userId:{},err:{}", app.getAppName(), userId, e.getLocalizedMessage(), e);
            throw new DingApiException("根据userId获取钉钉用户信息异常", e);
        }
    }

    @Override
    public OapiV2UserGetResponse.UserGetResponse userByMobile(IDingApp app, String mobile) {
        final String userId = this.findUserIdByMobile(app, mobile);
        return this.userByUserId(app, userId);
    }

    @Override
    public OapiV2UserGetResponse.UserGetResponse userByCode(IDingApp app, String code) {
        final String unionId = unionIdByCode(app, code);
        final String userId = userIdByUnionId(app, unionId);
        return userByUserId(app, userId);
    }

    @Override
    public OapiV2UserGetuserinfoResponse.UserGetByCodeResponse userByLoginAuthCode(IDingMiniH5 app, String authCode) {
        DingTalkClient client = new DefaultDingTalkClient(DingConstant.GET_USER_BY_LOGIN_AUTH_CODE);
        OapiV2UserGetuserinfoRequest request = new OapiV2UserGetuserinfoRequest();
        request.setCode(authCode);
        try {
            OapiV2UserGetuserinfoResponse response = client.execute(request, DingAccessTokenFactory.access_token(app));
            DingUtils.UserResponse.isOk(response);
            return response.getResult();
        } catch (ApiException e) {
            log.error("根据钉钉应用内免登录授权码获取钉钉用户信息异常,,app:{},code:{},err:{}", app.getAppName(), authCode, e.getLocalizedMessage(), e);
            throw new DingApiException("根据钉钉应用内免登录授权码获取钉钉用户信息异常", e);
        }
    }

    @Override
    public Set<OapiRoleSimplelistResponse.OpenEmpSimple> getAllUsers(IDingApp app) {
        final DingRoleHandler roleHandler = new DingRoleHandler();
        final List<OapiRoleListResponse.OpenRole> roles = roleHandler.roles(app, 1L, 200L);
        return roles.parallelStream().map(role -> roleHandler.usersByRoleId(app, role.getId(), 1L, 100L)).flatMap(Collection::stream).collect(Collectors.toSet());
    }
}
