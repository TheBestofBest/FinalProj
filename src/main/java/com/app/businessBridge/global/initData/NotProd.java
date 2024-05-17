//package com.app.businessBridge.global.initData;
//
//import com.app.businessBridge.domain.alarm.service.AlarmService;
//import com.app.businessBridge.domain.chattingRoom.service.ChattingRoomService;
//import com.app.businessBridge.domain.confirmFormType.entity.ConfirmFormType;
//import com.app.businessBridge.domain.confirmFormType.service.ConfirmFormTypeService;
//import com.app.businessBridge.domain.confirmStatus.entity.ConfirmStatus;
//import com.app.businessBridge.domain.confirmStatus.service.ConfirmStatusService;
//import com.app.businessBridge.domain.department.service.DepartmentService;
//import com.app.businessBridge.domain.division.service.DivisionService;
//import com.app.businessBridge.domain.grade.service.GradeService;
//import com.app.businessBridge.domain.member.Service.MemberService;
//import com.app.businessBridge.domain.rebate.service.RebateService;
//import com.app.businessBridge.global.holidayapi.workingdate.service.WorkingDateService;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//import java.io.IOException;
//import java.nio.file.*;
//import java.time.LocalDate;
//import java.util.Random;
//
//@Configuration
//@Profile({"dev", "test"})
//public class NotProd {
//
//    @Bean
//    CommandLineRunner initData(DivisionService divisionService,
//                               DepartmentService departmentService,
//                               GradeService gradeService,
//                               MemberService memberService,
//                               ChattingRoomService chattingRoomService,
//                               RebateService rebateService,
//                               WorkingDateService workingDateService,
//                               AlarmService alarmService,
//                               ConfirmFormTypeService confirmFormTypeService, ConfirmStatusService confirmStatusService) {
//
//        return args -> {
//
//            // 소속 생성
//            divisionService.create(0,"해당없음");
//            divisionService.create(1,"대표이사");
//            divisionService.create(10,"기술관리");
//            divisionService.create(11,"경영지원본부");
//            divisionService.create(12,"R&D본부");
//            divisionService.create(9999,"테스트 전용 소속");
//
//            // 부서 생성
//            departmentService.create(0,"해당없음");
//            departmentService.create(1,"대표이사");
//            departmentService.create(100, "관리");
//            departmentService.create(101, "마케팅");
//            departmentService.create(102, "영업");
//            departmentService.create(9999,"테스트 전용 부서");
//
//            // 직급 생성
//            gradeService.create(0,"해당없음");
//            gradeService.create(1,"대표이사");
//            gradeService.create(1000, "슈퍼관리자");
//            gradeService.create(1001, "부장");
//            gradeService.create(1002, "차장");
//            gradeService.create(1003, "과장");
//            gradeService.create(1004, "대리");
//            gradeService.create(1005, "주임");
//            gradeService.create(1006, "사원");
//            gradeService.create(9999,"테스트 전용 직급");
//
//            // 회원 생성
////            memberService.create(1,1,1,"CEO",10000,"왕대표","1234","CEO@emil.com");
////            memberService.create(10,100, 1000, "admin", 10001, "김관리", "1234", "admin@email.com");
////            memberService.create(11,101, 1001, "user1", 20001, "이마부", "1234", "user1@email.com");
////            memberService.create(11,102, 1002, "user2", 30001, "박영대", "1234", "user2@email.com");
////            memberService.create(12,102, 1002, "user3", 30002, "홍길동", "1234", "user3@email.com");
//            // 추가해야될거, 연봉, 성별, 나이, 개인번호, 내선번호
//            // 올 해 월 별 근무일 일괄 계산
//            workingDateService.createThisYear();
//
//            memberService.createFullTest(1,1,1,"CEO",10000,"왕대표","1234","CEO@emil.com", 10000000000L, '남', "46", "01077777777","0427777777");
//            memberService.createFullTest(10,100, 1000, "admin", 10001, "김관리", "1234", "admin@email.com", 99999999999L, '남', "99", "01099999999","0429999999");
//            memberService.createFullTest(11,101, 1001, "user1", 20001, "이마부", "1234", "user1@email.com", 620000000L, '여', "43", "01012341234","0421231234");
//            memberService.createFullTest(11,102, 1002, "user2", 30001, "박영대", "1234", "user2@email.com", 340000000L, '남', "31", "01012341234","0421231234");
//            memberService.createFullTest(12,102, 1002, "user3", 30002, "홍길동", "1234", "user3@email.com", 330000000L, '남', "29", "01012341234","0421231234");
//
//            String[] firstName = {"김", "이", "최", "박", "조", "왕", "홍", "양", "전", "류", "신"};
//            String[] secondName = {"철수", "영희", "민수", "수정", "기범", "미현", "광식", "두환", "정배", "오빈", "희동", "한선", "대호", "율택", " 신", " 현", "성진", "민호", "진아", "가은", "지혜", "혜지", "수지", "나라", "동건"};
//            char[] sex = {'남','여'};
//
//            Random random = new Random();
//
//
//
//            // 정산, 통계 테스트용 회원 생성
//            for (int i = 4; i < 504; i++) {
//
//                int randomNum2 = (int) (Math.random() * 100 + 1);
//                int randomAge2 = (int) (Math.random() * 100 + 1);
//                String testFirstName = firstName[random.nextInt(firstName.length)];
//                String testSecondName = secondName[random.nextInt(secondName.length)];
//                int testDivision = 10 + random.nextInt(3);
//                int testDept = 100 + random.nextInt(3);
//                int testGrade = 1000 + random.nextInt(6)+1;
//                char testSex = sex[random.nextInt(2)];
//
//                memberService.createFullTest(testDivision,testDept, testGrade, "user" + i, 20000 + i, (testFirstName + testSecondName), "1234", "user" + i + "@email.com", randomNum2 * 1000000L, testSex, String.valueOf(randomAge2), "01012341234","0421231234");
//            }
//
//            LocalDate currentDate = LocalDate.now();
//            int year = currentDate.getYear();
//            int month = currentDate.getMonthValue();
//
//            for (int i = 1; i < 506; i++) {
//                rebateService.createRebate(memberService.findById((long) i).getData(), String.valueOf(year), String.valueOf(month));
//                rebateService.createRebate(memberService.findById((long) i).getData(), String.valueOf(year), String.valueOf(month-1));
//                rebateService.createRebate(memberService.findById((long) i).getData(), String.valueOf(year), String.valueOf(month-2));
//                rebateService.createRebate(memberService.findById((long) i).getData(), String.valueOf(year), String.valueOf(month-3));
//                rebateService.createRebate(memberService.findById((long) i).getData(), String.valueOf(year), String.valueOf(month-4));
//            }
//
//            chattingRoomService.create("채팅방1", memberService.findByUsername("admin").getData());
//            chattingRoomService.create("채팅방2", memberService.findByUsername("admin").getData());
//            chattingRoomService.create("채팅방3", memberService.findByUsername("user1").getData());
//
//            chattingRoomService.invite(1L, memberService.findByUsername("user1").getData());
//            chattingRoomService.invite(1L, memberService.findByUsername("user2").getData());
//            chattingRoomService.invite(2L, memberService.findByUsername("user1").getData());
//
//            alarmService.save("all",0L,"전체 1번 데이터");
//            alarmService.save("dept",1L,"부서 1번 데이터");
//            alarmService.save("dept",2L,"부서 2번 데이터");
//            alarmService.save("all",0L,"전체 2번 데이터");
//            alarmService.save("all",0L,"전체 3번 데이터");
//            alarmService.save("dept",3L,"부서 3번 데이터");
//            alarmService.save("dept",1L,"부서 4번 데이터");
//            alarmService.save("dept",2L,"부서 5번 데이터");
//            alarmService.save("dept",3L,"부서 6번 데이터");
//            alarmService.save("member",1L,"회원 1번 데이터");
//            alarmService.save("member",2L,"회원 2번 데이터");
//            alarmService.save("all",0L,"전체 4번 데이터");
//            alarmService.save("member",3L,"회원 3번 데이터");
//            alarmService.save("member",1L,"회원 4번 데이터");
//            alarmService.save("member",2L,"회원 5번 데이터");
//            alarmService.save("member",3L,"회원 6번 데이터");
//            alarmService.save("all",0L,"전체 5번 데이터");
//
//
//            // 이미지 저장하는 외부 경로 폴더 생성 로직 필요 시 추가
//            Path directoryMail = Paths.get("C:\\B-bridge\\file_upload\\mail");
//            Path directoryArticle = Paths.get("C:\\B-bridge\\file_upload\\article");
//            Path directoryEducation = Paths.get("C:\\B-bridge\\file_upload\\education");
////            Path directoryQuestion = Paths.get("C:\\B-bridge\\file_upload\\question");
////            Path directoryMember = Paths.get("C:\\B-bridge\\file_upload\\member");
//
//            try {
//                Files.createDirectories(directoryMail);
//                Files.createDirectories(directoryArticle);
//                Files.createDirectories(directoryEducation);
////                Files.createDirectories(directoryQuestion);
////                Files.createDirectories(directoryMember);
//            } catch (FileAlreadyExistsException e) {
//                System.out.println("디렉토리가 이미 존재합니다");
//            } catch (NoSuchFileException e) {
//                System.out.println("디렉토리 경로가 존재하지 않습니다");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            // 결재 타입 생성
//
//            confirmFormTypeService.create("휴가 신청", "휴가를 신청합니다.");
//
//
//
//            // 결재 상태 생성
//            confirmStatusService.create("결재 처리중", "결재를 처리가 필요합니다.");
//            confirmStatusService.create("승인", "결재가 승인 됐습니다.");
//            confirmStatusService.create("반려", "결재가 반려 됐습니다..");
//        };
//    }
//}
