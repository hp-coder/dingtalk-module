package com.hp.dingding.service.api;

import com.dingtalk.api.response.OapiRoleListResponse;
import com.dingtalk.api.response.OapiRoleSimplelistResponse;
import com.hp.dingding.component.IDingApi;
import com.hp.dingding.component.application.IDingApp;

import java.util.List;

/**
 * 钉钉角色
 *
 * @author HP
 */
public interface IDingRoleHandler extends IDingApi {

    List<OapiRoleListResponse.OpenRole> roles(IDingApp app, Long page, Long size);

    List<OapiRoleSimplelistResponse.OpenEmpSimple> usersByRoleId(IDingApp app, Long roleId, Long page, Long size);
}
