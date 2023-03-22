package com.hp.dingding.component.factory;

import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest;
import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenResponse;
import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenResponseBody;
import com.aliyun.tea.TeaException;
import com.hp.dingding.component.IDingToken;
import com.hp.dingding.component.application.IDingApp;
import com.hp.dingding.component.exception.DingApiException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hp
 */
@Slf4j
public class DingAccessTokenFactory implements IDingToken {

    private DingAccessTokenFactory() {
    }

    private static class SingletonHolder {
        private static final DingAccessTokenFactory INSTANCE = new DingAccessTokenFactory();
    }

    private static final ConcurrentHashMap<String, DingToken> TOKEN_CACHE = new ConcurrentHashMap<>(16);

    public static String accessToken(IDingApp dingApp) {
        return accessToken(dingApp, false);
    }

    public static String accessToken(IDingApp dingApp, boolean forceRefresh) {
        log.info("GETTING ACCESS TOKEN WITH：{}",dingApp.getAppName());
        final DingAccessTokenFactory bean = SingletonHolder.INSTANCE;
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
            log.info("appKey:{},token:{},ttl:{}", appKey, accessToken, expireIn);
            log.info("forceRefresh:{}", forceRefresh);
            storeCache(appKey, accessToken, expireIn, forceRefresh);
            return Optional.of(accessToken);
        } catch (TeaException err) {
            if (StringUtils.hasText(err.code) && StringUtils.hasText(err.message)) {
                log.error("钉钉AccessToken,code:{},me:{}", err.code, err.message);
            }
            throw new DingApiException(err.message);
        } catch (Exception e) {
            e.printStackTrace();
            TeaException err = new TeaException(e.getMessage(), e);
            if (StringUtils.hasText(err.code) && StringUtils.hasText(err.message)) {
                log.error("钉钉AccessToken Ding异常码:{},Ding异常信息:{}", err.code, err.message);
            }
            throw new DingApiException(err.message);
        }
    }

    private Optional<String> checkCache(String appKey) {
        final DingToken token = TOKEN_CACHE.get(appKey);
        if (token == null) {
            return Optional.empty();
        }
        if (token.isExpired()) {
            TOKEN_CACHE.remove(appKey);
            return Optional.empty();
        }
        return Optional.ofNullable(token.getAccessToken());
    }

    private void storeCache(String appKey, String accessToken, Long expireIn, boolean forceRefresh) {
        if (!StringUtils.hasText(accessToken)) {
            log.debug("ding accessToken is empty");
            return;
        }
        Assert.isTrue(expireIn != null && expireIn > 600L, "DING TOKEN EXPIRED");
        if (forceRefresh) {
            TOKEN_CACHE.put(appKey, new DingToken(accessToken, LocalDateTime.now().plusSeconds(expireIn - 600)));
        } else {
            TOKEN_CACHE.putIfAbsent(appKey, new DingToken(accessToken, LocalDateTime.now().plusSeconds(expireIn - 600)));
        }
    }
}
