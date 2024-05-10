package com.app.businessBridge.domain.education.service;


import com.app.businessBridge.domain.education.entity.Education;
import com.app.businessBridge.domain.education.repository.EducationRepository;
import com.app.businessBridge.domain.education.request.EducationRequest;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import com.app.businessBridge.global.request.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
    private final Request request;

    public RsData<Education> saveVideo(EducationRequest.SaveVideo videoReq) {

        if(educationRepository.existsByTitle(videoReq.getTitle())){
            return RsData.of(RsCode.F_06,"중복된 제목입니다.");
        }

        Education education = Education.builder()
                .author(request.getMember())
                .category(videoReq.getCategory())
                .title(videoReq.getTitle())
                .content(videoReq.getContent())
                .hit(0L)
                .build();

        educationRepository.save(education);

        return RsData.of(RsCode.S_02,"동영상을 게시했습니다.",education);
    }
}
