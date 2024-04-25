package com.app.businessBridge.global.initData;

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
    CommandLineRunner initData() {

        return args -> {

            // 이미지 저장하는 외부 경로 폴더 생성 로직 필요 시 추가
            Path directoryArticle = Paths.get("C:\\B-bridge\\file_upload\\mail");
            Path directoryReview = Paths.get("C:\\B-bridge\\file_upload\\article");
//            Path directoryQuestion = Paths.get("C:\\B-bridge\\file_upload\\question");
//            Path directoryMember = Paths.get("C:\\B-bridge\\file_upload\\member");

            try {
                Files.createDirectories(directoryArticle);
                Files.createDirectories(directoryReview);
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