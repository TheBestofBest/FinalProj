package com.app.businessBridge.domain.education.dto;


import com.app.businessBridge.domain.education.entity.Education;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class EducationDto {

    private Long id;

    private String authorName;

    private String title;

    private String content;

    private Long hit;

    private String filePath;

    private String thumbnailPath;

    private double videoLength;

//    private Long mettingRoomId;

    public EducationDto(Education education) {
        this.id = education.getId();
        this.authorName = education.getAuthor().getName();
        this.title = education.getTitle();
        this.content = education.getContent();
        this.hit = education.getHit();
        this.filePath = education.getFilePath();
        this.thumbnailPath = education.getThumbnailPath();
        this.videoLength = education.getVideoLength();

//        this.mettingRoomId = education.getMettingRoom().getId();
    }

}
