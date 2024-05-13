package com.app.businessBridge.domain.education.response;

import com.app.businessBridge.domain.education.dto.EducationDto;
import com.app.businessBridge.domain.education.entity.Education;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class EducationResponse {

    @Getter
    @Setter
    public static class getVideos {
        Page<EducationDto> videos;

        public getVideos(Page<Education> videos){
            this.videos = videos.map(EducationDto::new);
        }

    }
}
