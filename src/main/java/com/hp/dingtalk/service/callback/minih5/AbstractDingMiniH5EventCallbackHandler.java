package com.hp.dingtalk.service.callback.minih5;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.hp.dingtalk.constant.DingMiniH5EventType;
import com.hp.dingtalk.pojo.GsonBuilderVisitor;
import com.hp.dingtalk.pojo.callback.eventbody.IDingMiniH5EventBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import static com.hp.dingtalk.pojo.callback.event.DingMiniH5CallbackEvents.DingMiniH5EventDecryptedPayload;

/**
 * @author hp
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractDingMiniH5EventCallbackHandler<T extends IDingMiniH5EventBody> implements IDingMiniH5EventCallbackHandler {

    @Nullable
    protected final GsonBuilderVisitor gsonBuilderVisitor;
    protected T body;

    @Override
    public boolean support(DingMiniH5EventDecryptedPayload payload) {
        final boolean supportEvent = supportEvent(payload.getEventType());
        final Class<T> clazz = getFirstTypeArguments();
        body = payload.getPayload(clazz, gsonBuilderVisitor);
        Preconditions.checkNotNull(body, "IDingMiniH5EventBody: the body is null");
        final boolean supportOther = supportOther(body);
        return supportEvent && supportOther;
    }

    protected Class<T> getFirstTypeArguments() {
        final ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        final Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        final Class<T> clazz = (Class<T>) actualTypeArguments[0];
        return clazz;
    }

    @Override
    public boolean process(DingMiniH5EventDecryptedPayload payload) {
        final DingMiniH5EventType eventType = payload.getEventType();
        log.info("客户端{}接收到钉钉事件:{}({})", this.getClass().getSimpleName(), eventType.getCode(), eventType.getName());
        if (!support(payload)) {
            log.error("不支持处理该类型钉钉事件:{}({})", eventType.getCode(), eventType.getName());
            return false;
        }
        try {
            if (log.isDebugEnabled()) {
                final Stopwatch stopwatch = Stopwatch.createStarted();
                final boolean bool = doProcess(payload);
                stopwatch.stop();
                log.debug("客户端业务处理钉钉事件:{}({})耗时:{}ms", eventType.getCode(), eventType.getName(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
                return bool;
            } else {
                return doProcess(payload);
            }
        } catch (Exception e) {
            log.error("处理钉钉事件:{}({})失败 \n", eventType.getCode(), eventType.getName(), e);
        }
        return false;
    }

    protected abstract boolean supportEvent(DingMiniH5EventType eventType);

    protected abstract boolean supportOther(T body);

    protected abstract boolean doProcess(DingMiniH5EventDecryptedPayload payload);
}
