package com.app.businessBridge.global.holidayapi.workingdate.service;

import com.app.businessBridge.domain.rebate.service.RebateService;
import com.app.businessBridge.global.holidayapi.ApiExplorer;
import com.app.businessBridge.global.holidayapi.dto.AllDayDto;
import com.app.businessBridge.global.holidayapi.dto.HoliDayDto;
import com.app.businessBridge.global.holidayapi.workingdate.entity.WorkingDate;
import com.app.businessBridge.global.holidayapi.workingdate.repository.WorkingDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkingDateService {

    private final WorkingDateRepository workingDateRepository;

    private final ApiExplorer apiExplorer;

    public void createThisYear() throws IOException {
        LocalDate currentDate = LocalDate.now();
        String year = String.valueOf(currentDate.getYear());

        for(int i = 1; i < 13; i++) {
            HoliDayDto holiDayDto = this.apiExplorer.getHoilDay(year, String.valueOf(i));
            AllDayDto allDayDto = this.apiExplorer.getAllDay(year, String.valueOf(i));

            int workDate = getWorkDate(allDayDto, holiDayDto);

            WorkingDate wd = WorkingDate.builder()
                    .year(year)
                    .month(String.valueOf(i))
                    .workDate(workDate)
                    .build();

            this.workingDateRepository.save(wd);
        }
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

    public WorkingDate findByYearAndMonth(String year, String month) {
        return this.workingDateRepository.findByYearAndMonth(year, month);
    }
}
