package com.app.businessBridge.domain.member.controller;

import com.app.businessBridge.domain.member.Service.MemberService;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.member.request.MemberRequest;
import com.app.businessBridge.domain.member.response.MemberResponse;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class ApiV1MemberController {
    private final MemberService memberService;

    // 멤버 생성
    @PostMapping("/signup")
    public RsData<MemberResponse.GetMember> signup(@Valid @RequestBody MemberRequest.CreateRequset createRequset,
                                                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return RsData.of(RsCode.F_10, "알 수 없는 오류로 실패했습니다.");
        }

        RsData<Member> rsData = this.memberService.create(createRequset.getDepartmentId(), createRequset.getGradeId(),
                createRequset.getUsername(), createRequset.getMemberNumber(), createRequset.getName(),
                createRequset.getPassword(), createRequset.getEmail());

        return RsData.of(rsData.getRsCode(), rsData.getMsg(), new MemberResponse.GetMember(rsData.getData()));
    }

//    @PostMapping("/login")
//    public RsData<MemberResponse.GetMember> login (@Valid)

    // 멤버 단건 조회
    @GetMapping("/{id}")
    public RsData<MemberResponse.GetMember> getMember(@PathVariable(value = "id") Long id) {
        RsData<Member> rsData = this.memberService.findById(id);

        return RsData.of(rsData.getRsCode(), rsData.getMsg(), new MemberResponse.GetMember(rsData.getData()));
    }

    // 멤버 수정
    @PatchMapping("")
    public RsData<MemberResponse.PatchedMember> patchMember(@Valid @RequestBody MemberRequest.UpdateRequest updateRequest,
                                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RsData.of(RsCode.F_10, "알 수 없는 오류로 실패했습니다.");
        }
        RsData<Member> rsData = this.memberService.update(updateRequest.getId(), updateRequest.getDepartmentId(), updateRequest.getGradeId(),
                updateRequest.getUsername(), updateRequest.getMemberNumber(), updateRequest.getName(),
                updateRequest.getPassword(), updateRequest.getEmail());

        return RsData.of(rsData.getRsCode(), rsData.getMsg(), new MemberResponse.PatchedMember(rsData.getData()));
    }

    // 멤버 삭제
    @DeleteMapping("")
    public RsData deleteMember(@Valid @RequestBody MemberRequest.DeleteRequest deleteRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RsData.of(RsCode.F_10, "알 수 없는 오류로 실패했습니다.");
        }

        RsData rsData = this.memberService.delete(deleteRequest.getId());

        return RsData.of(rsData.getRsCode(), rsData.getMsg());
    }

}
