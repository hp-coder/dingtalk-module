package com.hp.dingding.component.factory;

import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest;
import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenResponse;
import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenResponseBody;
import com.aliyun.tea.TeaException;
import com.hp.dingding.utils.SpringContextHolderAware;
import com.hp.dingding.component.IDingToken;
import com.hp.dingding.component.application.IDingApp;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@ConditionalOnClass({RedisOperations.class})
@RequiredArgsConstructor
public class DingAccessTokenFactory implements IDingToken {

    private static final String R_KEY = "ding:token:";
    private final StringRedisTemplate redisTemplate;

    public static String accessToken(IDingApp dingApp) {
        return accessToken(dingApp, false);
    }

    public static String accessToken(IDingApp dingApp, boolean forceRefresh) {
        final DingAccessTokenFactory bean = SpringContextHolderAware.getBean(DingAccessTokenFactory.class);
        return bean.accessToken(dingApp.getAppKey(), dingApp.getAppSecret(), forceRefresh).orElse(null);
    }

    @Override
    @SneakyThrows
    public Optional<String> accessToken(String appKey, String appSecret, boolean forceRefresh) {
        Optional<String> token = checkCache(appKey);
        if (token.isPresent()) {
            return token;
        }
        com.aliyun.dingtalkoauth2_1_0.Client client = new com.aliyun.dingtalkoauth2_1_0.Client(this.config());
        GetAccessTokenRequest getAccessTokenRequest = new GetAccessTokenRequest()
                .setAppKey(appKey)
                .setAppSecret(appSecret);
        try {
            final GetAccessTokenResponse tokenResponse = client.getAccessToken(getAccessTokenRequest);
            final GetAccessTokenResponseBody body = tokenResponse.getBody();
            final String accessToken = body.getAccessToken();
            final Long expireIn = body.getExpireIn();
            log.info("DingKey: {}, token: {}, ttl: {}", appKey, accessToken, expireIn);
            log.info("Is force refresh : {}", forceRefresh);
            storeCache(appKey, accessToken, expireIn, forceRefresh);
            return Optional.of(accessToken);
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                log.info("AccessToken Ding异常码: {},Ding异常信息: {}", err.code, err.message);
            }
            throw new RuntimeException(err.message);
        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                log.info("AccessToken Ding异常码: {},Ding异常信息: {}", err.code, err.message);
            }
            throw new RuntimeException(err.message);
        }
    }

    private Optional<String> checkCache(String appKey) {
        String token = null;
        try {
            token = redisTemplate.opsForValue().get(getCacheKey(appKey));
        } catch (Exception e) {
            log.error("缓存异常：获取Ding token失败, errMsg: {}, errCause: {}", e.getMessage(), e.getCause());
        }
        return Optional.ofNullable(token);
    }

    private void storeCache(String appKey, String accessToken, Long expireIn, boolean forceRefresh) {
        final String cacheKey = getCacheKey(appKey);
        if (StringUtils.isEmpty(accessToken)) {
            return;
        }
        if (forceRefresh) {
            redisTemplate.opsForValue().set(cacheKey, accessToken, expireIn - 100L, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().setIfAbsent(cacheKey, accessToken, expireIn - 100L, TimeUnit.SECONDS);
        }
    }

    private String getCacheKey(String appKey) {
        return R_KEY + appKey;
    }

}
