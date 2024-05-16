package com.app.businessBridge.domain.division.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
public class DivisionRequest {

    @Getter
    @Setter
    public static class CreateRequest {
        @NotNull
        private Integer code;
        @NotBlank
        private String name;
    }

    @Getter
    @Setter
    public static class UpdateRequest {
        @NotNull
        private Long id;
        @NotNull
        private Integer code;
        @NotBlank
        private String name;
    }

    @Getter
    @Setter
    public static class DeleteRequest {
        @NotNull
        private Long id;
    }
}