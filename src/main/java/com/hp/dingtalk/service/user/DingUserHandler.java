package com.hp.dingtalk.service.user;


import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.hp.dingtalk.component.application.IDingApp;
import com.hp.dingtalk.component.application.IDingMiniH5;
import com.hp.dingtalk.component.exception.DingApiException;
import com.hp.dingtalk.component.factory.token.DingAccessTokenFactory;
import com.hp.dingtalk.constant.DeptUserOrder;
import com.hp.dingtalk.constant.DingConstant;
import com.hp.dingtalk.constant.Language;
import com.hp.dingtalk.service.IDingUserHandler;
import com.hp.dingtalk.service.role.DingRoleHandler;
import com.hp.dingtalk.utils.DingUtils;
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
        log.debug("根据手机获取用户id,app:{},mobile:{}", app.getAppName(), mobile);
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
    public String findUnionIdByCode(IDingApp app, String code) {
        log.debug("根据临时码获取unionId,app:{},code:{}", app.getAppName(), code);
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
    public String findUserIdByUnionId(IDingApp app, String unionId) {
        log.debug("根据unionId获取userId,app:{},unionId:{}", app.getAppName(), unionId);
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
    public OapiV2UserGetResponse.UserGetResponse findUserByUserId(IDingApp app, String userId) {
        log.debug("根据userId获取钉钉用户信息,app:{},userId:{}", app.getAppName(), userId);
        try {
            DingTalkClient clientDingTalkClient2 = new DefaultDingTalkClient(DingConstant.GET_USER_BY_USER_ID);
            OapiV2UserGetRequest request = new OapiV2UserGetRequest();
            request.setUserid(userId);
            request.setLanguage(Language.zh_CN.name());
            OapiV2UserGetResponse response = clientDingTalkClient2.execute(request, DingAccessTokenFactory.accessToken(app));
            log.debug("根据userId获取钉钉用户信息,app:{},userId:{},response-body:{}", app.getAppName(), userId, response.getBody());
            DingUtils.UserResponse.isOk(response);
            return response.getResult();
        } catch (ApiException e) {
            log.error("根据userId获取钉钉用户信息,,app:{},userId:{},err:{}", app.getAppName(), userId, e.getLocalizedMessage(), e);
            throw new DingApiException("根据userId获取钉钉用户信息异常", e);
        }
    }

    @Override
    public OapiV2UserGetResponse.UserGetResponse findUserByMobile(IDingApp app, String mobile) {
        final String userId = this.findUserIdByMobile(app, mobile);
        return this.findUserByUserId(app, userId);
    }

    @Override
    public OapiV2UserGetResponse.UserGetResponse findUserByCode(IDingApp app, String code) {
        final String unionId = findUnionIdByCode(app, code);
        final String userId = findUserIdByUnionId(app, unionId);
        return findUserByUserId(app, userId);
    }

    @Override
    public OapiV2UserGetuserinfoResponse.UserGetByCodeResponse findUserByLoginAuthCode(IDingMiniH5 app, String authCode) {
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

    @Override
    public OapiUserListsimpleResponse.PageResult findNameAndUserIdByDept(IDingApp app, Long deptId, Long cursor, Long size) {
        return findNameAndUserIdByDept(app, deptId, cursor, size, DeptUserOrder.custom, true, Language.zh_CN);
    }

    @Override
    public OapiUserListsimpleResponse.PageResult findNameAndUserIdByDept(IDingApp app, Long deptId, Long cursor, Long size, DeptUserOrder order, Boolean containAccessLimit, Language language) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/user/listsimple");
        OapiUserListsimpleRequest req = new OapiUserListsimpleRequest();
        req.setDeptId(deptId);
        req.setCursor(cursor);
        req.setSize(size);
        req.setOrderField(order.name());
        req.setContainAccessLimit(containAccessLimit);
        req.setLanguage(language.name());
        try {
            OapiUserListsimpleResponse response = client.execute(req, DingAccessTokenFactory.access_token(app));
            DingUtils.UserResponse.isOk(response);
            return response.getResult();
        } catch (ApiException e) {
            log.error("获取钉钉部门下用户基础信息异常,app:{},deptId:{},err:{}", app.getAppName(), deptId, e.getLocalizedMessage(), e);
            throw new DingApiException("获取钉钉部门下用户基础信息异常", e);
        }
    }
}
