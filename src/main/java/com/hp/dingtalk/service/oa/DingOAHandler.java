package com.hp.dingtalk.service.oa;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.hp.dingtalk.component.application.IDingMiniH5;
import com.hp.dingtalk.component.exception.DingApiException;
import com.hp.dingtalk.component.factory.token.DingAccessTokenFactory;
import com.hp.dingtalk.pojo.oa.CreateProcessInstanceRequest;
import com.hp.dingtalk.utils.DingUtils;
import com.taobao.api.ApiException;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.hp.dingtalk.constant.DingConstant.*;

/**
 * @author hp
 */
public class DingOAHandler extends AbstractOAHandler {

    public DingOAHandler(IDingMiniH5 app) {
        super(app);
    }

    @Override
    public String createProcessInstance(CreateProcessInstanceRequest request) {
        DingTalkClient client = new DefaultDingTalkClient(CREATE_PROCESS_INSTANCE);
        final OapiProcessinstanceCreateRequest req = request.request();
        req.setAgentId(this.app.getAppId());
        OapiProcessinstanceCreateResponse rsp;
        try {
            rsp = client.execute(req, DingAccessTokenFactory.access_token(app));
            DingUtils.isSuccess(rsp);
        } catch (ApiException e) {
            throw new DingApiException("创建钉钉OA审批实例失败", e);
        }
        return rsp.getProcessInstanceId();
    }

    @Override
    public List<OapiProcessTemplateManageGetResponse.ProcessSimpleVO> getManageableProcessTemplatesInCorp(String userId) {
        Assert.notNull(app, "钉钉应用不能为空");
        Assert.hasText(userId, "用户id不能为空");
        DingTalkClient client = new DefaultDingTalkClient(GET_MANAGEABLE_PROCESS_TEMPLATE_IN_CORP_LIST);
        OapiProcessTemplateManageGetRequest req = new OapiProcessTemplateManageGetRequest();
        req.setUserid(userId);
        OapiProcessTemplateManageGetResponse rsp;
        try {
            rsp = client.execute(req, DingAccessTokenFactory.access_token(app));
            DingUtils.isSuccess(rsp);
        } catch (ApiException e) {
            throw new DingApiException("根据用户id获取审批模版列表失败", e);
        }
        return rsp.getResult();
    }

    @Override
    public String getProcessTemplateCodeByName(String name) {
        Assert.notNull(app, "钉钉应用不能为空");
        Assert.hasText(name, "模版名称不能为空");
        DingTalkClient client = new DefaultDingTalkClient(GET_PROCESS_TEMPLATE_CODE_BY_NAME);
        OapiProcessGetByNameRequest req = new OapiProcessGetByNameRequest();
        req.setName(name);
        OapiProcessGetByNameResponse rsp;
        try {
            rsp = client.execute(req, DingAccessTokenFactory.access_token(app));
            DingUtils.isSuccess(rsp);
        } catch (ApiException e) {
            throw new DingApiException("根据名称获取审批模版编号失败", e);
        }
        return rsp.getProcessCode();
    }

    @Override
    public OapiProcessinstanceListidsResponse.PageResult getProcessInstanceIds(String processCode, LocalDate start) {
        return getProcessInstanceIds(processCode, start, 10L, 0L);
    }

    @Override
    public OapiProcessinstanceListidsResponse.PageResult getProcessInstanceIds(String processCode, LocalDate start, Long size, Long cursor) {
        return getProcessInstanceIds(processCode, start, LocalDate.now(), size, cursor);
    }

    @Override
    public OapiProcessinstanceListidsResponse.PageResult getProcessInstanceIds(String processCode, LocalDate start, LocalDate end, Long size, Long cursor) {
        return getProcessInstanceIds(processCode, start, end, size, cursor, null);
    }

    @Override
    public OapiProcessinstanceListidsResponse.PageResult getProcessInstanceIds(String processCode, LocalDate start, LocalDate end, Long size, Long cursor, List<String> userIds) {
        Assert.notNull(app, "钉钉应用不能为空");
        Assert.hasText(processCode, "模版编号不能为空");
        Assert.isTrue(0 < size && size <= 20, "分页参数，每页大小，最多传20");
        Assert.notNull(start, "开始时间不能为空");
        end = end == null ? LocalDate.now() : end;
        Assert.isTrue(end.minusDays(120L).compareTo(start) <= 0, "时间范围不能超过120天");
        Assert.isTrue(LocalDate.now().minusDays(365L).compareTo(start) <= 0, "start时间距当前时间不能超过365天");
        DingTalkClient client = new DefaultDingTalkClient(GET_PROCESS_INSTANCE_IDS);
        OapiProcessinstanceListidsRequest req = new OapiProcessinstanceListidsRequest();
        req.setProcessCode(processCode);
        req.setStartTime(start.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        req.setEndTime(end.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        req.setSize(size);
        req.setCursor(cursor);
        req.setUseridList(CollectionUtils.isEmpty(userIds) ? null : String.join(",", userIds));
        OapiProcessinstanceListidsResponse rsp;
        try {
            rsp = client.execute(req, DingAccessTokenFactory.access_token(app));
            DingUtils.isSuccess(rsp);
        } catch (ApiException e) {
            throw new DingApiException("获取审批模版实例id列表失败：", e);
        }
        return rsp.getResult();
    }

    @Override
    public OapiProcessinstanceGetResponse.ProcessInstanceTopVo getProcessInstance(String processInstanceId) {
        DingTalkClient client = new DefaultDingTalkClient(GET_PROCESS_INSTANCE);
        OapiProcessinstanceGetRequest req = new OapiProcessinstanceGetRequest();
        req.setProcessInstanceId(processInstanceId);
        OapiProcessinstanceGetResponse rsp;
        try {
            rsp = client.execute(req, DingAccessTokenFactory.access_token(app));
            DingUtils.isSuccess(rsp);
        } catch (ApiException e) {
            throw new DingApiException("获取审批实例失败：", e);
        }
        return rsp.getProcessInstance();
    }
}
