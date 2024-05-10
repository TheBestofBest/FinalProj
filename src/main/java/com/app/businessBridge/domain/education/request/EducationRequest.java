package com.app.businessBridge.domain.education.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class EducationRequest {

    @Getter
    @Setter
    public static class SaveVideo {
        private String category;
        private String title;
        private String content;
    }

}
