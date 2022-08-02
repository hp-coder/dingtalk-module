package com.hp.dingding.component.factory;

import com.google.gson.Gson;
import com.hp.dingding.component.application.IDingApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 钉钉应用工厂
 * <p>
 * 既然已经使用了spring框架，直接使用其容器完成单例效果
 * <p>
 * 实现IDingApp接口并注册到spring容器中的类将被自动添加到工厂缓存中
 *
 * @Author: HP
 */
@Component
@Slf4j
public class DingAppFactory implements InitializingBean {


    private static final Set<IDingApp> APP_CACHE = new HashSet<>(16);
    private static Map<String, IDingApp> KEY_CACHE;
    private static Map<Long, IDingApp> ID_CACHE;

    private DingAppFactory() {
    }

    public static void setAppCache(IDingApp app) {
        Assert.notNull(app, "应用不能为空");
        APP_CACHE.add(app);
    }

    public static <T extends IDingApp> T app(Class<T> clazz) {
        return CollectionUtils.findValueOfType(APP_CACHE, clazz);
    }

    public static <T extends IDingApp> T app(Long appId) {
        return (T) ID_CACHE.get(appId);
    }

    public static <T extends IDingApp> T app(String key) {
        return (T) KEY_CACHE.get(key);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("DingAppFactory APP_CACHE SIZE: ", DingAppFactory.APP_CACHE.size());
        KEY_CACHE = APP_CACHE.stream().collect(Collectors.toMap(IDingApp::getAppKey, v -> v));
        ID_CACHE = APP_CACHE.stream().collect(Collectors.toMap(IDingApp::getAppId, v -> v));
    }
}
