package com.app.businessBridge.domain.alarm.controller;

import com.app.businessBridge.domain.alarm.entity.Alarm;
import com.app.businessBridge.domain.alarm.response.AlarmResponse;
import com.app.businessBridge.domain.alarm.service.AlarmService;
import com.app.businessBridge.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alarms")
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping("/top10")
    public RsData<AlarmResponse.AlarmTop10Res> getTop10(){

        RsData<List<Alarm>> result = alarmService.getTop10();

        return RsData.of(result.getRsCode(),result.getMsg(),new AlarmResponse.AlarmTop10Res(result.getData()) );
    }

    @GetMapping("")
    public RsData<AlarmResponse.AlarmRes> getAlarms(@RequestParam(value="page", defaultValue="0") int page){

        RsData<Page<Alarm>> result = alarmService.getAlarms(page);

        return RsData.of(result.getRsCode(),result.getMsg(),new AlarmResponse.AlarmRes(result.getData()) );
    }


}
