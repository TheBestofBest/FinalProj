package com.app.businessBridge.domain.education.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class EducationRequest {

    @Getter
    @Setter
    public static class SaveVideo {
        private String title;
        private String content;
    }

    @Getter
    @Setter
    public static class EditVideo {
        private Long id;
        private String title;
        private String content;
    }

}
