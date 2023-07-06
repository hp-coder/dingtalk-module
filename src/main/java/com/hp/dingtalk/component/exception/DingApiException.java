package com.hp.dingtalk.component.exception;

/**
 * 方便使用方统一处理异常
 *
 * @author hp 2023/3/21
 */
public class DingApiException extends RuntimeException {
    public DingApiException(String message) {
        super(message);
    }

    public DingApiException(String format, Object... args) {
        super(String.format(format, args));
    }

    public DingApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public DingApiException(Throwable cause) {
        super(cause);
    }
}
