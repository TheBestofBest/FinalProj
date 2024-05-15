package com.app.businessBridge.domain.member.Service;

import ch.qos.logback.core.spi.ConfigurationEvent;
import com.app.businessBridge.domain.department.entity.Department;
import com.app.businessBridge.domain.department.repository.DepartmentRepository;
import com.app.businessBridge.domain.division.entity.Division;
import com.app.businessBridge.domain.division.repository.DivisionRepository;
import com.app.businessBridge.domain.grade.entity.Grade;
import com.app.businessBridge.domain.grade.repository.GradeRepository;
import com.app.businessBridge.domain.grade.service.GradeService;
import com.app.businessBridge.domain.meetingRoom.entity.MeetingRoom;
import com.app.businessBridge.domain.meetingRoom.service.MeetingRoomService;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.member.repository.MemberRepository;
import com.app.businessBridge.domain.member.response.MemberResponse;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import com.app.businessBridge.global.jwt.JwtProvider;
import com.app.businessBridge.global.request.Request;
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
    private final DivisionRepository divisionRepository;
    private final DepartmentRepository departmentRepository;
    private final GradeRepository gradeRepository;
    private final JwtProvider jwtProvider;
    private final MeetingRoomService meetingRoomService;
    private final Request rq;

    // 회원 생성
    public RsData create(Integer divisionCode, Integer departmentCode, Integer gradeCode, String username,
                         Integer memberNumber, String name, String password, String email) {
        Optional<Division> odv = this.divisionRepository.findByCode(divisionCode);
        Optional<Department> od = this.departmentRepository.findByCode(departmentCode);
        Optional<Grade> og = this.gradeRepository.findByCode(gradeCode);

        if (findByUsername(username).getData() != null) {
            return RsData.of(RsCode.F_06, "중복된 아이디가 존재합니다.");
        } else if (findByMemberNumber(memberNumber).getData() != null) {
            return RsData.of(RsCode.F_06, "중복된 사원번호가 존재합니다.");
        }

        Member member = Member.builder()
                .division(odv.get())
                .department(od.get())
                .grade(og.get())
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

    // memberNumber(사원번호)로 회원 찾기
    public RsData<Member> findByMemberNumber(Integer memberNumber) {
        Optional<Member> om = this.memberRepository.findByMemberNumber(memberNumber);

        if (om.isEmpty()) {
            return RsData.of(RsCode.F_04, "회원이 존재하지 않습니다.", null);
        }
        return RsData.of(RsCode.S_05, "회원을 찾았습니다.", om.get());
    }

    // email로 회원 찾기
    public RsData<Member> findByEmail(String email) {
        Optional<Member> om = this.memberRepository.findByEmail(email);

        if (om.isEmpty()) {
            return RsData.of(RsCode.F_04, "회원이 존재하지 않습니다.", null);
        }
        return RsData.of(RsCode.S_05, "회원을 찾았습니다.", om.get());
    }

    // 회원 수정
    public RsData<Member> update(Long id, Integer divisionCode, Integer departmentCode, Integer gradeCode, String username,
                                 Integer memberNumber, String name, String password, String email) {
        RsData<Member> rsData = findById(id);
        Optional<Division> odv = this.divisionRepository.findByCode(divisionCode);
        Optional<Department> od = this.departmentRepository.findByCode(departmentCode);
        Optional<Grade> og = this.gradeRepository.findByCode(gradeCode);

        // 존재하는 회원인지 검증
        if (rsData.getData() == null) {
            return RsData.of(rsData.getRsCode(), rsData.getMsg(), null);
        }

        if (findByUsername(username).getData() != null && findByUsername(username).getData().getId() != id) {
            return RsData.of(RsCode.F_06, "중복된 아이디가 존재합니다.", null);
        } else if (findByMemberNumber(memberNumber).getData() != null && findByMemberNumber(memberNumber).getData().getId() != id) {
            return RsData.of(RsCode.F_06, "중복된 사원번호가 존재합니다.", null);
        } else if (findByEmail(email).getData() != null && findByEmail(email).getData().getId() != id) {
            return RsData.of(RsCode.F_06, "중복된 이메일이 존재합니다.", null);
        }

        Member member = rsData.getData().toBuilder()
                .division(odv.get())
                .department(od.get())
                .grade(og.get())
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

        if (om.isEmpty()) {
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

        if (!passwordEncoder.matches(password, om.get().getPassword())) {
            return RsData.of(RsCode.F_02, "잘못된 ID 또는 비밀번호 입니다.", null);
        }

        Member member = om.get();

        // AccessToken 생성
        String accessToken = jwtProvider.genAccessToken(member);
        // RefreshToken 생성
        String refreshToken = jwtProvider.genRefreshToken(member);

        om.get().setRefreshToken(refreshToken);

        System.out.println("accessToken : " + accessToken);

        return RsData.of(RsCode.S_06, "로그인에 성공했습니다.", new MemberResponse.AuthAndMakeTokensResponseBody(member, accessToken, refreshToken));
    }

    // 정산, 통계 테스트용 회원 생성 로직
    public RsData createRebateTest(Integer departmentCode, Integer gradeCode, String username,
                                   Integer memberNumber, String name, String password, String email, Long salary, char sex, String age) {
        Optional<Department> od = this.departmentRepository.findByCode(departmentCode);
        Optional<Grade> og = this.gradeRepository.findByCode(gradeCode);

        if (findByUsername(username).getData() != null) {
            return RsData.of(RsCode.F_06, "중복된 아이디가 존재합니다.");
        } else if (findByMemberNumber(memberNumber).getData() != null) {
            return RsData.of(RsCode.F_06, "중복된 사원번호가 존재합니다.");
        }

        Member member = Member.builder()
                .department(od.get())
                .grade(og.get())
                .username(username)
                .memberNumber(memberNumber)
                .name(name)
                .password(passwordEncoder.encode(password))
                .email(email)
                .salary(salary)
                .sex(sex)
                .age(age)
                .build();

        this.memberRepository.save(member);

        return RsData.of(RsCode.S_02, "회원이 성공적으로 등록되었습니다.");
    }


    //회의에 수락한 members
    @Transactional
    public RsData<List<Member>> getApprovedMembersByMeetingRoom(Long roomId) {
        List<Member> members = memberRepository.findByApprovedMeetingRoomId(roomId);
        return members.isEmpty() ? RsData.of(
                RsCode.F_04,
                "해당 회원 없음"
        ) : RsData.of(
                RsCode.S_01,
                "불러오기 성공",
                members
        );
    }


    //회의 수락
    @Transactional
    public RsData<Member> approveMeeting(Member member, Long roomId) {
        RsData<MeetingRoom> rsData = meetingRoomService.getMeetingRoom(roomId);
        if (!rsData.getIsSuccess()) {
            return RsData.of(RsCode.F_04,
                    "%d번 회의 없음".formatted(roomId));
        }
        if (!member.getMeetingRoom().getId().equals(roomId)) {
            return RsData.of(RsCode.F_01,
                    "초대받은 회의와 다른 요청입니다.");
        }
        Member approvedMember = member.toBuilder()
                .meetingState(true)
                .build();
        memberRepository.save(approvedMember);
        List<Member> members = this.getApprovedMembersByMeetingRoom(roomId).getData();
        meetingRoomService.updateMembers(roomId, members);
        return RsData.of(RsCode.S_01,
                "회의 수락", member);
    }

    //회의 초대
    @Transactional
    public RsData<Member> inviteMeeting(Member member, Long roomId) {
        RsData<MeetingRoom> rsData = meetingRoomService.getMeetingRoom(roomId);
        if (!rsData.getIsSuccess()) {
            return RsData.of(RsCode.F_04,
                    "%d번 회의 없음".formatted(roomId));
        }
        if (member.getMeetingRoom() != null) {
            return RsData.of(RsCode.F_01,
                    "%d번 회의 참여중".formatted(member.getMeetingRoom().getId()),
                    member);
        }
        Member invitedMember = member.toBuilder()
                .meetingState(false)
                .meetingRoom(rsData.getData())
                .build();
        memberRepository.save(invitedMember);
        return RsData.of(RsCode.S_01,
                "초대 완료", member);
    }

    //회의 나가기
    @Transactional
    public RsData<List<Member>> exitMeeting(Member member, Long roomId) {
        Member exitMember = member.toBuilder()
                .meetingState(null)
                .meetingRoom(null)
                .build();
        memberRepository.save(exitMember);
        List<Member> members = this.getApprovedMembersByMeetingRoom(roomId).getData();
        meetingRoomService.updateMembers(roomId, members);
        return RsData.of(RsCode.S_01,
                "나가기 완료", members);
    }

    // 로그아웃
    public RsData logout() {
        rq.removeCrossDomainCookie("accessToken");
        rq.removeCrossDomainCookie("refreshToken");

        return RsData.of(RsCode.S_07, "로그아웃되었습니다.");
    }

    // name(이름)로 회원 찾기
    public RsData<Member> findByName(String memberName) {
        Optional<Member> om = this.memberRepository.findByName(memberName);

        if (om.isEmpty()) {
            return RsData.of(RsCode.F_04, "회원이 존재하지 않습니다.", null);
        }
        return RsData.of(RsCode.S_05, "회원을 찾았습니다.", om.get());
    }
}