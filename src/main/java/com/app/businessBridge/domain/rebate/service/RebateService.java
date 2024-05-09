package com.app.businessBridge.domain.rebate.service;


import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.rebate.entity.Rebate;
import com.app.businessBridge.domain.rebate.repository.RebateRepository;
import com.app.businessBridge.global.holidayapi.ApiExplorer;
import com.app.businessBridge.global.holidayapi.dto.AllDayDto;
import com.app.businessBridge.global.holidayapi.dto.HoliDayDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RebateService {

    private final RebateRepository rebateRepository;

    private final ApiExplorer apiExplorer;

    public void createRebate(Member member, String year, String month) throws IOException {
        HoliDayDto holiDayDto = this.apiExplorer.getHoilDay(year, month);
        AllDayDto allDayDto = this.apiExplorer.getAllDay(year, month);

        // 해당 year년, month월에 근무해야 하는 일 수
        int workDate = getWorkDate(allDayDto, holiDayDto);

        // -----
        // member 결재내역 조회 하여 휴가 등 근무하지 않은 날 확인하는 로직 필요
        // -----

        Long salary = member.getSalary() / 12;
        // 현행법상 소득 1,200만원 이하의 경우 6% 적용 한다고함
        Long tax = (long) (salary * 0.06);
        Long insurance = 50000L;
        Long totalSalary = salary - tax - insurance;

        Rebate rebate = Rebate.builder()
                .member(member)
                .year(year)
                .month(month)
                .workDate(workDate)
                .workedDate(workDate)
                .salary(salary)
                .bonus(0L)
                .tax(tax)
                .insurance(insurance)
                .totalSalary(totalSalary)
                .build();

        this.rebateRepository.save(rebate);
    }

    private static int getWorkDate(AllDayDto allDayDto, HoliDayDto holiDayDto) {

        int holiDay = 0;

        List<String> weekends = new ArrayList<>();

        // 주말 구하는 로직
        for (int i = 0; i < allDayDto.getResponse().getBody().getItems().getItem().size(); i++) {
            if (allDayDto.getResponse().getBody().getItems().getItem().get(i).getSolWeek().equals("토") || allDayDto.getResponse().getBody().getItems().getItem().get(i).getSolWeek().equals("일")) {
                holiDay++;
                weekends.add(allDayDto.getResponse().getBody().getItems().getItem().get(i).getSolYear() + allDayDto.getResponse().getBody().getItems().getItem().get(i).getSolMonth() + allDayDto.getResponse().getBody().getItems().getItem().get(i).getSolDay());
            }
        }

        // 평일 중 공휴일 구하는 로직
        for (int i = 0; i < holiDayDto.getResponse().getBody().getItems().getItem().size(); i++) {
            for (int j = 0; j < weekends.size(); j++) {
                if (!holiDayDto.getResponse().getBody().getItems().getItem().get(i).getLocdate().equals(weekends.get(j))) {
                    holiDay++;
                }
            }
        }

        int workDate = allDayDto.getResponse().getBody().getTotalCount() - holiDay;

        return workDate;
    }
}
