package com.app.businessBridge.domain.schedule.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
public class ScheduleRequest {

    @Getter
    @Setter
    public static class CreateReq {
        @NotBlank
        public String relationName;
        @NotNull
        public Long relationId;
        @NotBlank
        public String name;
        @NotNull
        public LocalDate startDate;
        @NotNull
        public LocalDate endDate;
    }

    @Getter
    @Setter
    public static class UpdateReq {
        @NotNull
        public Long id;
        @NotBlank
        public String name;
        @NotNull
        public LocalDate startDate;
        @NotNull
        public LocalDate endDate;
    }

    @Getter
    @Setter
    public static class DeleteReq {
        @NotNull
        public Long id;
        @NotBlank
        public String relationName;
        @NotNull
        public Long relationId;
    }
}
