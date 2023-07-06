package com.hp.dingtalk.component;

import com.aliyun.teaopenapi.models.Config;
import com.hp.common.base.annotations.MethodDesc;

/**
 * @author hp
 */
public interface IDingNewApi extends IDingApi {
    SDK.Version NEW = SDK.Version.NEW;

    @MethodDesc("新版SDK的client配置")
    default Config clientConfig() {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return config;
    }
}
