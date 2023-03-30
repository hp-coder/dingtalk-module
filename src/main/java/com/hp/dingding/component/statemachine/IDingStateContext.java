package com.hp.dingding.component.statemachine;


import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * @author hp 2023/3/28
 */
public interface IDingStateContext<T> {

    /**
     * 状态机唯一id
     *
     * @return 状态机唯一id
     */
    String getSerialNo();

    /**
     * 状态机唯一id
     *
     * @param serialNo 状态机唯一id
     */
    void setSerialNo(String serialNo);

    /**
     * 业务数据
     *
     * @return 业务数据
     */
    T bizData();

    /**
     * 业务是否完成，用于完成后清除缓存
     */
    void setComplete(boolean complete);

    /**
     * 业务是否完成，用于完成后清除缓存
     *
     * @return 是否完成
     */
    boolean isComplete();

    /**
     * spring上下文方便关联
     *
     * @param applicationContext spring上下文
     */
    void setApplicationContext(ApplicationContext applicationContext);

    /**
     * spring上下文
     *
     * @return spring上下文
     */
    ApplicationContext getApplicationContext();

    <DATA> Map<Class<? extends IDingState>, DATA> getRequestCache();
}
