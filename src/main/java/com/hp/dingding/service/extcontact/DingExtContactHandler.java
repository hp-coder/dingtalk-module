package com.hp.dingding.service.extcontact;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.google.gson.Gson;
import com.hp.dingding.component.application.IDingMiniH5;
import com.hp.dingding.component.factory.DingAccessTokenFactory;
import com.hp.dingding.constant.DingConstant;
import com.hp.dingding.service.api.IDingExtContactHandler;
import com.hp.dingding.utils.DingUtils;
import com.taobao.api.ApiException;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 钉钉企业外部联系人接口
 *
 * @author HP
 */
public class DingExtContactHandler implements IDingExtContactHandler {

    @Override
    public OapiExtcontactGetResponse.OpenExtContact getExtContactByDingUserId(IDingMiniH5 app, String userId) {
        Assert.notNull(app, "调用接口应用不能为空");
        Assert.hasText(userId, "userId不能为空");
        DingTalkClient client = new DefaultDingTalkClient(DingConstant.GET_EXTCONTACT_INFO);
        OapiExtcontactGetRequest req = new OapiExtcontactGetRequest();
        req.setUserId(userId);
        OapiExtcontactGetResponse rsp;
        try {
            rsp = client.execute(req, DingAccessTokenFactory.accessToken(app));
            if (!Objects.equals(rsp.getErrcode(),0L)){
                throw new ApiException(new Gson().toJson(rsp));
            }
        } catch (ApiException e) {
            throw new RuntimeException("获取钉钉外部联系人信息异常", e);
        }
        return rsp.getResult();
    }


    public String addExtContact(IDingMiniH5 app, OapiExtcontactCreateRequest.OpenExtContact payload) {
        DingTalkClient client = new DefaultDingTalkClient(DingConstant.ADD_EXTCONTACT);
        OapiExtcontactCreateRequest req = new OapiExtcontactCreateRequest();
        req.setContact(payload);
        OapiExtcontactCreateResponse rsp;
        try {
            rsp = client.execute(req, DingAccessTokenFactory.accessToken(app));
            if (!Objects.equals(rsp.getErrcode(),0L)){
                throw new ApiException(new Gson().toJson(rsp));
            }
        } catch (ApiException e) {
            e.printStackTrace();
            throw new RuntimeException("添加外部联系人失败", e);
        }
        return rsp.getUserid();
    }

    @Override
    public String addExtContact(IDingMiniH5 app, String title, List<Long> labelIds, List<Long> shareDeptIds,
                                String address, String remark, String followerUserId, String name,
                                String stateCode, String companyName, List<String> shareUserIds, String mobile
    ) {
        Assert.notNull(app, "调用接口应用不能为空");
        Assert.notNull(labelIds, "联系人标签不能为空");
        Assert.hasText(followerUserId, "关注用户ID不能为空");
        Assert.hasText(name, "联系人名称不能为空");
        Assert.hasText(mobile, "联系人电话不能为空");
        Assert.isTrue(labelIds.size() <= 20, "labelIds一次最多20个");
        Assert.hasText(address,"地址不能为空");
        Assert.hasText(companyName,"企业名称不能为空");
        if (StringUtils.isEmpty(stateCode)) {
            stateCode = "86";
        }
        if (!CollectionUtils.isEmpty(shareDeptIds)) {
            Assert.isTrue(shareDeptIds.size() <= 20, "shareDeptIds一次最多20个");
        }
        if (!CollectionUtils.isEmpty(shareUserIds)) {
            Assert.isTrue(shareUserIds.size() <= 20, "shareUserIds一次最多20个");
        }
        OapiExtcontactCreateRequest.OpenExtContact contact = new OapiExtcontactCreateRequest.OpenExtContact();
        contact.setTitle(title);
        contact.setLabelIds(labelIds);
        contact.setShareDeptIds(shareDeptIds);
        contact.setFollowerUserId(followerUserId);
        contact.setName(name);
        contact.setAddress(address);
        contact.setStateCode(stateCode);
        contact.setCompanyName(companyName);
        contact.setShareUserIds(shareUserIds);
        contact.setMobile(mobile);
        return this.addExtContact(app,contact);
    }

    @Override
    public String addExtContact(IDingMiniH5 app, List<Long> labelIds, String followerUserId, String address, String name, String stateCode, String companyName, String mobile) {
        return addExtContact(app, null, labelIds, null,
                address, null, followerUserId, name,
                stateCode, companyName, null, mobile);
    }

    @Override
    public String addExtContact(IDingMiniH5 app, List<Long> labelIds, String followerUserId, String address, String name, String companyName, String mobile) {
        return addExtContact(app, labelIds, followerUserId, address, name, null, companyName, mobile);
    }

