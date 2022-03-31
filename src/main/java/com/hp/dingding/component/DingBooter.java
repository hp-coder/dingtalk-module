package com.hp.dingding.component;

import com.hp.dingding.component.callback.IDingCallBack;
import com.hp.dingding.service.api.IDingInteractiveMessageHandler;
import com.taobao.api.ApiException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;

/**
 * 启动加载齐
 * @Description: 启动时加载注册互动卡片的回调地址
 * @Author: HP
 */
@Slf4j
@ConditionalOnClass({RedisOperations.class})
@Component
public class DingBooter implements ApplicationRunner {

    @SneakyThrows
    @Override
    public void run(ApplicationArguments args) {
        IDingCallBack.registry.forEach(callBack -> callBack.getDingApps().forEach(app->{
            try {
                IDingInteractiveMessageHandler.registerCallBackUrl(app,callBack);
            } catch (ApiException e) {
                log.error("注册回调地址失败：应用：{}, 回调地址：{}, 路由键：{}",app.getAppName(),callBack.getCallbackUrl(),callBack.getCallbackUrlKey());
                log.error("注册回调地址失败：异常：{}",e.getCause(),e);
            }
        }));
    }
}
