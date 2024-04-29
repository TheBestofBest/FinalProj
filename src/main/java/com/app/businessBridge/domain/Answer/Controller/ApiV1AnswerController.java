package com.app.businessBridge.domain.Answer.Controller;

import com.app.businessBridge.domain.Answer.DTO.AnswerDto;
import com.app.businessBridge.domain.Answer.Service.AnswerService;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/answers")
public class ApiV1AnswerController {
    private final AnswerService answerService;






    @Data
    public static class AnswerRequest{
        @NotBlank
        private String content;
    }
    @AllArgsConstructor
    @Getter
    public static class AnswerResponse{
        private final AnswerDto answerDto;
    }

}
