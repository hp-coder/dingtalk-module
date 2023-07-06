package com.hp.dingtalk.service.dept;

import com.dingtalk.api.request.OapiV2DepartmentListsubRequest;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import com.hp.dingtalk.component.application.IDingApp;
import com.hp.dingtalk.constant.Language;
import com.hp.dingtalk.service.AbstractDingOldApi;
import com.hp.dingtalk.service.IDingDeptHandler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.hp.dingtalk.constant.DingUrlConstant.GET_DEPT_LIST;

/**
 * 钉钉部门
 *
 * @author hp
 */
@Slf4j
public class DingDeptHandler extends AbstractDingOldApi implements IDingDeptHandler {

    public DingDeptHandler(@NonNull IDingApp app) {
        super(app);
    }

    @Override
    public List<OapiV2DepartmentListsubResponse.DeptBaseResponse> getDeptList() {
        return this.getDeptList(null, Language.zh_CN);
    }

    @Override
    public List<OapiV2DepartmentListsubResponse.DeptBaseResponse> getDeptList(Long deptId, Language language) {
        OapiV2DepartmentListsubRequest request = new OapiV2DepartmentListsubRequest();
        request.setDeptId(deptId);
        request.setLanguage(language.name());
        final OapiV2DepartmentListsubResponse response = execute(
                GET_DEPT_LIST,
                request,
                () -> "获取部门列表"
        );
        return response.getResult();
    }
}
