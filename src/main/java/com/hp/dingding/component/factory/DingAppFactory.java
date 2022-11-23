package com.hp.dingding.component.factory;

import com.hp.dingding.component.application.IDingApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 钉钉应用工厂
 *
 * @Author: HP
 */
@Slf4j
public class DingAppFactory {

    private static final Map<String, IDingApp> KEY_CACHE = new HashMap<>(4);
    private static final Map<Long, IDingApp> ID_CACHE = new HashMap<>(4);

    private DingAppFactory() {
    }

    public static void setAppCache(IDingApp app) {
        Assert.notNull(app, "应用不能为空");
        KEY_CACHE.put(app.getAppKey(), app);
        ID_CACHE.put(app.getAppId(), app);
    }

    public static <T extends IDingApp> T app(Class<T> clazz) {
        return CollectionUtils.findValueOfType(KEY_CACHE.values(), clazz);
    }

    public static <T extends IDingApp> T app(Long appId) {
        return (T) ID_CACHE.get(appId);
    }

    public static <T extends IDingApp> T app(String key) {
        return (T) KEY_CACHE.get(key);
    }

}
