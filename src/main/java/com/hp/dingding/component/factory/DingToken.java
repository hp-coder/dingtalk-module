package com.hp.dingding.component.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @Author: HP
 */
@AllArgsConstructor
@Getter
@Setter
@Slf4j
public class DingToken {

    private String accessToken;
    private LocalDateTime expiredAt;

    public boolean isExpired() {
        log.info("DING TOKEN EXPIRES AT: {}", getExpiredAt());
        return this.expiredAt.compareTo(LocalDateTime.now()) <= 0;
    }
}
