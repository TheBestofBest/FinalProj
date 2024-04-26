package com.app.businessBridge.global.image.service;

import com.app.businessBridge.domain.education.entity.Education;
import com.app.businessBridge.global.image.entity.Image;
import com.app.businessBridge.global.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${custom.fileDirPath}")
    private String fileDirPath;

    public Image createEduImg(Education education, MultipartFile representImg) throws IOException {

        // 프로젝트 외부 저장
        // C://B-bridge//file_upload//education
        String thumnailPath = "";
        String thunmail = "";

        if (representImg.isEmpty()) {
            // 기본이미지 생성 필요
            thumnailPath = "";
        } else if (!representImg.isEmpty()) {
            thunmail = "product/" + UUID.randomUUID().toString() + ".jpg";
            File representImgFile = new File(fileDirPath + "/" + thunmail);
            representImg.transferTo(representImgFile);
            thumnailPath = "/file/" + thunmail;
        }

        Image img = Image.builder()
                .type("education")
                .filePath(thumnailPath)
                .build();

        this.imageRepository.save(img);

        return img;
    }
}
