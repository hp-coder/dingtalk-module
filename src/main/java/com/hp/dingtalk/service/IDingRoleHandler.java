package com.hp.dingtalk.service;

import com.dingtalk.api.response.OapiRoleListResponse;
import com.dingtalk.api.response.OapiRoleSimplelistResponse;
import com.hp.dingtalk.component.IDingApi;
import com.hp.dingtalk.component.application.IDingApp;

import java.util.List;

/**
 * 钉钉角色
 *
 * @author hp
 */
public interface IDingRoleHandler extends IDingApi {

    List<OapiRoleListResponse.OpenRole> roles(IDingApp app, Long page, Long size);

    List<OapiRoleSimplelistResponse.OpenEmpSimple> usersByRoleId(IDingApp app, Long roleId, Long page, Long size);
}
