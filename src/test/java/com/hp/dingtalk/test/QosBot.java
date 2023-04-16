package com.hp.dingtalk.test;

import com.hp.dingtalk.component.application.IDingBot;
import lombok.Getter;
import lombok.ToString;

/**
 * @author hp 2023/3/17
 */
@Getter
@ToString
public class QosBot implements IDingBot {
    private final String appName;
    private final Long appId;
    private final String appKey;
    private final String appSecret;

    public QosBot(String appName, Long appId, String appKey, String appSecret) {
        this.appName = appName;
        this.appId = appId;
        this.appKey = appKey;
        this.appSecret = appSecret;
    }
}
