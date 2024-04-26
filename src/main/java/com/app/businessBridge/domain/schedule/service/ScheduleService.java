package com.app.businessBridge.domain.schedule.service;

import com.app.businessBridge.domain.schedule.controller.ApiV1ScheduleController;
import com.app.businessBridge.domain.schedule.entity.Schedule;
import com.app.businessBridge.domain.schedule.repository.ScheduleRepository;
import com.app.businessBridge.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public RsData create(ApiV1ScheduleController.CreateReq req) {

        if(req.startDate.isAfter(req.endDate)){
            return RsData.of("F-1","끝 날짜는 시작 날짜보다 앞일 수 없습니다.");
        }

        Schedule schedule = Schedule.builder()
                .authorName("홍길동")
                .relationName(req.getRelationName())
                .relationId(req.getRelationId())
                .name(req.getName())
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .build();

        scheduleRepository.save(schedule);

        return RsData.of("S-1","스케줄을 생성하였습니다.");
    }
}
