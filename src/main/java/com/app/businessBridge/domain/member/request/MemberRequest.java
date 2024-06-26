package com.app.businessBridge.domain.member.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MemberRequest {

    @Getter
    @Setter
    public static class CreateRequest {
        @NotNull
        private Integer divisionCode;
        @NotNull
        private Integer departmentCode;
        @NotNull
        private Integer gradeCode;
        @NotBlank
        private String username;
        @NotBlank
        private String password;
        @NotNull
        private Integer memberNumber;
        @NotBlank
        private String name;
    }

    @Getter
    @Setter
    public static class UpdateRequest {
        @NotNull
        private Long id;
        @NotNull
        private Integer divisionCode;
        @NotNull
        private Integer departmentCode;
        @NotNull
        private Integer gradeCode;
        @NotBlank
        private String username;
        @NotBlank
        private String password;
        @NotBlank
        private String email;
        @NotNull
        private Integer memberNumber;
        @NotBlank
        private String name;
        @NotBlank
        private String assignedTask;
        @NotBlank
        private String extensionNumber;
        @NotBlank
        String phoneNumber;
        @NotBlank
        String statusMessage;
        @NotBlank
        char sex;
        @NotBlank
        String age;
    }

    @Getter
    @Setter
    public static class DeleteRequest {
        @NotNull
        private Long id;
    }

    @Getter
    @Setter
    public static class LoginRequest {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @Getter
    @Setter
    public static class SearchRequest {
        @NotBlank
        private String keyword;
    }
}
