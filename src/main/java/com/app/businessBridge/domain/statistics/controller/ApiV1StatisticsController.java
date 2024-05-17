package com.app.businessBridge.domain.statistics.controller;


import com.app.businessBridge.domain.member.Service.MemberService;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.rebate.response.RebatesResponse;
import com.app.businessBridge.domain.statistics.dto.StatsDto;
import com.app.businessBridge.domain.statistics.response.StatsResponse;
import com.app.businessBridge.domain.statistics.service.StatisticsService;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import com.app.businessBridge.global.request.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics")
public class ApiV1StatisticsController {

    private final StatisticsService statisticsService;

    private final MemberService memberService;

    private final Request rq;

    @GetMapping("")
    public RsData<StatsResponse> getStatistics() {

        Member member = rq.getMember();

        if(member.getGrade().getCode() != 1000 || !member.getGrade().getName().equals("슈퍼관리자")) {
            return RsData.of(
                    RsCode.F_02,
                    "접근 권한이 없습니다.",
                    null
            );
        }

        List<Member> members = this.memberService.findAll();


        return RsData.of(
                RsCode.S_01,
                "통계 데이터 생성 성공",
                new StatsResponse(new StatsDto(members))
        );
    }
}
