package com.app.businessBridge.domain.education.service;


import com.app.businessBridge.domain.education.DurationExtractor;
import com.app.businessBridge.domain.education.ThumbnailExtractor;
import com.app.businessBridge.domain.education.dto.EducationDto;
import com.app.businessBridge.domain.education.entity.Education;
import com.app.businessBridge.domain.education.repository.EducationRepository;
import com.app.businessBridge.domain.education.request.EducationRequest;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import com.app.businessBridge.global.request.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
    private final Request request;

    @Value("${custom.fileDirPath}")
    private String fileDirPath;

    public RsData<Education> saveVideo(EducationRequest.SaveVideo videoReq, MultipartFile video) throws IOException {

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

        education = createThumbnailAndVideoLength(education,video);

        educationRepository.save(education);

        return RsData.of(RsCode.S_02,"동영상을 게시했습니다.",education);
    }

    private Education createThumbnailAndVideoLength(Education education, MultipartFile video) throws IOException {

        // 비디오 저장
        String videoName = "";
        String videoPath = "";

        videoName = "education/" + UUID.randomUUID().toString() +"."+ video.getContentType().split("/")[1];
        File representImgFile = new File(fileDirPath + "/" + videoName);
        video.transferTo(representImgFile);
        videoPath = "http://localhost:8090/file/" + videoName;

        // 썸네일 및  저장
        String thumbnailPath = ThumbnailExtractor.extract(representImgFile);
        double videoLength = DurationExtractor.extract(representImgFile);

        education = education.toBuilder()
                .videoLength(videoLength)
                .filePath(videoPath)
                .thumbnailPath("http://localhost:8090/file/education/" + thumbnailPath)
                .build();

        return education;
    }

    public RsData<Page<Education>> getVideos(int page) {

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));

        Pageable pageable = PageRequest.of(page, 8, Sort.by(sorts));

        Page<Education> videos = educationRepository.findAll(pageable);

        return  RsData.of(RsCode.S_05,"데이터 조회 성공",videos);
    }
}
