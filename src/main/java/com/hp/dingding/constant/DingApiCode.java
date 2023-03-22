package com.hp.dingding.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hp 2023/3/21
 */
public interface DingApiCode{

    @Getter
    @AllArgsConstructor
    enum BaseResponse{
        // 基础接口相关
        IP_NOT_IN_WHITE_LIST(88L, "IP不在白名单");
        private final Long code;
        private final String name;
    }

    @Getter
    @AllArgsConstructor
    enum UserResponse{
        // 用户管理接口相关
        NOT_FOUND(60121L, "找不到该用户");
        private final Long code;
        private final String name;
    }
}
