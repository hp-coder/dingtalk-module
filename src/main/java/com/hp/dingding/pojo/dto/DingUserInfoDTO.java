package com.hp.dingding.pojo.dto;

import com.google.gson.annotations.SerializedName;
import com.hp.dingding.constant.DingResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 钉钉企业内用户信息响应信息
 *
 * @Author: HP
 */
@Data
@AllArgsConstructor
public class DingUserInfoDTO extends DingResponse {

    @SerializedName("result")
    private Result result;

    @SerializedName("user_info")
    private UserInfo userInfo;

    @Data
    @AllArgsConstructor
    public static class UserInfo {
        @SerializedName("nick")
        private String nick;
        @SerializedName("unionid")
        private String unionId;
        @SerializedName("dingId")
        private String dingId;
        @SerializedName("openid")
        private String openid;
        @SerializedName("main_org_auth_high_level")
        private Boolean mainOrgAuthHighLevel;
    }

    @AllArgsConstructor
    @Data
    public static class Result {

        @SerializedName("active")
        private Boolean active;
        @SerializedName("admin")
        private Boolean admin;
        @SerializedName("avatar")
        private String avatar;
        @SerializedName("mobile")
        private String mobile;
        @SerializedName("boss")
        private Boolean boss;
        @SerializedName("dept_id_list")
        private List<Long> deptIdList;
        @SerializedName("dept_order_list")
        private List<DeptOrderListDTO> deptOrderList;
        @SerializedName("hide_mobile")
        private Boolean hideMobile;
        @SerializedName("hired_date")
        private Long hiredDate;
        @SerializedName("job_number")
        private String jobNumber;
        @SerializedName("leader_in_dept")
        private List<LeaderInDeptDTO> leaderInDept;
        @SerializedName("name")
        private String name;
        @SerializedName("real_authed")
        private Boolean realAuthed;
        @SerializedName("role_list")
        private List<RoleListDTO> roleList;
        @SerializedName("senior")
        private Boolean senior;
        @SerializedName("title")
        private String title;
        @SerializedName("union_emp_ext")
        private UnionEmpExtDTO unionEmpExt;
        @SerializedName("unionid")
        private String unionid;
        @SerializedName("userid")
        private String userid;

        @NoArgsConstructor
        @Data
        public static class UnionEmpExtDTO {
        }

        @NoArgsConstructor
        @Data
        public static class DeptOrderListDTO {
            @SerializedName("dept_id")
            private Long deptId;
            @SerializedName("order")
            private Long order;
        }

        @NoArgsConstructor
        @Data
        public static class LeaderInDeptDTO {
            @SerializedName("dept_id")
            private Long deptId;
            @SerializedName("leader")
            private Boolean leader;
        }

        @NoArgsConstructor
        @Data
        public static class RoleListDTO {
            @SerializedName("group_name")
            private String groupName;
            @SerializedName("id")
            private String id;
            @SerializedName("name")
            private String name;
        }
    }

}
