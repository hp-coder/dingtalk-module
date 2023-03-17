package com.hp.dingtalk.test;

import lombok.Getter;

/**
 * @author hp 2023/3/17
 */
@Getter
public class TestBot extends QosBot {
    public TestBot() {
        super("appName", 11L, "appKey", "appSecret");
    }
}
