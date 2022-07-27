package com.hp.dingding.service.user;


import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiSnsGetuserinfoBycodeRequest;
import com.dingtalk.api.request.OapiUserGetbyunionidRequest;
import com.dingtalk.api.request.OapiV2UserGetRequest;
import com.dingtalk.api.request.OapiV2UserGetbymobileRequest;
import com.dingtalk.api.response.*;
import com.hp.dingding.component.application.IDingApp;
import com.hp.dingding.component.factory.DingAccessTokenFactory;
import com.hp.dingding.constant.DingConstant;
import com.hp.dingding.service.api.IDingUserHandler;
import com.hp.dingding.service.role.DingRoleHandler;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class DingUserHandler implements IDingUserHandler {

    public static final String REGEX_MOBILE = "^1[3456789]\\d{9}$";

    public static final Pattern pattern = Pattern.compile(REGEX_MOBILE);

    @Override
    public String findUserIdByMobile(IDingApp app, String mobile) {
        try {
            Assert.notNull(Collections.singleton(mobile), "请传入手机号码");
            Assert.isTrue(pattern.matcher(mobile).matches(),"非法手机号");
            log.info("应用: {} :请求钉钉根据手机获取用户id: 手机: {}", app.getAppName(), mobile);
            DingTalkClient client = new DefaultDingTalkClient(DingConstant.GET_USERID_BY_MOBILE);
            OapiV2UserGetbymobileRequest req = new OapiV2UserGetbymobileRequest();
            req.setMobile(mobile);
            req.setSupportExclusiveAccountSearch(false);
            OapiV2UserGetbymobileResponse rsp = client.execute(req, DingAccessTokenFactory.accessToken(app));
            log.info("应用: {} :请求钉钉根据手机获取用户id: 手机: {} 响应: {}", app.getAppName(), mobile, rsp.getBody());
            if ("找不到该用户".equals(rsp.getErrmsg()) || Long.valueOf(60121).equals(rsp.getErrcode())) {
                return null;
            }
            return rsp.getResult().getUserid();
        } catch (ApiException e) {
            log.error("应用: {} :请求钉钉根据手机获取用户id: 手机: {} 异常: {}", app.getAppName(), mobile, e.getMessage());
            throw new RuntimeException("请求钉钉根据手机获取用户id出现异常");
        }
    }

    @Override
    public String unionIdByCode(IDingApp app, String code) {
        try {
            // 通过临时授权码获取授权用户的个人信息
            DefaultDingTalkClient client2 = new DefaultDingTalkClient(DingConstant.GET_USER_INFO_BY_CODE);
            OapiSnsGetuserinfoBycodeRequest reqBycodeRequest = new OapiSnsGetuserinfoBycodeRequest();
            // 通过扫描二维码，跳转指定的redirect_uri后，向url中追加的code临时授权码
            reqBycodeRequest.setTmpAuthCode(code);
            // 修改appid和appSecret为步骤三创建扫码登录时创建的appid和appSecret
            OapiSnsGetuserinfoBycodeResponse bycodeResponse = client2.execute(reqBycodeRequest, app.getAppKey(), app.getAppSecret());
            // 根据unionid获取userid
            return bycodeResponse.getUserInfo().getUnionid();
        } catch (ApiException e) {
            log.error("根据code获取钉钉unionId异常: 应用：{}，异常：{}", app.getAppName(), e.getCause(), e);
            throw new RuntimeException("根据code获取钉钉unionId异常");
        }
    }

    @Override
    public String userIdByUnionId(IDingApp app, String unionId) {
        try {
            DingTalkClient clientDingTalkClient = new DefaultDingTalkClient(DingConstant.GET_USER_ID_BY_UNION_ID);
            OapiUserGetbyunionidRequest reqGetbyunionidRequest = new OapiUserGetbyunionidRequest();
            reqGetbyunionidRequest.setUnionid(unionId);
            OapiUserGetbyunionidResponse oapiUserGetbyunionidResponse = clientDingTalkClient.execute(reqGetbyunionidRequest, DingAccessTokenFactory.accessToken(app));
            if (oapiUserGetbyunionidResponse.getErrcode() == 60121L) {
                return oapiUserGetbyunionidResponse.getBody();
            }
            // 根据userId获取用户信息
            return oapiUserGetbyunionidResponse.getResult().getUserid();
        } catch (ApiException e) {
            log.error("根据unionId获取钉钉userId异常: 应用：{}，异常：{}", app.getAppName(), e.getCause(), e);
            throw new RuntimeException("根据unionId获取钉钉userId异常");
        }
    }

    @Override
    public OapiV2UserGetResponse.UserGetResponse userByUserId(IDingApp app, String userId) {
        try {
            DingTalkClient clientDingTalkClient2 = new DefaultDingTalkClient(DingConstant.GET_USER_BY_USER_ID);
            OapiV2UserGetRequest request = new OapiV2UserGetRequest();
            request.setUserid(userId);
            request.setLanguage("zh_CN");
            OapiV2UserGetResponse response = clientDingTalkClient2.execute(request, DingAccessTokenFactory.accessToken(app));
            return response.getResult();
        } catch (ApiException e) {
            log.error("根据userId获取钉钉用户信息异常: 应用：{}，异常：{}", app.getAppName(), e.getCause(), e);
            throw new RuntimeException("根据userId获取钉钉用户信息异常");
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
    public Set<OapiRoleSimplelistResponse.OpenEmpSimple> getAllUsers(IDingApp app) {
        final DingRoleHandler roleHandler = new DingRoleHandler();
        final List<OapiRoleListResponse.OpenRole> roles = roleHandler.roles(app, 1L, 200L);
        return roles.parallelStream().map(role ->
                        roleHandler.usersByRoleId(app, role.getId(), 1L, 100L))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

}
