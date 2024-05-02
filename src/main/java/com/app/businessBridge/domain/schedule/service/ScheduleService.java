package com.app.businessBridge.domain.schedule.service;

import com.app.businessBridge.domain.schedule.entity.Schedule;
import com.app.businessBridge.domain.schedule.repository.ScheduleRepository;
import com.app.businessBridge.domain.schedule.request.ScheduleRequest;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // 모든 메서드에 대해 권한 검사 해야함

    @Transactional
    public RsData create(ScheduleRequest.CreateReq req) {

        if(timeValidation(req.getStartDate(), req.getEndDate())== -1){
            return RsData.of(RsCode.F_06,"끝시간은 시작시간보다 앞일 수 없습니다.");
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

        return RsData.of(RsCode.S_02,"스케줄을 생성하였습니다.");
    }

    @Transactional
    public Optional<Schedule> getSchedule(Long id) {
        return scheduleRepository.findById(id);
    }

    @Transactional
    public List<Schedule> getSchedulesByRelationNameAndRelationId(String relationName, Long relationId) {
        return scheduleRepository.findByRelationNameAndRelationId(relationName,relationId);
    }

    @Transactional
    public RsData update(ScheduleRequest.UpdateReq req) {

        Optional<Schedule> schedule = scheduleRepository.findById(req.getId());

        if(schedule.isEmpty()){
            return RsData.of(RsCode.F_04,"존재하지 않는 스케줄 입니다.");
        }

        if(timeValidation(req.getStartDate(), req.getEndDate()) == -1){
            return RsData.of(RsCode.F_06,"끝시간은 시작시간보다 앞일 수 없습니다.");
        }

        Schedule tempSchedule = schedule.get().toBuilder()
                .authorName("홍길동")
                .name(req.getName())
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .build();

        scheduleRepository.save(tempSchedule);

        return RsData.of(RsCode.S_03,"스케줄을 업데이트 하였습니다.");
    }

    public RsData delete(ScheduleRequest.DeleteReq req) {

        Optional<Schedule> schedule = scheduleRepository.findById(req.getId());

        if(schedule.isEmpty()){
            return RsData.of(RsCode.F_04,"존재하지 않는 스케줄 입니다.");
        }

        scheduleRepository.delete(schedule.get());

        return RsData.of(RsCode.S_05,"스케줄을 삭제 하였습니다.");

    }

    public int timeValidation(LocalDate startDate, LocalDate endDate){
        return endDate.compareTo(startDate);
    }

}
