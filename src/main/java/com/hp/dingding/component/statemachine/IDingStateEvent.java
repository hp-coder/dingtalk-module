package com.hp.dingding.component.statemachine;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hp 2023/3/29
 */
public interface IDingStateEvent {

    @AllArgsConstructor
    @Getter
    class CompleteEvent {
       private IDingStateContext<?> context;
    }
}
