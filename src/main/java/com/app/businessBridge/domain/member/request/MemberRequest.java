package com.app.businessBridge.domain.member.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MemberRequest {

    @Getter
    @Setter
    public static class CreateRequset {
        @NotNull
        private Long departmentId;
        @NotNull
        private Long gradeId;
        @NotBlank
        private String username;
        @NotNull
        private Integer memberNumber;
        @NotBlank
        private String name;
        @NotBlank
        private String password;
        @NotBlank
        private String email;
    }

    @Getter
    @Setter
    public static class UpdateRequest {
        @NotNull
        private Long id;
        @NotNull
        private Long departmentId;
        @NotNull
        private Long gradeId;
        @NotBlank
        private String username;
        @NotNull
        private Integer memberNumber;
        @NotBlank
        private String name;
        @NotBlank
        private String password;
        @NotBlank
        private String email;
    }

    @Getter
    @Setter
    public static class DeleteRequest {
        @NotNull
        private Long id;
    }
}
