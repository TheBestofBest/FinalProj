package com.app.businessBridge.domain.member.response;

import com.app.businessBridge.domain.member.DTO.MemberDTO;
import com.app.businessBridge.domain.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class MemberResponse {

    @Getter
    public static class GetMember {
        private MemberDTO memberDTO;

        public GetMember(Member member) {
            this.memberDTO = new MemberDTO(member);
        }
    }

    @Getter
    public static class PatchedMember {
        private MemberDTO memberDTO;

        public PatchedMember(Member member) {
            this.memberDTO = new MemberDTO(member);
        }
    }
}
