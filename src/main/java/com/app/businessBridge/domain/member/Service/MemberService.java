package com.app.businessBridge.domain.member.Service;

import com.app.businessBridge.domain.department.entity.Department;
import com.app.businessBridge.domain.grade.entity.Grade;
import com.app.businessBridge.domain.member.DTO.MemberDTO;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.member.repository.MemberRepository;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RsData<MemberDTO> create(Department department, Grade grade, String username,
                                    Integer memberNumber, String name, String password, String email) {
        Member member = Member.builder()
                .department(department)
                .grade(grade)
                .username(username)
                .memberNumber(memberNumber)
                .name(name)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();

        this.memberRepository.save(member);

        return RsData.of(RsCode.S_02, "회원이 성공적으로 등록되었습니다.", new MemberDTO(member));
    }

    public RsData<MemberDTO> update(Long id, Department department, Grade grade, String username,
                                    Integer memberNumber, String name, String password, String email) {
        RsData<Member> rsData = findById(id);
        if (rsData.getData() == null) {
            return RsData.of(rsData.getRsCode(), rsData.getMsg(), null);
        }
        Member member = rsData.getData().toBuilder()
                .department(department)
                .grade(grade)
                .username(username)
                .memberNumber(memberNumber)
                .name(name)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();

        this.memberRepository.save(member);

        return RsData.of(RsCode.S_03, "회원 수정이 완료되었습니다.", new MemberDTO(member));
    }

    public RsData<MemberDTO> delete(Long id) {
        RsData<Member> rsData = findById(id);
        if (rsData.getData() == null) {
            return RsData.of(rsData.getRsCode(), rsData.getMsg(), null);
        }

        this.memberRepository.delete(rsData.getData());

        return RsData.of(RsCode.S_04, "회원 삭제가 완료되었습니다.", null);
    }

    public RsData<Member> findById(Long id) {
        Optional<Member> om = this.memberRepository.findById(id);

        if (om.isEmpty()) {
            return RsData.of(RsCode.F_04, "회원이 존재하지 않습니다.", null);
        }
        return RsData.of(RsCode.S_05, "회원을 찾았습니다.", om.get());
    }

    public RsData<Member> findByUsername(String username) {
        Optional<Member> om = this.memberRepository.findByUsername(username);

        if (om.isEmpty()) {
            return RsData.of(RsCode.F_04, "회원이 존재하지 않습니다.", null);
        }
        return RsData.of(RsCode.S_05, "회원을 찾았습니다.", om.get());
    }
}
