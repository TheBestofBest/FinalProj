package com.app.businessBridge.domain.mail.Controller;


import com.app.businessBridge.domain.mail.Entity.Mail;
import com.app.businessBridge.domain.mail.Request.MailRequest;
import com.app.businessBridge.domain.mail.Response.MailResponse;
import com.app.businessBridge.domain.mail.Service.MailService;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mails")
public class ApiV1MailController {

    private final MailService mailService;

    @PostMapping("")
    public RsData sendMail(@Valid @RequestBody MailRequest.CreateRequest createRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RsData.of(RsCode.F_06, "올바른 요청이 아닙니다.");
        }
        RsData result = mailService.create(createRequest);
        if (result.getIsSuccess()) {
            return RsData.of(RsCode.S_01, "메일이 성공적으로 전송되었습니다.");
        } else {
            return RsData.of(RsCode.F_01, "메일 전송에 실패했습니다.", null);
        }
    }

    @DeleteMapping("/{id}")
    public RsData deleteById(@PathVariable(name = "id") Long id){

        return mailService.deleteById(id);
    }

    @GetMapping("/{id}")
    public RsData<MailResponse.sendMail> getSendMailById(@PathVariable(name = "id") Long id){
        RsData<Mail> result = mailService.findById(id);

        return RsData.of(result.getRsCode(), result.getMsg(), new MailResponse.sendMail(result.getData()));

    }

}
