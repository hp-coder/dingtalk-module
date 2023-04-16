package com.hp.dingtalk.service;

import com.dingtalk.api.response.OapiMediaUploadResponse;
import com.hp.dingtalk.component.IDingApi;
import com.hp.dingtalk.component.application.IDingApp;
import com.hp.dingtalk.pojo.file.MediaRequest;

import java.io.IOException;

/**
 * @author hp 2023/3/15
 */
public interface IDingMediaHandler extends IDingApi {

    OapiMediaUploadResponse upload(IDingApp app, MediaRequest mediaRequest) throws IOException;
}
