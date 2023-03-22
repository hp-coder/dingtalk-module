package com.hp.dingding.service.api;

import com.dingtalk.api.response.OapiMediaUploadResponse;
import com.hp.dingding.component.IDingApi;
import com.hp.dingding.component.application.IDingApp;
import com.hp.dingding.pojo.file.MediaRequest;

/**
 * @author hp 2023/3/15
 */
public interface IDingMediaHandler extends IDingApi {

    OapiMediaUploadResponse upload(IDingApp app, MediaRequest mediaRequest);
}
