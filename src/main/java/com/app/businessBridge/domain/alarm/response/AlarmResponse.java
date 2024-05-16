package com.app.businessBridge.domain.alarm.response;

import com.app.businessBridge.domain.alarm.dto.AlarmDto;
import com.app.businessBridge.domain.alarm.entity.Alarm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class AlarmResponse {

    @Getter
    @Setter
    public static class AlarmTop10Res{
        List<AlarmDto> alarms;

        public AlarmTop10Res(List<Alarm> alarms) {
            this.alarms = alarms.stream().map(AlarmDto::new).toList();
        }

    }

    @Getter
    @Setter
    public static class AlarmRes{
        Page<AlarmDto> alarms;

        public AlarmRes(Page<Alarm> alarms) {
            this.alarms = alarms.map(AlarmDto::new);
        }

    }


}
