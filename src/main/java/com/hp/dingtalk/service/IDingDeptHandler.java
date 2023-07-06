package com.hp.dingtalk.service;

import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import com.hp.dingtalk.constant.Language;
import lombok.NonNull;

import java.util.List;

/**
 * 钉钉部门
 *
 * @author hp
 */
public interface IDingDeptHandler {

    /**
     * 获取所有部门
     *
     * @return 部门集合
     */
    List<OapiV2DepartmentListsubResponse.DeptBaseResponse> getDeptList();

    /**
     * 获取所有部门
     *
     * @param deptId   指定部门id
     * @param language 语言：zh_CN 或 en_US
     * @return 部门集合
     */
    List<OapiV2DepartmentListsubResponse.DeptBaseResponse> getDeptList(Long deptId, @NonNull Language language);
}
