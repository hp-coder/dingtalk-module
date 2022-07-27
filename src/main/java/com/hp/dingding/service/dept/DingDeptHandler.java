package com.hp.dingding.service.dept;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiV2DepartmentListsubRequest;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import com.google.gson.Gson;
import com.hp.dingding.component.application.IDingApp;
import com.hp.dingding.component.factory.DingAccessTokenFactory;
import com.hp.dingding.constant.DingConstant;
import com.hp.dingding.service.api.IDingDeptHandler;
import com.hp.dingding.utils.DingUtils;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

/**
 * 钉钉部门
 *
 * @Author: HP
 */
@Slf4j
public class DingDeptHandler implements IDingDeptHandler {

    @Override
    public List<OapiV2DepartmentListsubResponse.DeptBaseResponse> getDeptList(IDingApp app) {
        return this.getDeptList(app, null, "zh_CN");
    }

    @Override
    public List<OapiV2DepartmentListsubResponse.DeptBaseResponse> getDeptList(IDingApp app, Long deptId, String language) {
        DingTalkClient client = new DefaultDingTalkClient(DingConstant.GET_DEPT_LIST);
        OapiV2DepartmentListsubRequest req = new OapiV2DepartmentListsubRequest();
        req.setDeptId(deptId);
        req.setLanguage(language);
        OapiV2DepartmentListsubResponse rsp;
        try {
            rsp = client.execute(req, DingAccessTokenFactory.accessToken(app));
            DingUtils.isSuccess(rsp);
        } catch (ApiException e) {
            log.error("应用: {} : 获取部门列表: 部门: {} 语言: {}", app.getAppName(), deptId, language, e);
            throw new RuntimeException("获取部门列表出现异常");
        }
        return rsp.getResult();
    }
}
