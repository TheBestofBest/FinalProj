package com.app.businessBridge.domain.member.Service;

import com.app.businessBridge.domain.department.entity.Department;
import com.app.businessBridge.domain.department.service.DepartmentService;
import com.app.businessBridge.domain.grade.entity.Grade;
import com.app.businessBridge.domain.grade.service.GradeService;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.member.repository.MemberRepository;
import com.app.businessBridge.domain.member.response.MemberResponse;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import com.app.businessBridge.global.jwt.JwtProvider;
import com.app.businessBridge.global.security.SecurityUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentService departmentService;
    private final GradeService gradeService;
    private final JwtProvider jwtProvider;

    // 회원 생성
    public RsData create(Long departmentId, Long gradeId, String username,
                         Integer memberNumber, String name, String password, String email) {
        RsData<Department> departmentRsData = this.departmentService.findById(departmentId);
        RsData<Grade> gradeRsData = this.gradeService.findById(gradeId);

        // 존재하는 부서, 직급인지 검증하는 if 문
        if (departmentRsData.getData() == null) {
            return RsData.of(departmentRsData.getRsCode(), departmentRsData.getMsg());
        } else if (gradeRsData.getData() == null) {
            return RsData.of(gradeRsData.getRsCode(), gradeRsData.getMsg());
        }

        Member member = Member.builder()
                .department(departmentRsData.getData())
                .grade(gradeRsData.getData())
                .username(username)
                .memberNumber(memberNumber)
                .name(name)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();

        this.memberRepository.save(member);

        return RsData.of(RsCode.S_02, "회원이 성공적으로 등록되었습니다.");
    }

    // Id로 회원 찾기
    public RsData<Member> findById(Long id) {
        Optional<Member> om = this.memberRepository.findById(id);

        if (om.isEmpty()) {
            return RsData.of(RsCode.F_04, "회원이 존재하지 않습니다.", null);
        }
        return RsData.of(RsCode.S_05, "회원을 찾았습니다.", om.get());
    }

    // username으로 회원 찾기
    public RsData<Member> findByUsername(String username) {
        Optional<Member> om = this.memberRepository.findByUsername(username);

        if (om.isEmpty()) {
            return RsData.of(RsCode.F_04, "회원이 존재하지 않습니다.", null);
        }
        return RsData.of(RsCode.S_05, "회원을 찾았습니다.", om.get());
    }

    // 회원 수정
    public RsData<Member> update(Long id, Long departmentId, Long gradeId, String username,
                                 Integer memberNumber, String name, String password, String email) {
        RsData<Member> rsData = findById(id);
        RsData<Department> departmentRsData = this.departmentService.findById(departmentId);
        RsData<Grade> gradeRsData = this.gradeService.findById(gradeId);

        // 존재하는 회원, 부서, 직급인지 검증하는 if 문
        if (rsData.getData() == null) {
            return RsData.of(rsData.getRsCode(), rsData.getMsg(), null);
        } else if (departmentRsData.getData() == null) {
            return RsData.of(departmentRsData.getRsCode(), departmentRsData.getMsg(), null);
        } else if (gradeRsData.getData() == null) {
            return RsData.of(gradeRsData.getRsCode(), gradeRsData.getMsg(), null);
        }

        Member member = rsData.getData().toBuilder()
                .department(departmentRsData.getData())
                .grade(gradeRsData.getData())
                .username(username)
                .memberNumber(memberNumber)
                .name(name)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();

        String refreshToken = jwtProvider.genRefreshToken(member);

        member.setRefreshToken(refreshToken);


        this.memberRepository.save(member);

        return RsData.of(RsCode.S_03, "회원 수정이 완료되었습니다.", member);
    }

    // 토큰 유효성 확인
    public boolean validateToken(String token) {
        return jwtProvider.verify(token);
    }

    public RsData<String> refreshAccessToken(String refreshToken) {
        Optional<Member> om = this.memberRepository.findByRefreshToken(refreshToken);

        if (om.isEmpty()){
            return RsData.of(RsCode.F_04, "리프레시 토큰이 존재하지 않습니다.", null);
        }
        Member member = om.get();

        String accessToken = jwtProvider.genAccessToken(member);

        return RsData.of(RsCode.S_07, "토큰 갱신 성공", accessToken);
    }

    // 회원 탈퇴
    public RsData delete(Long id) {
        RsData<Member> rsData = findById(id);
        if (rsData.getData() == null) {
            return RsData.of(rsData.getRsCode(), rsData.getMsg());
        }

        this.memberRepository.delete(rsData.getData());

        return RsData.of(RsCode.S_04, "회원 삭제가 완료되었습니다.");
    }

    public SecurityUser getUserFromAccessToken(String accessToken) {
        Map<String, Object> payloadBody = jwtProvider.getClaims(accessToken);

        long id = (int) payloadBody.get("id");
        String username = (String) payloadBody.get("username");
        List<GrantedAuthority> authorities = new ArrayList<>();

        return new SecurityUser(id, username, "", authorities);
    }

    public RsData<MemberResponse.AuthAndMakeTokensResponseBody> authAndMakeTokens(String username, String password) {
        Optional<Member> om = this.memberRepository.findByUsername(username);

        if (om.isEmpty()) {
            return RsData.of(RsCode.F_04, "잘못된 ID 또는 비밀번호 입니다.", null);
        }

        if(!passwordEncoder.matches(password,om.get().getPassword())){
            return RsData.of(RsCode.F_02,"잘못된 ID 또는 비밀번호 입니다.",null);
        }

        Member member = om.get();

        // AccessToken 생성
        String accessToken = jwtProvider.genAccessToken(member);
        // RefreshToken 생성
        String refreshToken = jwtProvider.genRefreshToken(member);

        System.out.println("accessToken : " + accessToken);

        return RsData.of(RsCode.S_06, "로그인에 성공했습니다.", new MemberResponse.AuthAndMakeTokensResponseBody(member, accessToken,refreshToken));
    }

}