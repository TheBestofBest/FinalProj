package com.app.businessBridge.domain.education.controller;


import com.app.businessBridge.domain.education.entity.Education;
import com.app.businessBridge.domain.education.request.EducationRequest;
import com.app.businessBridge.domain.education.service.EducationService;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import com.app.businessBridge.global.image.service.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/educations")
public class ApiV1EducationController {

    private final EducationService educationService;
    private final ImageService imageService;

    @PostMapping("")
    public RsData saveVideo(@Valid EducationRequest.SaveVideo videoReq, BindingResult br, @RequestParam(name = "video", required = false) MultipartFile video) throws IOException {

        if(br.hasErrors() || video == null){
            return RsData.of(RsCode.F_06,"올바르지 않은 데이터 입니다.");
        }

        RsData<Education> result = educationService.saveVideo(videoReq);

        if(result.getIsSuccess()){
            imageService.createEduVideo(result.getData(),video);
        }

        return result;
    }

}
