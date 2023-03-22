package com.hp.dingding.service.file.media;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMediaUploadRequest;
import com.dingtalk.api.response.OapiMediaUploadResponse;
import com.google.gson.Gson;
import com.hp.dingding.component.application.IDingApp;
import com.hp.dingding.component.exception.DingApiException;
import com.hp.dingding.component.factory.DingAccessTokenFactory;
import com.hp.dingding.constant.DingConstant;
import com.hp.dingding.pojo.file.MediaRequest;
import com.hp.dingding.service.api.IDingMediaHandler;
import com.taobao.api.ApiException;
import com.taobao.api.FileItem;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * @author hp 2023/3/15
 */
public class DingMediaHandler implements IDingMediaHandler {

    @Override
    public OapiMediaUploadResponse upload(IDingApp app, MediaRequest request) {
        final FileItem fileItem = request.toFileItem();
        Assert.isTrue(fileItem.isValid(), "文件不合法");
        DingTalkClient client = new DefaultDingTalkClient(DingConstant.MEDIA_UPLOAD);
        OapiMediaUploadRequest req = new OapiMediaUploadRequest();
        req.setType(request.getMediaType().name());
        req.setMedia(fileItem);
        try {
            final OapiMediaUploadResponse rsp = client.execute(req, DingAccessTokenFactory.accessToken(app));
            if (!Objects.equals(rsp.getErrcode(), 0L)) {
                throw new ApiException(new Gson().toJson(rsp));
            }
            return rsp;
        } catch (ApiException e) {
            throw new DingApiException("上传媒体文件异常", e);
        }
    }
}
