package com.app.businessBridge.global.initData;

import com.app.businessBridge.domain.alarm.service.AlarmService;
import com.app.businessBridge.domain.chattingRoom.service.ChattingRoomService;
import com.app.businessBridge.domain.confirmFormType.entity.ConfirmFormType;
import com.app.businessBridge.domain.confirmFormType.service.ConfirmFormTypeService;
import com.app.businessBridge.domain.confirmStatus.entity.ConfirmStatus;
import com.app.businessBridge.domain.confirmStatus.service.ConfirmStatusService;
import com.app.businessBridge.domain.department.service.DepartmentService;
import com.app.businessBridge.domain.grade.service.GradeService;
import com.app.businessBridge.domain.member.Service.MemberService;
import com.app.businessBridge.domain.rebate.service.RebateService;
import com.app.businessBridge.global.holidayapi.workingdate.service.WorkingDateService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;

@Configuration
@Profile({"dev", "test"})
public class NotProd {

    @Bean
    CommandLineRunner initData(DepartmentService departmentService,
                               GradeService gradeService,
                               MemberService memberService,
                               ChattingRoomService chattingRoomService,
                               RebateService rebateService,
                               WorkingDateService workingDateService,
                               AlarmService alarmService,
                               ConfirmFormTypeService confirmFormTypeService, ConfirmStatusService confirmStatusService) {

        return args -> {

            // 부서 생성
            departmentService.create(1, "관리");
            departmentService.create(101, "마케팅");
            departmentService.create(102, "영업");

            // 직급 생성
            gradeService.create(1, "슈퍼관리자");
            gradeService.create(1001, "부장");
            gradeService.create(1002, "대리");

            // 회원 생성
            memberService.create(1, 1, "admin", 10001, "김관리", "1234", "admin@email.com");
            memberService.create(101, 1001, "user1", 20001, "이마부", "1234", "user1@email.com");
            memberService.create(102, 1002, "user2", 30001, "박영대", "1234", "user2@email.com");
            memberService.create(102, 1002, "user3", 30002, "홍길동", "1234", "user3@email.com");

            // 올 해 월 별 근무일 일괄 계산
            workingDateService.createThisYear();

            // 정산, 통계 테스트용 회원 생성
            for (int i = 4; i < 104; i++) {
                int randomNum = (int) (Math.random() * 100 + 1);
                int randomAge = (int) (Math.random() * 100 + 1);
                if (i % 2 == 0) {
                    memberService.createRebateTest(101, 1002, "user" + i, 20000 + i, "직원" + i, "1234", "user" + i + "@email.com", randomNum * 1000000L, '남', String.valueOf(randomAge));
                }
                memberService.createRebateTest(102, 1001, "user" + i, 20000 + i, "직원" + i, "1234", "user" + i + "@email.com", randomNum * 1000000L, '여', String.valueOf(randomAge));
            }

            LocalDate currentDate = LocalDate.now();
            int year = currentDate.getYear();
            int month = currentDate.getMonthValue();

            for (int i = 5; i < 105; i++) {
                rebateService.createRebate(memberService.findById((long) i).getData(), String.valueOf(year), String.valueOf(month));
                rebateService.createRebate(memberService.findById((long) i).getData(), String.valueOf(year), String.valueOf(month-1));
                rebateService.createRebate(memberService.findById((long) i).getData(), String.valueOf(year), String.valueOf(month-2));
                rebateService.createRebate(memberService.findById((long) i).getData(), String.valueOf(year), String.valueOf(month-3));
                rebateService.createRebate(memberService.findById((long) i).getData(), String.valueOf(year), String.valueOf(month-4));
            }

            chattingRoomService.create("채팅방1", memberService.findByUsername("admin").getData());
            chattingRoomService.create("채팅방2", memberService.findByUsername("admin").getData());
            chattingRoomService.create("채팅방3", memberService.findByUsername("user1").getData());

            chattingRoomService.invite(1L, memberService.findByUsername("user1").getData());
            chattingRoomService.invite(1L, memberService.findByUsername("user2").getData());
            chattingRoomService.invite(2L, memberService.findByUsername("user1").getData());

            alarmService.save("all",0L,"전체 1번 데이터");
            alarmService.save("dept",1L,"부서 1번 데이터");
            alarmService.save("dept",2L,"부서 2번 데이터");
            alarmService.save("all",0L,"전체 2번 데이터");
            alarmService.save("all",0L,"전체 3번 데이터");
            alarmService.save("dept",3L,"부서 3번 데이터");
            alarmService.save("dept",1L,"부서 4번 데이터");
            alarmService.save("dept",2L,"부서 5번 데이터");
            alarmService.save("dept",3L,"부서 6번 데이터");
            alarmService.save("member",1L,"회원 1번 데이터");
            alarmService.save("member",2L,"회원 2번 데이터");
            alarmService.save("all",0L,"전체 4번 데이터");
            alarmService.save("member",3L,"회원 3번 데이터");
            alarmService.save("member",1L,"회원 4번 데이터");
            alarmService.save("member",2L,"회원 5번 데이터");
            alarmService.save("member",3L,"회원 6번 데이터");
            alarmService.save("all",0L,"전체 5번 데이터");


            // 이미지 저장하는 외부 경로 폴더 생성 로직 필요 시 추가
            Path directoryMail = Paths.get("C:\\B-bridge\\file_upload\\mail");
            Path directoryArticle = Paths.get("C:\\B-bridge\\file_upload\\article");
            Path directoryEducation = Paths.get("C:\\B-bridge\\file_upload\\education");
//            Path directoryQuestion = Paths.get("C:\\B-bridge\\file_upload\\question");
//            Path directoryMember = Paths.get("C:\\B-bridge\\file_upload\\member");

            try {
                Files.createDirectories(directoryMail);
                Files.createDirectories(directoryArticle);
                Files.createDirectories(directoryEducation);
//                Files.createDirectories(directoryQuestion);
//                Files.createDirectories(directoryMember);
            } catch (FileAlreadyExistsException e) {
                System.out.println("디렉토리가 이미 존재합니다");
            } catch (NoSuchFileException e) {
                System.out.println("디렉토리 경로가 존재하지 않습니다");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 결재 타입 생성

            confirmFormTypeService.create("휴가 신청", "휴가를 신청합니다.");



            // 결재 상태 생성
            confirmStatusService.create("결재 처리중", "결재를 처리가 필요합니다.");
            confirmStatusService.create("승인", "결재가 승인 됐습니다.");
            confirmStatusService.create("반려", "결재가 반려 됐습니다..");
        };
    }
}