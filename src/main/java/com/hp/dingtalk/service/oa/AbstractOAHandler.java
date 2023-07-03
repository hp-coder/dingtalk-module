package com.hp.dingtalk.service.oa;

import com.hp.dingtalk.component.application.IDingMiniH5;
import com.hp.dingtalk.service.IDingOAHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hp
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractOAHandler implements IDingOAHandler {

    protected final IDingMiniH5 app;
}
