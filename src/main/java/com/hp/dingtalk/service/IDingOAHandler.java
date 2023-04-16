package com.hp.dingtalk.service;

import com.dingtalk.api.response.OapiProcessTemplateManageGetResponse;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.dingtalk.api.response.OapiProcessinstanceListidsResponse;
import com.hp.dingtalk.component.IDingApi;
import com.hp.dingtalk.component.application.IDingMiniH5;

import java.time.LocalDate;
import java.util.List;


/**
 * 钉钉OA审批相关
 *
 * @author hp
 */
public interface IDingOAHandler extends IDingApi {

    /**
     * 获取用户可见的所有模版编号信息
     *
     * @param app    企业内钉钉h5应用
     * @param userId 用户id
     * @return 薄膜编号
     */
    List<OapiProcessTemplateManageGetResponse.ProcessSimpleVO> getProcessTemplates(IDingMiniH5 app, String userId);

    /**
     * 根据审批模版名称获取审批模版编号
     *
     * @param app  企业内钉钉h5应用
     * @param name 模版名称
     * @return 模版编号
     */
    String getProcessTemplateCodeByName(IDingMiniH5 app, String name);

    /**
     * 根据审批模版编码查询实例id列表
     *
     * @param app         企业内钉钉h5应用
     * @param processCode 模版编号
     * @param start       开始时间
     * @return 模版实例id列表 及 下次分页指针
     */
    OapiProcessinstanceListidsResponse.PageResult getProcessInstanceIds(IDingMiniH5 app, String processCode, LocalDate start);

    /**
     * 根据审批模版编码查询实例id列表
     *
     * @param app         企业内钉钉h5应用
     * @param processCode 模版编号
     * @param start       开始时间
     * @param size        分页大小，最大20
     * @param cursor      分页指针
     * @return 模版实例id列表 及 下次分页指针
     */
    OapiProcessinstanceListidsResponse.PageResult getProcessInstanceIds(IDingMiniH5 app, String processCode, LocalDate start, Long size, Long cursor);

    /**
     * 根据审批模版编码查询实例id列表
     *
     * @param app         企业内钉钉h5应用
     * @param processCode 模版编号
     * @param start       开始时间
     * @param end         结束时间
     * @param size        分页大小，最大20
     * @param cursor      分页指针
     * @return 模版实例id列表 及 下次分页指针
     */
    OapiProcessinstanceListidsResponse.PageResult getProcessInstanceIds(IDingMiniH5 app, String processCode, LocalDate start, LocalDate end, Long size, Long cursor);

    /**
     * 根据审批模版编码查询实例id列表
     *
     * @param app         企业内钉钉h5应用
     * @param processCode 模版编号
     * @param start       开始时间
     * @param end         结束时间
     * @param size        分页大小，最大20
     * @param cursor      分页指针
     * @param userIds     发起请求的用户id
     * @return 模版实例id列表 及 下次分页指针
     */
    OapiProcessinstanceListidsResponse.PageResult getProcessInstanceIds(IDingMiniH5 app, String processCode, LocalDate start, LocalDate end, Long size, Long cursor, List<String> userIds);

    /**
     * 获取审批实例信息
     *
     * @param app               企业内钉钉h5应用
     * @param processInstanceId 审批实例id
     * @return 审批实例
     */
    OapiProcessinstanceGetResponse.ProcessInstanceTopVo getProcessInstance(IDingMiniH5 app, String processInstanceId);

}
