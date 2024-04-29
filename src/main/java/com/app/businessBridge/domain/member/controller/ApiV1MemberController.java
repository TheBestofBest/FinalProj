package com.app.businessBridge.domain.member.controller;

import com.app.businessBridge.domain.member.DTO.MemberDTO;
import com.app.businessBridge.domain.member.Service.MemberService;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class ApiV1MemberController {
    private final MemberService memberService;

    @GetMapping("/{id}")
    public RsData<MemberDTO> getMember(@PathVariable(value = "id") Long id) {
        RsData<Member> rsData = this.memberService.findById(id);

        // 멤버 null 일시 dto변환 리턴에 에러 터질 수 있음.
        return RsData.of(rsData.getRsCode(), rsData.getMsg(), new MemberDTO(rsData.getData()));
    }
}
