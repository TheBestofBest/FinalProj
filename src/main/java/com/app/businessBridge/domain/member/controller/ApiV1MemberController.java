package com.app.businessBridge.domain.member.controller;

import com.app.businessBridge.domain.Article.Entity.Article;
import com.app.businessBridge.domain.member.DTO.MemberDTO;
import com.app.businessBridge.domain.member.Service.MemberService;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.member.request.MemberRequest;
import com.app.businessBridge.domain.member.response.MemberResponse;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import com.app.businessBridge.global.request.Request;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class ApiV1MemberController {
    private final MemberService memberService;
    private final Request rq;

    // 멤버 생성
    @PostMapping("")
    public RsData signup(@Valid @RequestBody MemberRequest.CreateRequest createRequest,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return RsData.of(RsCode.F_10, "알 수 없는 오류로 실패했습니다.");
        }

        RsData rsData = this.memberService.create(createRequest.getDivisionCode(), createRequest.getDepartmentCode(), createRequest.getGradeCode(),
                createRequest.getUsername(), createRequest.getPassword(), createRequest.getEmail(), createRequest.getMemberNumber(), createRequest.getName()
                );

        return RsData.of(rsData.getRsCode(), rsData.getMsg());
    }

    // 멤버 로그인
    @PostMapping("/login")
    public RsData<MemberResponse.LoginResponse> login(@Valid @RequestBody MemberRequest.LoginRequest loginRequest) {
        RsData<MemberResponse.AuthAndMakeTokensResponseBody> authAndMakeTokensRsData =
                this.memberService.authAndMakeTokens(loginRequest.getUsername(), loginRequest.getPassword());
        try {
            // 쿠키에 accessToken, refreshToken 토큰 넣기
            rq.setCrossDomainCookie("accessToken", authAndMakeTokensRsData.getData().getAccessToken());
            rq.setCrossDomainCookie("refreshToken", authAndMakeTokensRsData.getData().getRefreshToken());
        } catch (Exception e) {
            return RsData.of(RsCode.F_10, "로그인에 실패하였습니다.");
        }

        return RsData.of(authAndMakeTokensRsData.getRsCode(), authAndMakeTokensRsData.getMsg(),
                new MemberResponse.LoginResponse(new MemberDTO(authAndMakeTokensRsData.getData().getMember())));
    }

    @PostMapping("/logout")
    public RsData<Void> logout() {
        return this.memberService.logout();
    }

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
        RsData<Member> rsData = this.memberService.update(updateRequest.getId(), updateRequest.getDivisionCode(), updateRequest.getDepartmentCode(), updateRequest.getGradeCode(),
                updateRequest.getUsername(),updateRequest.getPassword(), updateRequest.getEmail(), updateRequest.getMemberNumber(),
                updateRequest.getName(),updateRequest.getAssignedTask(),updateRequest.getExtensionNumber(),
                updateRequest.getPhoneNumber(),updateRequest.getStatusMessage(),updateRequest.getSex(),
                updateRequest.getAge());

        if (rsData.getData() == null) {
            return RsData.of(rsData.getRsCode(), rsData.getMsg());
        }
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

    @GetMapping("/me")
    public RsData<MemberResponse.GetMember> memberMe() {
        if (rq.getMember() == null) {
            return RsData.of(RsCode.F_02, "로그아웃 상태입니다.", null);
        }
        return RsData.of(RsCode.S_06, "로그인 상태 입니다.", new MemberResponse.GetMember(rq.getMember()));
    }

    @GetMapping("")
    public RsData<MemberResponse.GetMembers> getMembers() {
        List<Member> memberList = this.memberService.getAll();

        return RsData.of(RsCode.S_01, "회원 리스트를 성공적으로 불러왔습니다.", new MemberResponse.GetMembers(memberList));
    }

    @GetMapping("/search")
    public RsData<MemberResponse.MemberSearchResponse> searchArticleByKeyword(@RequestParam(value = "keyword") String keyword) {
        List<Member> MemberList = this.memberService.searchMember(keyword);

        return RsData.of(RsCode.S_05, "멤버 검색 성공", new MemberResponse.MemberSearchResponse(MemberList));
    }
}
