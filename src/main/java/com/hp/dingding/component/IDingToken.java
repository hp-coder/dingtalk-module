package com.hp.dingding.component;


import java.util.Optional;

/**
 * 钉钉accessToken顶层接口
 *
 * @Author: HP
 */
public interface IDingToken extends IDingApi {

    Optional<String> accessToken(String appKey, String appSecret, boolean forceRefresh);
}
