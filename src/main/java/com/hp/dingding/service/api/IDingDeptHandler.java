package com.hp.dingding.service.api;

import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import com.hp.dingding.component.IDingApi;
import com.hp.dingding.component.application.IDingApp;

import java.util.List;

/**
 * 钉钉部门
 *
 * @author HP
 */
public interface IDingDeptHandler extends IDingApi {

    /**
     * 获取所有部门
     *
     * @param app 钉钉应用
     * @return 部门集合
     */
    List<OapiV2DepartmentListsubResponse.DeptBaseResponse> getDeptList(IDingApp app);

    /**
     * 获取所有部门
     *
     * @param app      钉钉应用
     * @param deptId   指定部门id
     * @param language 语言：zh_CN 或 en_US
     * @return 部门集合
     */
    List<OapiV2DepartmentListsubResponse.DeptBaseResponse> getDeptList(IDingApp app, Long deptId, String language);
}
