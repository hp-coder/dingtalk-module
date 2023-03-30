package com.hp.dingding.component.statemachine;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


/**
 * @author hp 2023/3/28
 */
@Slf4j
public abstract class AbstractDingStateMachine<DATA> implements IDingStateMachine<DATA> {

    protected IDingState<DATA> current;
    @Getter
    protected IDingStateContext<?> context;

    /**
     * 校验失败，无法执行entry
     *
     * @param newState 新状态
     * @param input    输入数据
     * @param reason   检测失败的原因
     */
    protected abstract void cantProceedEntry(IDingState<DATA> newState, DATA input, String reason);

    /**
     * 校验失败，无法执行complete
     *
     * @param input  输入数据
     * @param reason 检测失败的原因
     */
    protected abstract void cantProceedComplete(DATA input, String reason);

    public AbstractDingStateMachine(IDingState<DATA> initState, DATA input) {
        this.current = initState;
        this.context = initState.getContext();
        this.stateChange(initState, input);
    }

    @Override
    public IDingState<DATA> currentState() {
        return this.current;
    }

    @Override
    public void computeInput(DATA input) {
        if (this.current == null) {
            return;
        }
        this.context.getRequestCache().put(this.current.getClass(), input);
        final IDingState.Predicate predicate = this.current.proceedComplete().apply(input);
        if (predicate.isProceed()) {
            try {
                this.current.onComplete(input);
            } catch (Exception e) {
                log.error("currentState:{} onComplete error", current.getClass(), e);
                cantProceedComplete(input, e.getMessage());
                return;
            }
            final IDingState<DATA> nextState = nextState(input);
            stateChange(nextState, input);
        } else {
            cantProceedComplete(input, predicate.getReason());
        }
    }

    @Override
    public void stateChange(IDingState<DATA> newState, DATA input) {
        if (newState == null) {
            return;
        }
        this.context.getRequestCache().put(newState.getClass(), input);
        final IDingState.Predicate predicate = newState.proceedEntry().apply(input);
        if (predicate.isProceed()) {
            try {
                newState.onEntry(input);
            } catch (Exception e) {
                log.error("newState:{} onEntry error", newState.getClass(), e);
                cantProceedEntry(newState, input, e.getMessage());
                return;
            }
            this.current = newState;
        } else {
            cantProceedEntry(newState, input, predicate.getReason());
        }
    }

    @Override
    public void exit(IDingState<DATA> exitState, DATA input) {
        try {
            exitState.onEntry(input);
        } catch (Exception e) {
            log.error("exitState:{} onEntry err", exitState.getClass(), e);
        }
        try {
            exitState.onComplete(input);
        } catch (Exception e) {
            log.error("exitState:{} onComplete err", exitState.getClass(), e);
        }
        this.current = null;
        this.context = null;
    }
}
