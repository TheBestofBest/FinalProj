package com.app.businessBridge.domain.education.controller;

import com.app.businessBridge.domain.education.dto.EducationDto;
import com.app.businessBridge.domain.education.entity.Education;
import com.app.businessBridge.domain.education.request.EducationRequest;
import com.app.businessBridge.domain.education.response.EducationResponse;
import com.app.businessBridge.domain.education.service.EducationService;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/educations")
public class ApiV1EducationController {

    private final EducationService educationService;

    @PostMapping("")
    public RsData<EducationDto> saveVideo(@Valid EducationRequest.SaveVideo videoReq, BindingResult br, @RequestParam(name = "video", required = false) MultipartFile video) throws IOException {

        if(br.hasErrors() || video == null){
            return RsData.of(RsCode.F_06,"올바르지 않은 데이터 입니다.");
        }

        RsData<Education> result = educationService.saveVideo(videoReq,video);

        return RsData.of(result.getRsCode(), result.getMsg(), new EducationDto(result.getData()));
    }

    @GetMapping("")
    public RsData<EducationResponse.getVideos> getVideos(@RequestParam(value="page", defaultValue="0") int page){

        RsData<Page<Education>> result = educationService.getVideos(page);

        return RsData.of(result.getRsCode(), result.getMsg(), new EducationResponse.getVideos(result.getData()));
    }

}
