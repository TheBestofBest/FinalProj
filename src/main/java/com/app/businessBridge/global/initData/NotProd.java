package com.app.businessBridge.global.initData;

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

import java.io.IOException;
import java.nio.file.*;

@Configuration
@Profile({"dev", "test"})
public class NotProd {
    @Bean
    CommandLineRunner initData(MemberRepository memberRepository, DepartmentRepository departmentRepository, GradeRepository gradeRepository) {

        return args -> {
            Department department = Department.builder().departmentCode(1).departmentName("사원").build();
            departmentRepository.save(department);
            Grade grade = Grade.builder().gradeCode(1).gradeName("팀").build();
            gradeRepository.save(grade);
            Member member = Member.builder()
                    .department(department)
                    .grade(grade)
                    .username("user01")
                    .memberNumber(101)
                    .name("김사원")
                    .password("1234")
                    .email("test@test.com")
                    .build();
            memberRepository.save(member);
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