package com.hp.dingtalk.service.file.media;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMediaUploadRequest;
import com.dingtalk.api.response.OapiMediaUploadResponse;
import com.hp.dingtalk.component.application.IDingApp;
import com.hp.dingtalk.component.exception.DingApiException;
import com.hp.dingtalk.component.factory.token.DingAccessTokenFactory;
import com.hp.dingtalk.constant.DingConstant;
import com.hp.dingtalk.pojo.file.MediaRequest;
import com.hp.dingtalk.service.IDingMediaHandler;
import com.hp.dingtalk.utils.DingUtils;
import com.taobao.api.ApiException;
import com.taobao.api.FileItem;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * @author hp 2023/3/15
 */
public class DingMediaHandler implements IDingMediaHandler {

    @Override
    public OapiMediaUploadResponse upload(IDingApp app, MediaRequest request) throws IOException {
        final FileItem fileItem = request.toFileItem();
        Assert.isTrue(fileItem.isValid(), "文件不合法");
        DingTalkClient client = new DefaultDingTalkClient(DingConstant.MEDIA_UPLOAD);
        OapiMediaUploadRequest req = new OapiMediaUploadRequest();
        req.setType(request.getMediaType().name());
        req.setMedia(fileItem);
        try {
            final OapiMediaUploadResponse rsp = client.execute(req, DingAccessTokenFactory.access_token(app));
            DingUtils.isSuccess(rsp);
            return rsp;
        } catch (ApiException e) {
            throw new DingApiException("上传媒体文件异常", e);
        }
    }
}
