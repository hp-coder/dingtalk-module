package com.hp.dingtalk.listener;

import com.google.common.base.Stopwatch;
import com.hp.dingtalk.constant.DingMiniH5EventType;
import com.hp.dingtalk.pojo.callback.event.DingMiniH5CallbackEvents;
import com.hp.dingtalk.service.IDingMiniH5EventCallbackHandler;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author hp
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "dingtalk.miniH5.event.listener", name = "enabled", havingValue = "true")
public class DefaultDingMiniH5EventListener {

    private final List<IDingMiniH5EventCallbackHandler> miniH5EventCallbackHandlers;
    private final ExecutorService executorService;

    @EventListener
    public void handleMiniH5EventCallback(DingMiniH5CallbackEvents.DingMiniH5EventDecryptedPayload payload) {
        final DingMiniH5EventType eventType = payload.getEventType();
        log.info("使用插件自带默认监听器处理钉钉微应用事件回调:{}({})", eventType.getCode(), eventType.getName());
        if (CollectionUtils.isEmpty(miniH5EventCallbackHandlers)) {
            log.error("容器中没有找到任何IDingMiniH5EventCallbackHandler实例");
            return;
        }
        miniH5EventCallbackHandlers
                .stream()
                .filter(handler-> handler.support(payload))
                .collect(Collectors.groupingBy(h -> h.order()))
                .entrySet()
                .stream()
                .map(entry -> new ExecutorWithLevel(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(ExecutorWithLevel::getOrder))
                .forEach(executorWithLevel -> {
                    try {
                        final List<Task> tasks = executorWithLevel.handlers.stream()
                                .map(i -> new Task(payload, i))
                                .collect(Collectors.toList());
                        if (log.isDebugEnabled()) {
                            final Stopwatch stopwatch = Stopwatch.createStarted();
                            executorService.invokeAll(tasks);
                            stopwatch.stop();
                            log.debug(
                                    "钉钉微应用事件:{}({}) 处理器执行耗时:{}ms",
                                    eventType.getCode(),
                                    eventType.getName(),
                                    stopwatch.elapsed(TimeUnit.MILLISECONDS)
                            );
                        } else {
                            executorService.invokeAll(tasks);
                        }
                    } catch (InterruptedException e) {
                        log.error("执行对钉钉微应用事件:{}({})处理失败", eventType.getCode(), eventType.getName(), e);
                    }
                });
    }

    @Value
    class ExecutorWithLevel {
        Integer order;
        List<IDingMiniH5EventCallbackHandler> handlers;
    }

    class Task implements Callable<Void> {
        private final DingMiniH5CallbackEvents.DingMiniH5EventDecryptedPayload payload;
        private final IDingMiniH5EventCallbackHandler handler;

        public Task(DingMiniH5CallbackEvents.DingMiniH5EventDecryptedPayload payload, IDingMiniH5EventCallbackHandler handler) {
            this.payload = payload;
            this.handler = handler;
        }

        @Override
        public Void call() {
            final boolean process = handler.process(payload);
            log.debug("钉钉微应用事件处理器处理结果:{}", process);
            return null;
        }
    }
}