    @Override
    public void updateExtContact(IDingMiniH5 app, String userId, String title, List<Long> labelIds, List<Long> shareDeptIds,
                                 String address, String remark, String followerUserId, String name,
                                 String companyName, List<String> shareUserIds) {
        Assert.notNull(app, "调用接口应用不能为空");
        Assert.hasText(userId, "联系人userId不能为空");
        Assert.notNull(labelIds, "联系人标签不能为空");
        Assert.hasText(followerUserId, "关注用户ID不能为空");
        Assert.hasText(name, "联系人名称不能为空");
        Assert.isTrue(labelIds.size() <= 20, "labelIds一次最多20个");
        if (!CollectionUtils.isEmpty(shareDeptIds)) {
            Assert.isTrue(shareDeptIds.size() <= 20, "shareDeptIds一次最多20个");
        }
        if (!CollectionUtils.isEmpty(shareUserIds)) {
            Assert.isTrue(shareUserIds.size() <= 20, "shareUserIds一次最多20个");
        }
        OapiExtcontactUpdateRequest.OpenExtContact contact = new OapiExtcontactUpdateRequest.OpenExtContact();
        contact.setTitle(title);
        contact.setLabelIds(labelIds);
        contact.setShareDeptIds(shareDeptIds);
        contact.setAddress(address);
        contact.setRemark(remark);
        contact.setFollowerUserId(followerUserId);
        contact.setName(name);
        contact.setUserId(userId);
        contact.setCompanyName(companyName);
        contact.setShareUserIds(shareUserIds);
        this.updateExtContact(app, contact);
    }

    @Override
    public void updateExtContact(IDingMiniH5 app, String userId, List<Long> labelIds, String followerUserId, String address, String name, String companyName, String mobile) {
        this.updateExtContact(app, userId, null, labelIds, null, address, null, followerUserId, name, companyName, null);
    }

    public void updateExtContact(IDingMiniH5 app, OapiExtcontactUpdateRequest.OpenExtContact payload) {
        DingTalkClient client = new DefaultDingTalkClient(DingConstant.UPDATE_EXTCONTACT);
        OapiExtcontactUpdateRequest req = new OapiExtcontactUpdateRequest();
        req.setContact(payload);
        try {
            final OapiExtcontactUpdateResponse rsp = client.execute(req, DingAccessTokenFactory.accessToken(app));
            DingUtils.isSuccess(rsp);
        } catch (ApiException e) {
            throw new RuntimeException("更新外部联系人失败", e);
        }
    }

    @Override
    public void deleteExtContactByDingUserId(IDingMiniH5 app, String userId) {
        Assert.notNull(app, "调用接口应用不能为空");
        Assert.hasText(userId, "联系人userId不能为空");
        DingTalkClient client = new DefaultDingTalkClient(DingConstant.DELETE_EXTCONTACT);
        OapiExtcontactDeleteRequest req = new OapiExtcontactDeleteRequest();
        req.setUserId(userId);
        try {
            final OapiExtcontactDeleteResponse rsp = client.execute(req, DingAccessTokenFactory.accessToken(app));
            if (!Objects.equals(rsp.getErrcode(),0L) && !Objects.equals(rsp.getErrcode(),33012L)){
                throw new ApiException(new Gson().toJson(rsp));
            }
        } catch (ApiException e) {
            throw new RuntimeException("删除外部联系人失败", e);
        }
    }

    @Override
    public List<OapiExtcontactListResponse.OpenExtContact> getExtContacts(IDingMiniH5 app, Long page, Long size) {
        Assert.notNull(app, "调用接口应用不能为空");
        Assert.notNull(page, "page不能为空");
        Assert.isTrue(page >= 1, "page必须大于1");
        Assert.notNull(size, "size不能为空");
        Assert.isTrue(size >= 0, "size必须大于0");
        DingTalkClient client = new DefaultDingTalkClient(DingConstant.GET_EXTCONTACTS);
        OapiExtcontactListRequest req = new OapiExtcontactListRequest();
        req.setSize(20L);
        req.setOffset(0L);
        OapiExtcontactListResponse rsp;
        try {
            rsp = client.execute(req, DingAccessTokenFactory.accessToken(app));
            DingUtils.isSuccess(rsp);
        } catch (ApiException e) {
            throw new RuntimeException("获取外部联系人列表失败", e);
        }
        return rsp.getResults();
    }

    @Override
    public List<OapiExtcontactListlabelgroupsResponse.OpenLabelGroup> getExtContactTags(IDingMiniH5 app, Long page, Long size) {
        Assert.notNull(app, "调用接口应用不能为空");
        Assert.notNull(page, "page不能为空");
        Assert.isTrue(page >= 1, "page必须大于1");
        Assert.notNull(size, "size不能为空");
        Assert.isTrue(size >= 0, "size必须大于0");
        DingTalkClient client = new DefaultDingTalkClient(DingConstant.GET_EXTCONTACT_TAGS);
        OapiExtcontactListlabelgroupsRequest req = new OapiExtcontactListlabelgroupsRequest();
        req.setSize(size);
        req.setOffset((page - 1) * size);
        OapiExtcontactListlabelgroupsResponse rsp;
        try {
            rsp = client.execute(req, DingAccessTokenFactory.accessToken(app));
            DingUtils.isSuccess(rsp);
        } catch (ApiException e) {
            throw new RuntimeException("获取外部联系人标签列表失败", e);
        }
        return rsp.getResults();
    }
}
