package com.app.businessBridge.domain.schedule.controller;

import com.app.businessBridge.domain.schedule.entity.Schedule;
import com.app.businessBridge.domain.schedule.request.ScheduleRequest;
import com.app.businessBridge.domain.schedule.response.ScheduleResponse;
import com.app.businessBridge.domain.schedule.service.ScheduleService;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ApiV1ScheduleController {

    private final ScheduleService scheduleService;
    private final AlarmWebSocketController alarmWebSocketController;

    // 생성
    @PostMapping("")
    public RsData create(@Valid @RequestBody  ScheduleRequest.CreateReq req, BindingResult br){

        if(br.hasErrors()){
            return RsData.of(RsCode.F_06,"유효하지 않은 요청입니다.");
        }
        RsData result = scheduleService.create(req);

        if(result.getIsSuccess()){
            alarmWebSocketController.sendMessageToTopic(req.getRelationName(), req.getRelationId(), "일정이 등록되었습니다");
        }
        return result;
    }

    // 단건 조회
    @GetMapping("/getSchedule/{id}")
    public RsData<ScheduleResponse.getSchedule> getSchedule(@PathVariable(name = "id") Long id){

        return scheduleService.getSchedule(id)
                .map(schedule -> RsData.of(RsCode.S_05,"스케줄 조회 성공",new ScheduleResponse.getSchedule(schedule)))
                .orElse(RsData.of(RsCode.F_04,"존재하지 않는 스케줄 입니다."));
    }

    // 다건 조회
    @GetMapping("/getSchedules/{relationName}/{relationId}")
    public RsData<ScheduleResponse.getSchedules> getSchedules(@PathVariable(name = "relationName") String relationName, @PathVariable(name = "relationId") Long relationId){

        List<Schedule> schedules = scheduleService.getSchedulesByRelationNameAndRelationId(relationName, relationId);

        return RsData.of(RsCode.S_05,"스케줄 조회 성공",new ScheduleResponse.getSchedules(schedules));
    }

    // 수정
    @PatchMapping("")
    public RsData update(@Valid @RequestBody ScheduleRequest.UpdateReq req, BindingResult br){

        if(br.hasErrors()){
            return RsData.of(RsCode.F_06,"유효하지 않은 요청입니다.");
        }

        RsData<Schedule> result = scheduleService.update(req);

        if(result.getIsSuccess()){
            alarmWebSocketController.sendMessageToTopic(result.getData().getRelationName(), result.getData().getRelationId(), "일정이 수정되었습니다");
        }

        return result;
    }

    // 삭제
    @DeleteMapping("")
    public RsData delete(@Valid @RequestBody ScheduleRequest.DeleteReq req, BindingResult br){

        if(br.hasErrors()){
            return RsData.of(RsCode.F_06,"유효하지 않은 요청입니다.");
        }

        RsData result = scheduleService.delete(req);

        if(result.getIsSuccess()){
            alarmWebSocketController.sendMessageToTopic(req.getRelationName(), req.getRelationId(), "일정이 삭제 되었습니다.");
        }

        return result;
    }
}
