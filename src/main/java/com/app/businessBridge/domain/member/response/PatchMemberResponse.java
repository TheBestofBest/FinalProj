package com.app.businessBridge.domain.member.response;

import com.app.businessBridge.domain.member.DTO.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatchMemberResponse {
    private MemberDTO memberDTO;
}
