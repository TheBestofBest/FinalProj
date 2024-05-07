package com.app.businessBridge.global.initData;

import com.app.businessBridge.domain.department.service.DepartmentService;
import com.app.businessBridge.domain.grade.service.GradeService;
import com.app.businessBridge.domain.member.Service.MemberService;
import com.app.businessBridge.domain.department.entity.Department;
import com.app.businessBridge.domain.department.repository.DepartmentRepository;
import com.app.businessBridge.domain.grade.entity.Grade;
import com.app.businessBridge.domain.grade.repository.GradeRepository;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.member.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.nio.file.*;

@Configuration
@Profile({"dev", "test"})
public class NotProd {
    @Bean
    CommandLineRunner initData(DepartmentService departmentService, GradeService gradeService, MemberService memberService) {

        return args -> {

            // 부서 생성
            departmentService.create(1,"관리");
            departmentService.create(101,"마케팅");
            departmentService.create(102,"영업");

            // 직급 생성
            gradeService.create(1,"슈퍼관리자");
            gradeService.create(1001,"부장");
            gradeService.create(1002,"대리");

            // 회원 생성
            memberService.create(1L,1L,"admin",10001,"김관리","1234","admin@email.com");
            memberService.create(2L,2L,"user1",20001,"이마부","1234","user1@email.com");
            memberService.create(3L,3L,"user2",30001,"박영대","1234","user2@email.com");
            memberService.create(3L,3L,"user3",30002,"홍길동","1234","user3@email.com");

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