package com.hp.dingtalk.service;

import com.aliyun.tea.TeaException;
import com.aliyun.tea.TeaModel;
import com.aliyun.teaopenapi.Client;
import com.aliyun.teaopenapi.models.Config;
import com.google.gson.GsonBuilder;
import com.hp.dingtalk.component.IDingNewApi;
import com.hp.dingtalk.component.application.IDingApp;
import com.hp.dingtalk.component.exception.DingApiException;
import com.hp.dingtalk.component.factory.token.DingAccessTokenFactory;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author hp
 */
@Slf4j
public abstract class AbstractDingNewApi implements IDingNewApi {

    protected final IDingApp app;
    protected final Config config;

    public AbstractDingNewApi(IDingApp app) {
        this.app = app;
        this.config = clientConfig();
    }

    public <CLIENT extends Client, RESPONSE extends TeaModel> RESPONSE execute(Class<CLIENT> clazz, Function<CLIENT, RESPONSE> function, Supplier<String> description) {
        try {
            final Constructor<CLIENT> declaredConstructor = clazz.getDeclaredConstructor(Config.class);
            declaredConstructor.setAccessible(true);
            final CLIENT client = declaredConstructor.newInstance(config);
            final RESPONSE response = function.apply(client);
            log.debug("{}响应:{}", description.get(), new GsonBuilder().create().toJson(response));
            return response;
        } catch (TeaException err) {
            final String des = description.get();
            log.error("{}执行新版SDK失败", des, err);
            throw new DingApiException(String.format("%s执行新版SDK失败", des), err);
        } catch (Exception e) {
            final String des = description.get();
            log.error("{}执行新版SDK异常", des, e);
            throw new DingApiException(String.format("%s执行新版SDK异常", des), e);
        }
    }

    protected String accessToken() {
        return DingAccessTokenFactory.accessToken(app);
    }
}
