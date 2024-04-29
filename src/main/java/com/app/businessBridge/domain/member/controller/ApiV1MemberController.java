package com.app.businessBridge.domain.member.controller;

import com.app.businessBridge.domain.member.DTO.MemberDTO;
import com.app.businessBridge.domain.member.Service.MemberService;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.member.request.PatchMemberRequest;
import com.app.businessBridge.domain.member.request.PostMemberRequest;
import com.app.businessBridge.domain.member.response.MemberResponse;
import com.app.businessBridge.domain.member.response.PatchMemberResponse;
import com.app.businessBridge.domain.member.response.PostMemberResponse;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class ApiV1MemberController {
    private final MemberService memberService;

    @GetMapping("/{id}")
    public RsData<MemberResponse> getMember(@PathVariable(value = "id") Long id) {
        RsData<Member> rsData = this.memberService.findById(id);

        // 멤버 null일 시 dto변환 리턴에 에러 터질 수 있음.
        return RsData.of(rsData.getRsCode(), rsData.getMsg(), new MemberResponse(new MemberDTO(rsData.getData())));
    }

    @PostMapping("")
    public RsData<PostMemberResponse> postMember(@Valid @RequestBody PostMemberRequest postMemberRequest) {
        RsData<MemberDTO> rsData = this.memberService.create(postMemberRequest.getDepartmentId(), postMemberRequest.getGradeId(),
                postMemberRequest.getUsername(), postMemberRequest.getMemberNumber(), postMemberRequest.getName(),
                postMemberRequest.getPassword(), postMemberRequest.getEmail());

        return RsData.of(rsData.getRsCode(), rsData.getMsg(), new PostMemberResponse(rsData.getData()));
    }

    @PatchMapping("/{id}")
    public RsData<PatchMemberResponse> patchMember(@PathVariable(value = "id") Long id,
                                                   @Valid @RequestBody PatchMemberRequest patchMemberRequest) {
        RsData<MemberDTO> rsData = this.memberService.update(id, patchMemberRequest.getDepartmentId(), patchMemberRequest.getGradeId(),
                patchMemberRequest.getUsername(), patchMemberRequest.getMemberNumber(), patchMemberRequest.getName(),
                patchMemberRequest.getPassword(), patchMemberRequest.getEmail());

        return RsData.of(rsData.getRsCode(), rsData.getMsg(), new PatchMemberResponse(rsData.getData()));
    }

    @DeleteMapping("/{id}")
    public RsData<MemberResponse> deleteMember(@PathVariable(value = "id") Long id) {
        RsData<MemberDTO> rsData = this.memberService.delete(id);

        // 멤버 null일 시 dto변환 리턴에 에러 터질 수 있음.
        return RsData.of(rsData.getRsCode(), rsData.getMsg(), new MemberResponse(rsData.getData()));
    }
}
