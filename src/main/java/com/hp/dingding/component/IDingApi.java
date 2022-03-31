package com.hp.dingding.component;

import com.aliyun.teaopenapi.models.Config;

/**
 * 钉钉api顶层接口
 *
 * @Author: HP
 */
public interface IDingApi {

    /**
     * please check this out before calling any api
     */
    String API_INVOCATION_FREQUENCY_LIMIT = "https://open.dingtalk.com/document/isvapp-server/invocation-frequency-limit-1";

    /**
     * 新版api client 配置
     *
     * @return
     */
    default Config config() {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return config;
    }
}
