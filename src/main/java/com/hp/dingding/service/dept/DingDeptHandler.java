package com.hp.dingding.service.dept;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiV2DepartmentListsubRequest;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import com.hp.dingding.component.application.IDingApp;
import com.hp.dingding.component.exception.DingApiException;
import com.hp.dingding.component.factory.token.DingAccessTokenFactory;
import com.hp.dingding.constant.DingConstant;
import com.hp.dingding.service.IDingDeptHandler;
import com.hp.dingding.utils.DingUtils;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 钉钉部门
 *
 * @author hp
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
            log.error("获取部门列表,应用:{},部门:{},语言:{}", app.getAppName(), deptId, language, e);
            throw new DingApiException("获取部门列表异常", e);
        }
        return rsp.getResult();
    }
}
