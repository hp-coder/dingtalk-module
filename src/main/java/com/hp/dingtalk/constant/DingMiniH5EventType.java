package com.hp.dingtalk.constant;

import com.hp.common.base.annotations.FieldDesc;
import com.hp.common.base.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 事件订阅总览
 *
 * @author hp
 * @See https://open.dingtalk.com/document/orgapp/event-list
 */
@Getter
@AllArgsConstructor
public enum DingMiniH5EventType implements BaseEnum<DingMiniH5EventType, String> {
    /***/
    CHECK_URL("check_url", "检查配置URL事件"),

    TODO_CREATE("todo_task_create", "创建待办任务事件"),

    @FieldDesc("审批任务回调，是针对审批任务状态的推送; 有任务开始，任务结束和任务转交(转到下一审批人)共三个事件，可获取当前审批人的userId(针对审批流的各个审批人节点进行触发推送的场景)。")
    AUDIT_TASK_CHANGE("bpms_task_change", "审批任务回调"),

    @FieldDesc("针对审批实例状态的推送；有审批实例开始和审批实例结束两个事件，能得到发起审批人的userId(对整体审批流的触发推送的场景)")
    AUDIT_INSTANCE_CHANGE("bpms_instance_change", "审批实例回调"),

    ;
    private final String code;
    private final String name;

    public static Optional<DingMiniH5EventType> of(String code) {
        return Optional.ofNullable(BaseEnum.parseByCode(DingMiniH5EventType.class, code));
    }
}
