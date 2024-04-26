package com.app.businessBridge.domain.schedule.controller;

import com.app.businessBridge.domain.schedule.service.ScheduleService;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
public class ApiV1ScheduleController {

    private final ScheduleService scheduleService;

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

    @PostMapping("")
    public RsData create(@Valid @RequestBody CreateReq req, BindingResult br){

        if(br.hasErrors()){
            return RsData.of("F-1","유효하지 않은 입력 입니다.");
        }

        return scheduleService.create(req);
    }

}
