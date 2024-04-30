package com.app.businessBridge.domain.department.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
public class DepartmentRequest {

    @Getter
    @Setter
    public static class CreateRequest {
        @NotNull
        private Integer departmentCode;
        @NotBlank
        private String departmentName;
    }

    @Getter
    @Setter
    public static class UpdateRequest {
        @NotNull
        private Long id;
        @NotNull
        private Integer departmentCode;
        @NotBlank
        private String departmentName;
    }
}
