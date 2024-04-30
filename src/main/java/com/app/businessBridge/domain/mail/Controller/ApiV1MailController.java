package com.app.businessBridge.domain.mail.Controller;

import com.app.businessBridge.domain.mail.DTO.MailDTO;
import com.app.businessBridge.domain.mail.Response.MailResponse;
import com.app.businessBridge.domain.mail.Response.MailsResponse;
import com.app.businessBridge.domain.mail.Service.MailService;
import com.app.businessBridge.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/v1/mails")
public class ApiV1MailController {
    private final MailService mailService;

    // 기본 코드들로 우선 미리 넣어두었습니다 다시 수정 할 예정

//    @GetMapping("")
//    public RsData<MailsResponse> getMails() {
//        List<MailDTO> mailsDTOList = this.mailService
//                .getList()
//                .stream()
//                .map(mails -> new MailDTO(mails))
//                .toList();
//        return RsData.of("S-01", "Success 요청 성공", new MailsResponse(mailsDTOList));
//    }
//
//    @GetMapping("/{id}")
//    public RsData<MailResponse> getMail(@PathVariable("id") Long id) {
//        return mailService.getMail(id).map(mail -> RsData.of(
//                "S-01",
//                "Success 조회 성공",
//                new MailResponse(new MailDTO(mail))
//        )).orElseGet(() -> RsData.of(
//                "F-01",
//                "Bad Request %d 번 메일은 존재하지 않습니다.".formatted(id),
//                null
//        ));
//    }

}
