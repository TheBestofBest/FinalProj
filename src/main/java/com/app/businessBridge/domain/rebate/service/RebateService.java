package com.app.businessBridge.domain.rebate.service;


import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.rebate.entity.Rebate;
import com.app.businessBridge.domain.rebate.repository.RebateRepository;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import com.app.businessBridge.global.holidayapi.ApiExplorer;
import com.app.businessBridge.global.holidayapi.dto.AllDayDto;
import com.app.businessBridge.global.holidayapi.dto.HoliDayDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RebateService {

    private final RebateRepository rebateRepository;

    private final ApiExplorer apiExplorer;

    public RsData<Rebate> createRebate(Member member, String year, String month) throws IOException {

        HoliDayDto holiDayDto = this.apiExplorer.getHoilDay(year, month);
        AllDayDto allDayDto = this.apiExplorer.getAllDay(year, month);

        // 해당 year년, month월에 근무해야 하는 일 수
        int workDate = getWorkDate(allDayDto, holiDayDto);

        // -----
        // member 결재내역 조회 하여 휴가 등 근무하지 않은 날 확인하는 로직 필요
        // 또는 근태관련 결재 최종 승인 시 근무일을 빼는 방법으로 ????
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

        return RsData.of(RsCode.S_01, year + "년 " + month + "월" + " 급여정산내역", rebate);
    }

    private static int getWorkDate(AllDayDto allDayDto, HoliDayDto holiDayDto) {

        int holiDay = 0;

        List<String> weekends = new ArrayList<>();

        // 주말 구하는 로직
        for (int i = 0; i < allDayDto.getResponse().getBody().getItems().getItem().size(); i++) {
            if (allDayDto.getResponse().getBody().getItems().getItem().get(i).getSolWeek().equals("토") || allDayDto.getResponse().getBody().getItems().getItem().get(i).getSolWeek().equals("일")) {
                holiDay++;
                // '20240509' 형식으로 weekends에 추가
                weekends.add(allDayDto.getResponse().getBody().getItems().getItem().get(i).getSolYear() + allDayDto.getResponse().getBody().getItems().getItem().get(i).getSolMonth() + allDayDto.getResponse().getBody().getItems().getItem().get(i).getSolDay());
            }
        }

        // 평일 중 공휴일 구하는 로직
        if(holiDayDto.getResponse().getBody().getItems() != null) {
            for (int i = 0; i < holiDayDto.getResponse().getBody().getItems().getItem().size(); i++) {
                // holiday '20240509' 형식의 locdate가 weekends에 포함되어 있지 않으면
                if(!weekends.contains(holiDayDto.getResponse().getBody().getItems().getItem().get(i).getLocdate())) {
                    holiDay++;
                }
            }
        }

        int workDate = allDayDto.getResponse().getBody().getTotalCount() - holiDay;

        return workDate;
    }

    public List<Rebate> findAll() {
        return this.rebateRepository.findAll();
    }

    public RsData<Rebate> findById(Long id) {
        Optional<Rebate> rebate = this.rebateRepository.findById(id);

        if(rebate.isEmpty()) {
            return RsData.of(RsCode.F_04,
                    "존재하지 않는 정산내역 입니다.",
                    null);
        }

        return RsData.of(RsCode.S_01,
                "불러오기 성공",
                rebate.get());
    }

    public List<Rebate> findMyRebates(Long id) {
        return this.rebateRepository.findMyRebates(String.valueOf(id));
    }
}