package com.hp.dingtalk.service;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.hp.common.base.annotations.FieldDesc;
import com.hp.dingtalk.component.IDingOldApi;
import com.hp.dingtalk.component.application.IDingApp;
import com.hp.dingtalk.component.exception.DingApiException;
import com.hp.dingtalk.component.factory.token.DingAccessTokenFactory;
import com.hp.dingtalk.utils.DingUtils;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoRequest;
import com.taobao.api.TaobaoResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author hp
 */
@Slf4j
public abstract class AbstractDingOldApi implements IDingOldApi {

    @FieldDesc("不再对方法级别提供支持")
    protected final IDingApp app;

    public AbstractDingOldApi(@NonNull IDingApp app) {
        this.app = app;
    }

    protected <T extends TaobaoResponse> T execute(@NonNull String URL, @NonNull TaobaoRequest<T> request, Supplier<String> description) {
        return execute(URL, request, response -> {
            try {
                DingUtils.isSuccess(response);
            } catch (ApiException e) {
                final String des = description.get();
                log.error("{}旧版SDK响应失败", des, e);
                throw new DingApiException("%s旧版SDK响应失败", des);
            }
        }, description);
    }

    protected <T extends TaobaoResponse> T execute(@NonNull String URL, @NonNull TaobaoRequest<T> request, Consumer<T> validator, Supplier<String> description) {
        DingTalkClient client = new DefaultDingTalkClient(URL);
        final T response;
        try {
            response = client.execute(request, accessToken());
            validator.accept(response);
        } catch (ApiException e) {
            final String des = description.get();
            log.error("{}执行旧版SDK失败", des, e);
            throw new DingApiException("%s执行旧版SDK失败", des);
        }
        return response;
    }

    protected String accessToken() {
        return DingAccessTokenFactory.access_token(app);
    }
}
