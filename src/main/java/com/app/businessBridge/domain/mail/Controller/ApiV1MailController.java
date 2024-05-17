package com.app.businessBridge.domain.mail.Controller;


import com.app.businessBridge.domain.mail.Entity.Mail;
import com.app.businessBridge.domain.mail.Request.MailRequest;
import com.app.businessBridge.domain.mail.Response.MailResponse;
import com.app.businessBridge.domain.mail.Service.MailService;
import com.app.businessBridge.domain.member.Service.MemberService;
import com.app.businessBridge.domain.member.entity.Member;
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
    private final MemberService memberService;

    @PostMapping("")
    public RsData sendMail(@Valid @RequestBody MailRequest.CreateRequest createRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RsData.of(RsCode.F_06, "올바른 요청이 아닙니다.");
        }

        RsData<Member> senderRsData = memberService.findByEmail(createRequest.getSender().getEmail());
        if (!senderRsData.getIsSuccess()) {
            return RsData.of(senderRsData.getRsCode(), senderRsData.getMsg(), null);
        }

        RsData<Member> receiverRsData = memberService.findByEmail(createRequest.getReceiver().getEmail());
        if (!receiverRsData.getIsSuccess()) {
            return RsData.of(receiverRsData.getRsCode(), receiverRsData.getMsg(), null);
        }

        RsData<Member> referenceRsData = null;
        if (createRequest.getReference() != null) {
            referenceRsData = memberService.findByEmail(createRequest.getReference().getEmail());
            if (!referenceRsData.getIsSuccess()) {
                return RsData.of(referenceRsData.getRsCode(), referenceRsData.getMsg(), null);
            }
        }

        // 발신자, 수신자, 참조자 정보를 가져옵니다.
        Member sender = senderRsData.getData();
        Member receiver = receiverRsData.getData();
        Member reference = referenceRsData != null ? referenceRsData.getData() : null;

        // 메일 생성 서비스를 호출하여 메일을 전송하고 결과를 받습니다.
        RsData result = mailService.create(createRequest, sender, receiver, reference);

        // 메일 전송 결과에 따라 적절한 응답을 반환합니다.
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
