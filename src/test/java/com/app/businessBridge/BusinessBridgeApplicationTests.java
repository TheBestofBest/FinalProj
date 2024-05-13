package com.app.businessBridge;

import com.app.businessBridge.domain.member.Service.MemberService;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.schedule.entity.Schedule;
import com.app.businessBridge.domain.schedule.repository.ScheduleRepository;
import com.app.businessBridge.global.RsData.RsData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;

@SpringBootTest
class BusinessBridgeApplicationTests {

//    @Autowired
//    WebSocketChatHandler webSocketChatHandler;


    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    MemberService memberService;


    @Test
    void contextLoads() {
    }

    @Test
    void scheduleCreate() {
        // 회원 생성
        memberService.create(1,1,"admin",10001,"김관리","1234","admin@email.com");

        RsData<Member> member = memberService.findByUsername("admin");

        // 스케줄 생성
        Schedule schedule = Schedule.builder()
                .authorName(member.getData().getName())
                .relationName("all")
                .relationId(0L)
                .name("전체 1번 스케줄")
                .startDate(LocalDate.of(2024,5,13))
                .endDate(LocalDate.of(2024,5,14))
                .build();

        // 스케줄 저장
        scheduleRepository.save(schedule);

        // 스케줄 개수 확인
        Assertions.assertThat(scheduleRepository.findAll().size()).isEqualTo(1);




    }

}
