package com.hp.dingtalk.pojo.oa;

import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author hp
 */
@Data
@AllArgsConstructor
public class CreateProcessInstanceRequest {

    private String processCode;
    private String userId;
    private Long deptId;
    private List<String> approvers;
    private List<OapiProcessinstanceCreateRequest.ProcessInstanceApproverVo> approversV2;
    private List<String> ccList;
    private CCPosition ccPosition;
    private List<OapiProcessinstanceCreateRequest.FormComponentValueVo> formComponentValueVos;

    private CreateProcessInstanceRequest() {
    }

    @Data
    public static class Builder {
        private String processCode;
        private String userId;
        private Long deptId;
        private List<String> approvers;
        private List<OapiProcessinstanceCreateRequest.ProcessInstanceApproverVo> approversV2;
        private List<String> ccList;
        private CCPosition ccPosition;
        private List<OapiProcessinstanceCreateRequest.FormComponentValueVo> formComponentValueVos;

        public Builder(
                String processCode,
                String userId,
                Long deptId,
                List<OapiProcessinstanceCreateRequest.FormComponentValueVo> formComponentValueVos
        ) {
            Assert.notNull(processCode,"审批模版编号不能为空");
            Assert.notNull(userId,"发起人id不能为空");
            Assert.notNull(deptId,"发起人部门id不能为空");
            Assert.notNull(formComponentValueVos,"表单项不能为空");
            this.processCode = processCode;
            this.userId = userId;
            this.deptId = deptId;
            this.formComponentValueVos = formComponentValueVos;
        }

        public Builder approvers(List<String> approvers) {
            return approvers(approvers, null, null);
        }

        public Builder approversV2(List<OapiProcessinstanceCreateRequest.ProcessInstanceApproverVo> approversV2) {
            return approversV2(approversV2, null, null);
        }

        public Builder approvers(
                List<String> approvers,
                List<String> ccList
        ) {
            this.approvers = approvers;
            this.ccList = ccList;
            return this;
        }

        public Builder approvers(
                List<String> approvers,
                List<String> ccList,
                CCPosition ccPosition
        ) {
            this.approvers = approvers;
            this.ccList = ccList;
            this.ccPosition = ccPosition;
            return this;
        }

        public Builder approversV2(
                List<OapiProcessinstanceCreateRequest.ProcessInstanceApproverVo> approversV2,
                List<String> ccList
        ) {
            this.approversV2 = approversV2;
            this.ccList = ccList;
            return this;
        }

        public Builder approversV2(
                List<OapiProcessinstanceCreateRequest.ProcessInstanceApproverVo> approversV2,
                List<String> ccList,
                CCPosition ccPosition
        ) {
            this.approversV2 = approversV2;
            this.ccList = ccList;
            this.ccPosition = ccPosition;
            return this;
        }

        public CreateProcessInstanceRequest build() {
            return new CreateProcessInstanceRequest(
                    processCode,
                    userId,
                    deptId,
                    approvers,
                    approversV2,
                    ccList,
                    ccPosition,
                    formComponentValueVos
            );
        }
    }

    public OapiProcessinstanceCreateRequest request() {
        OapiProcessinstanceCreateRequest req = new OapiProcessinstanceCreateRequest();
        req.setProcessCode(processCode);
        req.setOriginatorUserId(userId);
        req.setDeptId(deptId);
        req.setFormComponentValues(formComponentValueVos);
        if (!CollectionUtils.isEmpty(approvers)) {
            req.setApprovers(String.join(",", approvers));
        }
        if (!CollectionUtils.isEmpty(approversV2)) {
            req.setApproversV2(approversV2);
        }
        if (!CollectionUtils.isEmpty(ccList)) {
            req.setCcList(String.join(",", ccList));
        }
        if (Objects.nonNull(ccPosition)) {
            req.setCcPosition(ccPosition.name());
        }
        return req;
    }

    public enum CCPosition {
        START,
        FINISH,
        START_FINISH
    }
}
