package com.app.businessBridge.domain.member.response;

import com.app.businessBridge.domain.chattingRoom.dto.ChattingRoomDto;
import com.app.businessBridge.domain.member.DTO.MemberDTO;
import com.app.businessBridge.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

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
    public static class GetMembers {
        private List<MemberDTO> memberDTOs;

        public GetMembers(List<Member> members) {
            this.memberDTOs = (members == null) ? null : members.stream().map(MemberDTO::new).toList();
        }
    }

    @Getter
    public static class PatchedMember {
        private MemberDTO memberDTO;

        public PatchedMember(Member member) {
            this.memberDTO = new MemberDTO(member);
        }
    }

    @Getter
    public static class AuthAndMakeTokensResponseBody {
        private Member member;
        private String accessToken;
        private String refreshToken;

        public AuthAndMakeTokensResponseBody(Member member, String accessToken, String refreshToken) {
            this.member = member;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }

    @Getter
    public static class LoginResponse {
        private MemberDTO memberDTO;

        public LoginResponse(MemberDTO memberDTO) {
            this.memberDTO = memberDTO;
        }
    }


    @Getter
    public static class MemberSearchResponse{
        private List<MemberDTO> memberDTOs;

        public MemberSearchResponse(List<Member> memberList) {
            this.memberDTOs = memberList.stream().map(MemberDTO::new).toList();
        }
    }
}
