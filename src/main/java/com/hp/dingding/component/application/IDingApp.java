package com.hp.dingding.component.application;

import com.hp.dingding.component.factory.DingAppFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * 钉钉应用
 *
 * @Author: HP
 */
public interface IDingApp extends InitializingBean {

    String getAppName();

    String getAppKey();

    String getAppSecret();

    Long getAppId();

    @Override
    default void afterPropertiesSet() {
        DingAppFactory.setAppCache(this);
    }
}
