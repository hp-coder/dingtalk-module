package com.hp.dingding.service.role;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRoleListRequest;
import com.dingtalk.api.request.OapiRoleSimplelistRequest;
import com.dingtalk.api.response.OapiRoleListResponse;
import com.dingtalk.api.response.OapiRoleSimplelistResponse;
import com.hp.dingding.component.application.IDingApp;
import com.hp.dingding.component.factory.DingAccessTokenFactory;
import com.hp.dingding.constant.DingConstant;
import com.hp.dingding.service.api.IDingRoleHandler;
import com.hp.dingding.utils.DingUtils;
import com.taobao.api.ApiException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 钉钉角色
 *
 * @author HP
 */
public class DingRoleHandler implements IDingRoleHandler {

    @Override
    public List<OapiRoleListResponse.OpenRole> roles(IDingApp app, Long page, Long size) {
        page = page <= 0? 1: page;
        size = size <= 0 || size > 200 ? 200: size;
        DingTalkClient client = new DefaultDingTalkClient(DingConstant.GET_ROLE_LIST);
        OapiRoleListRequest req = new OapiRoleListRequest();
        req.setSize(size);
        req.setOffset((page - 1) * size);
        OapiRoleListResponse rsp;
        try {
            rsp = client.execute(req, DingAccessTokenFactory.accessToken(app));
            DingUtils.isSuccess(rsp);
        } catch (ApiException e) {
            throw new RuntimeException("获取角色列表失败", e);
        }
        return rsp.getResult()
                .getList()
                .stream()
                .map(OapiRoleListResponse.OpenRoleGroup::getRoles)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<OapiRoleSimplelistResponse.OpenEmpSimple> usersByRoleId(IDingApp app, Long roleId, Long page, Long size) {
        page = page <= 0? 1: page;
        size = size <= 0 || size > 100 ? 100: size;
        DingTalkClient client = new DefaultDingTalkClient(DingConstant.GET_USERS_BY_ROLE_ID);
        OapiRoleSimplelistRequest req = new OapiRoleSimplelistRequest();
        req.setRoleId(roleId);
        req.setSize(size);
        req.setOffset((page - 1) * size);
        OapiRoleSimplelistResponse rsp;
        try {
            rsp = client.execute(req, DingAccessTokenFactory.accessToken(app));
            DingUtils.isSuccess(rsp);
        } catch (ApiException e) {
            throw new RuntimeException("根据角色id获取用户列表失败", e);
        }
        return rsp.getResult().getList();
    }


}
