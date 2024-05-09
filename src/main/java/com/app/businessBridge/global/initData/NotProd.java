package com.app.businessBridge.global.initData;

import com.app.businessBridge.domain.chattingRoom.service.ChattingRoomService;
import com.app.businessBridge.domain.department.service.DepartmentService;
import com.app.businessBridge.domain.grade.service.GradeService;
import com.app.businessBridge.domain.member.Service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.nio.file.*;

@Configuration
@Profile({"dev", "test"})
public class NotProd {
    @Bean
    CommandLineRunner initData(DepartmentService departmentService, GradeService gradeService, MemberService memberService, ChattingRoomService chattingRoomService) {

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
            memberService.create(1,1,"admin",10001,"김관리","1234","admin@email.com");
            memberService.create(101,1001,"user1",20001,"이마부","1234","user1@email.com");
            memberService.create(102,1002,"user2",30001,"박영대","1234","user2@email.com");
            memberService.create(102,1002,"user3",30002,"홍길동","1234","user3@email.com");

            // 정산 테스트용 회원 생성
            memberService.createRebateTest(101,1001,"user4",20002,"김둘리","1234","user4@email.com",4000L);
            memberService.createRebateTest(101,1001,"user5",20003,"박또치","1234","user5@email.com",5000L);
            memberService.createRebateTest(102,1001,"user6",20004,"최도너","1234","user6@email.com",6000L);
            memberService.createRebateTest(102,1001,"user7",20005,"마이콜","1234","user7@email.com",7000L);
            memberService.createRebateTest(102,1001,"user8",20006,"고길동","1234","user8@email.com",8000L);

            chattingRoomService.create("채팅방1", memberService.findByUsername("admin").getData());
            chattingRoomService.create("채팅방2", memberService.findByUsername("admin").getData());
            chattingRoomService.create("채팅방3", memberService.findByUsername("user1").getData());

            chattingRoomService.invite(1L, memberService.findByUsername("user1").getData());
            chattingRoomService.invite(1L, memberService.findByUsername("user2").getData());
            chattingRoomService.invite(2L, memberService.findByUsername("user1").getData());


            // 이미지 저장하는 외부 경로 폴더 생성 로직 필요 시 추가
            Path directoryMail = Paths.get("C:\\B-bridge\\file_upload\\mail");
            Path directoryArticle = Paths.get("C:\\B-bridge\\file_upload\\article");
//            Path directoryQuestion = Paths.get("C:\\B-bridge\\file_upload\\question");
//            Path directoryMember = Paths.get("C:\\B-bridge\\file_upload\\member");

            try {
                Files.createDirectories(directoryMail);
                Files.createDirectories(directoryArticle);
//                Files.createDirectories(directoryQuestion);
//                Files.createDirectories(directoryMember);
            } catch (FileAlreadyExistsException e) {
                System.out.println("디렉토리가 이미 존재합니다");
            } catch (NoSuchFileException e) {
                System.out.println("디렉토리 경로가 존재하지 않습니다");
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}