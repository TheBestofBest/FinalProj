package com.app.businessBridge.domain.alarm.service;

import com.app.businessBridge.domain.alarm.entity.Alarm;
import com.app.businessBridge.domain.alarm.repository.AlarmRepository;
import com.app.businessBridge.domain.alarm.response.AlarmResponse;
import com.app.businessBridge.domain.education.entity.Education;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import com.app.businessBridge.global.request.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final Request rq;

    public void save(String relationName, Long relationId, String content){

        Alarm alarm = Alarm.builder()
                .relationName(relationName)
                .relationId(relationId)
                .content(content)
                .build();

        alarmRepository.save(alarm);
    }

    public RsData<List<Alarm>> getTop10() {
        Member member = rq.getMember();
        List<Alarm> top10 = alarmRepository.findTop10(member.getId(),member.getDepartment().getId());

        return RsData.of(RsCode.S_05,"조회 성공",top10);
    }

    public RsData<Page<Alarm>> getAlarms(int page) {

        Member member = rq.getMember();

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));

        Pageable pageable = PageRequest.of(page, 8, Sort.by(sorts));

        Page<Alarm> alarms = alarmRepository.findByRelationNameAndRelationIdOrRelationNameAndRelationIdOrRelationNameAndRelationId(
                "all" ,0L,
                "dept", member.getDepartment().getId(),
                "member", member.getId(),
                pageable
        );

        return RsData.of(RsCode.S_05,"조회 성공",alarms);
    }

    public void delete(Long id) {
        Alarm alarm = alarmRepository.findById(id).get();
        alarmRepository.delete(alarm);
    }
}
